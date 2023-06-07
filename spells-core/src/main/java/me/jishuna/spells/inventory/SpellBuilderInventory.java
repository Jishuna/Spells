package me.jishuna.spells.inventory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import me.jishuna.jishlib.MessageHandler;
import me.jishuna.jishlib.Utils;
import me.jishuna.jishlib.inventory.CustomInventory;
import me.jishuna.jishlib.items.ItemBuilder;
import me.jishuna.spells.ArbitraryStringPrompt;
import me.jishuna.spells.Spells;
import me.jishuna.spells.api.MessageKeys;
import me.jishuna.spells.api.SpellsAPI;
import me.jishuna.spells.api.playerdata.PlayerSpellData;
import me.jishuna.spells.api.spell.Spell;
import me.jishuna.spells.api.spell.SpellBuilder;
import me.jishuna.spells.api.spell.part.SpellPart;
import me.jishuna.spells.api.spell.util.SpellUtil;

public class SpellBuilderInventory extends CustomInventory {
    // @formatter:off
    private final ItemStack PREVIOUS = ItemBuilder.create(Material.PLAYER_HEAD)
            .name(MessageHandler.get(MessageKeys.PREVIOUS_PAGE))
            .skullTexture("bd69e06e5dadfd84e5f3d1c21063f2553b2fa945ee1d4d7152fdc5425bc12a9")
            .build();

    private final ItemStack NEXT = ItemBuilder.create(Material.PLAYER_HEAD)
            .name(MessageHandler.get(MessageKeys.NEXT_PAGE))
            .skullTexture("19bf3292e126a105b54eba713aa1b152d541a1d8938829c56364d178ed22bf")
            .build();

    private static final ItemStack FILLER = ItemBuilder.create(Material.ORANGE_STAINED_GLASS_PANE)
            .name(" ")
            .build();

    // @formatter:on
    private final Spells plugin;
    private final Player player;
    private final ItemStack targetItem;
    private final Spell[] spells;
    private final int spellIndex;
    private final SpellBuilder builder;
    private final List<SpellPart> allParts;

    private List<SpellPart> filteredParts;
    private int spellStart;
    private int partStart;

    public SpellBuilderInventory(Spells plugin, PlayerSpellData data, ItemStack item, Spell[] spells, int index) {
        super(Bukkit.createInventory(null, 54, MessageHandler.get(MessageKeys.SPELL_BUILDER_GUI_TITLE)));

        this.plugin = plugin;
        this.player = data.getPlayer();
        this.targetItem = item;
        this.spells = spells;
        this.spellIndex = index;
        this.builder = SpellBuilder.modifySpell(spells[index]);

        this.allParts = new ArrayList<>(data.getUnlockedParts());
        this.allParts.removeIf(part -> part == SpellPart.EMPTY || !part.isEnabled());
        this.allParts.sort(null);

        this.filteredParts = SpellUtil.filterParts(this.allParts, this.builder);

        addClickConsumer(event -> event.setCancelled(true));
        addCloseConsumer(this::finalizeSpell);

        populate();
        refreshSpell();
    }

    public void setColor(Color color) {
        this.builder.setColor(color);

        setItem(48, createColorItem());
    }

    private void populate() {
        for (int i = 28; i < 35; i++) {
            setItem(i, FILLER);
        }

        for (int i = 46; i < 53; i++) {
            setItem(i, FILLER);
        }

        addButton(27, PREVIOUS, e -> changePartIndex(-1));
        addButton(35, NEXT, e -> changePartIndex(1));

        addButton(45, PREVIOUS, e -> changeSpellIndex(-1));
        addButton(53, NEXT, e -> changeSpellIndex(1));

        addButton(47, createRenameItem(), this::renameSpell);
        addButton(48, createColorItem(), this::openColorSelector);

        addButton(50, ItemBuilder.create(Material.LIME_DYE).name(MessageHandler.get(MessageKeys.SAVE)).build(), event -> Bukkit.getScheduler().runTask(this.plugin, () -> event.getWhoClicked().closeInventory()));
        addButton(51, ItemBuilder.create(Material.RED_DYE).name(MessageHandler.get(MessageKeys.CLEAR_BUTTON_NAME)).lore(MessageHandler.getList(MessageKeys.CLEAR_BUTTON_LORE)).build(), this::clearParts);
    }

