package me.jishuna.spells.inventory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.jishuna.jishlib.MessageHandler;
import me.jishuna.jishlib.inventory.CustomInventory;
import me.jishuna.jishlib.items.ItemBuilder;
import me.jishuna.spells.Spells;
import me.jishuna.spells.api.MessageKeys;
import me.jishuna.spells.api.playerdata.PlayerSpellData;
import me.jishuna.spells.api.spell.Spell;
import me.jishuna.spells.api.spell.part.SpellPart;
import net.md_5.bungee.api.ChatColor;

public class WandInventory extends CustomInventory {

    // @formatter:on
    private final Spells plugin;
    private final PlayerSpellData data;
    private final ItemStack targetItem;
    private final Spell[] spells;

    public WandInventory(Spells plugin, PlayerSpellData data, ItemStack item, Spell[] spells) {
        super(Bukkit.createInventory(null, 9, MessageHandler.get(MessageKeys.SPELL_BUILDER_GUI_TITLE)));

        this.plugin = plugin;
        this.data = data;
        this.targetItem = item;
        this.spells = spells;

        addClickConsumer(event -> event.setCancelled(true));

        populate();
    }

    private void populate() {
        int index = 0;
        for (Spell spell : this.spells) {
            final int indexCopy = index;
            addButton(indexCopy, createSpellIcon(spell), event -> editSpell(indexCopy));
            index++;
        }
    }

    private void editSpell(int index) {
        SpellBuilderInventory inventory = new SpellBuilderInventory(this.plugin, this.data, this.targetItem, this.spells, index);
        Bukkit.getScheduler().runTask(this.plugin, () -> this.plugin.getInventoryManager().openInventory(this.data.getPlayer(), inventory));
    }

    private ItemStack createSpellIcon(Spell spell) {
        List<String> lore = new ArrayList<>();

        for (SpellPart part : spell.getParts()) {
            if (part == SpellPart.EMPTY) {
                continue;
            }
            lore.add(part.getDisplayName());
        }
        lore.add(" ");
        lore.add("Click to edit");

        return ItemBuilder.create(Material.PAPER).name(ChatColor.GOLD + spell.getName()).lore(lore).build();
    }
}
