package me.jishuna.spells;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.jishuna.jishlib.Utils;
import me.jishuna.jishlib.inventory.CustomInventory;
import me.jishuna.jishlib.items.ItemBuilder;
import me.jishuna.spells.api.spell.SpellBuilder;
import me.jishuna.spells.api.spell.part.SpellPart;
import me.jishuna.spells.api.spell.part.SpellPartRegistry;

public class SpellBuilderInventory extends CustomInventory {
    private final ItemStack targetItem;

    private final SpellBuilder builder;
    private final List<SpellPart> allParts;

    private final int maxPartPage;

    private int spellStart;
    private int partStart;

    public SpellBuilderInventory(ItemStack item, SpellPartRegistry registry, SpellBuilder builder) {
        super(Bukkit.createInventory(null, 54, "Spell Builder"));
        this.targetItem = item;
        this.allParts = new ArrayList<>(registry.getAllParts());
        this.allParts.removeIf(SpellPart.EMPTY::equals); // Don't show the dummy empty part
        this.builder = builder;
        this.maxPartPage = this.allParts.size() / 27;

        addClickConsumer(event -> event.setCancelled(true));
        addCloseConsumer(this::finalizeSpell);

        populate();
        refreshOptions();
        refreshSpell();
    }

    private void populate() {
        addButton(27, ItemBuilder.create(Material.ARROW).name("Back").build(), e -> changePartIndex(-1));
        addButton(35, ItemBuilder.create(Material.ARROW).name("Next").build(), e -> changePartIndex(1));

        addButton(45, ItemBuilder.create(Material.ARROW).name("Back").build(), e -> changeSpellIndex(-1));
        addButton(53, ItemBuilder.create(Material.ARROW).name("Next").build(), e -> changeSpellIndex(1));

        addButton(48, ItemBuilder.create(Material.ARROW).name("Clear").build(), this::clearParts);
    }

    private void refreshOptions() {
        for (int i = 0; i < 27; i++) {
            int index = this.partStart + i;
            if (index >= allParts.size()) {
                setItem(i, null);
                removeButton(i);
            } else {
                SpellPart part = allParts.get(index);
                addButton(i, ItemBuilder.create(Material.PAPER).name(part.getKey().toString())
                        .persistentData(NamespacedKey.fromString("spells:part"), Spells.spellPartType, part).build(),
                        this::addPart);
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
                addButton(i + 36, ItemBuilder.create(Material.PAPER).name(part.getKey().toString())
                        .lore("Index: " + index).build(), this::removePart);
            }
        }
    }

    private void addPart(InventoryClickEvent event) {
        ItemStack item = event.getCurrentItem();

        SpellPart part = item.getItemMeta().getPersistentDataContainer().get(NamespacedKey.fromString("spells:part"),
                Spells.spellPartType);

        this.builder.addPart(part);
        refreshSpell();
    }

    private void changeSpellIndex(int amount) {
        this.spellStart = Utils.clamp(this.spellStart + amount, 0, this.builder.getSize() - 9);
        refreshSpell();
    }

    private void changePartIndex(int amount) {
        this.partStart = Utils.clamp((this.partStart / 27) + amount, 0, this.maxPartPage) * 27;
        refreshOptions();
    }

    private void removePart(InventoryClickEvent event) {
        this.builder.clearPart(this.spellStart + (event.getSlot() - 36));
        refreshSpell();
    }

    private void clearParts(InventoryClickEvent event) {
        this.builder.clearParts();
        refreshSpell();
    }

    private void finalizeSpell(InventoryCloseEvent event) {
        ItemMeta meta = this.targetItem.getItemMeta();
        meta.getPersistentDataContainer().set(NamespacedKey.fromString("spells:spell"), Spells.SPELL_TYPE,
                this.builder.toSpell());
        this.targetItem.setItemMeta(meta);
    }
}