    private void refreshOptions() {
        for (int i = 0; i < 27; i++) {
            int index = this.partStart + i;
            if (index >= this.filteredParts.size()) {
                setItem(i, null);
                removeButton(i);
            } else {
                SpellPart part = this.filteredParts.get(index);
                addButton(i, part.getDisplayItem(), this::addPart);
            }
        }
    }

    private void refreshSpell() {
        for (int i = 0; i < 9; i++) {
            int index = this.spellStart + i;

            SpellPart part = builder.getPart(index);
            if (part == SpellPart.EMPTY) {
                setItem(i + 36, null);
                removeButton(i + 36);
            } else {
                addButton(i + 36, part.getDisplayItem(), this::removePart);
            }
        }

        this.filteredParts = SpellUtil.filterParts(this.allParts, this.builder);
        refreshOptions();
    }

    private void addPart(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();
        SpellPart part = item.getItemMeta().getPersistentDataContainer().get(NamespacedKey.fromString("spells:part"), SpellsAPI.SPELL_PART_TYPE);

        this.builder.addPart(part);

        refreshSpell();
    }

    private void changeSpellIndex(int amount) {
        this.player.playSound(this.player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1f, 1f);
        this.spellStart = Utils.clamp(this.spellStart + amount, 0, this.builder.getSize() - 9);
        refreshSpell();

    }

    private void changePartIndex(int amount) {
        this.player.playSound(this.player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1f, 1f);
        int maxPage = (this.filteredParts.size() - 1) / 27;
        this.partStart = Utils.clamp((this.partStart / 27) + amount, 0, maxPage) * 27;

        refreshOptions();
    }

    private void removePart(InventoryClickEvent event) {
        this.builder.clearPart(this.spellStart + (event.getSlot() - 36));

        refreshSpell();
    }

    private void clearParts(InventoryClickEvent event) {
        if (!event.isShiftClick()) {
            return;
        }
        this.builder.clearParts();

        refreshSpell();
    }

    private void renameSpell(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ConversationFactory factory = new ConversationFactory(this.plugin);

        Conversation conversation = factory.withFirstPrompt(new ArbitraryStringPrompt()).withEscapeSequence("exit").buildConversation(player);
        conversation.addConversationAbandonedListener(abandonEvent -> {
            String message = (String) abandonEvent.getContext().getSessionData("message");
            if (message != null && !message.isBlank()) {
                this.builder.setName(message);
            }
            Bukkit.getScheduler().runTask(this.plugin, () -> {
                setItem(47, createRenameItem());
                this.plugin.getInventoryManager().openInventory(player, this);
            });
        });

        Bukkit.getScheduler().runTask(this.plugin, () -> {
            player.closeInventory();
            conversation.begin();
        });
    }

    private void openColorSelector(InventoryClickEvent event) {
        ColorSelectorInventory inventory = new ColorSelectorInventory(this.plugin, this, this.builder.getColor());
        Bukkit.getScheduler().runTask(this.plugin, () -> this.plugin.getInventoryManager().openInventory(event.getWhoClicked(), inventory));
    }

    private void finalizeSpell(InventoryCloseEvent event) {
        this.spells[this.spellIndex] = this.builder.toSpell();

        ItemMeta meta = this.targetItem.getItemMeta();
        meta.getPersistentDataContainer().set(NamespacedKey.fromString("spells:spells"), SpellsAPI.SPELL_ARRAY_TYPE, this.spells);
        this.targetItem.setItemMeta(meta);
    }

    private ItemStack createColorItem() {
        return ItemBuilder.create(Material.LEATHER_CHESTPLATE).name(MessageHandler.get(MessageKeys.CHANGE_COLOR)).modify(LeatherArmorMeta.class, meta -> meta.setColor(this.builder.getColor())).flags(ItemFlag.HIDE_DYE, ItemFlag.HIDE_ATTRIBUTES).build();
    }

    private ItemStack createRenameItem() {
        return ItemBuilder.create(Material.ANVIL).name(MessageHandler.get(MessageKeys.RENAME_BUTTON_NAME)).lore(MessageHandler.getList(MessageKeys.RENAME_BUTTON_LORE, this.builder.getName())).build();
    }
}
