package me.jishuna.spells.inventory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.jishuna.jishlib.MessageHandler;
import me.jishuna.jishlib.Utils;
import me.jishuna.jishlib.inventory.CustomInventory;
import me.jishuna.jishlib.items.ItemBuilder;
import me.jishuna.spells.Spells;
import me.jishuna.spells.api.MessageKeys;
import me.jishuna.spells.api.altar.recipe.AltarRecipe;
import me.jishuna.spells.api.altar.recipe.SpellPartRecipe;
import me.jishuna.spells.api.playerdata.PlayerSpellData;

public class AltarRecipeInventory extends CustomInventory {
    // @formatter:off
    private final ItemStack PREVIOUS = ItemBuilder.create(Material.PLAYER_HEAD)
            .name(MessageHandler.get(MessageKeys.PREVIOUS_PAGE))
            .skullTexture("bd69e06e5dadfd84e5f3d1c21063f2553b2fa945ee1d4d7152fdc5425bc12a9")
            .build();

    private final ItemStack NEXT = ItemBuilder.create(Material.PLAYER_HEAD)
            .name(MessageHandler.get(MessageKeys.NEXT_PAGE))
            .skullTexture("19bf3292e126a105b54eba713aa1b152d541a1d8938829c56364d178ed22bf")
            .build();

    // @formatter:on
    private final Spells plugin;
    private final PlayerSpellData spellData;
    private final Location location;
    private final List<AltarRecipe> recipes;

    private int startIndex;

    public AltarRecipeInventory(Spells plugin, PlayerSpellData spellData, Location location) {
        super(Bukkit.createInventory(null, 54, MessageHandler.get(MessageKeys.ALTAR_GUI_TITLE)));

        this.plugin = plugin;
        this.spellData = spellData;
        this.location = location;
        this.recipes = new ArrayList<>(plugin.getRegistryHolder().getAltarRecipeRegistry().getRecipes());

        addClickConsumer(event -> event.setCancelled(true));

        populate();
        refreshOptions();
    }

    private void populate() {
        addButton(45, PREVIOUS, e -> changePage(-1));
        addButton(53, NEXT, e -> changePage(1));

        addButton(49, ItemBuilder.create(Material.BARRIER).name(MessageHandler.get(MessageKeys.CANCEL)).build(), event -> Bukkit.getScheduler().runTask(this.plugin, () -> event.getWhoClicked().closeInventory()));
    }

    private void refreshOptions() {
        for (int i = 0; i < 45; i++) {
            int index = this.startIndex + i;
            if (index >= this.recipes.size()) {
                setItem(i, null);
                removeButton(i);
            } else {
                AltarRecipe recipe = this.recipes.get(index);

                ItemStack item = recipe.getIcon();
                if (recipe instanceof SpellPartRecipe partRecipe) {
                    boolean unlocked = this.spellData.hasUnlockedPart(partRecipe.getPart());
                    item = ItemBuilder.modifyItem(item).name(unlocked ? MessageHandler.get(MessageKeys.UNLOCKED) : MessageHandler.get(MessageKeys.LOCKED), true).build();
                }
                addButton(i, item, this::craftPart);
            }
        }
    }

    private void craftPart(InventoryClickEvent event) {
        AltarRecipe recipe = this.recipes.get(this.startIndex + event.getSlot());

        Bukkit.getScheduler().runTask(this.plugin, () -> {
            this.plugin.getAltarManager().startAltarTask(this.spellData.getPlayer(), this.location, recipe);
            event.getWhoClicked().closeInventory();
        });
    }

    private void changePage(int amount) {
        this.spellData.getPlayer().playSound(this.spellData.getPlayer().getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1f, 1f);
        int maxPage = (this.recipes.size() - 1) / 45;
        this.startIndex = Utils.clamp((this.startIndex / 45) + amount, 0, maxPage) * 27;

        refreshOptions();
    }
}
