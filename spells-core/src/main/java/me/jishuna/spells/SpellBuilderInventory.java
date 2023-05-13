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

import me.jishuna.jishlib.inventory.CustomInventory;
import me.jishuna.jishlib.items.ItemBuilder;
import me.jishuna.spells.api.spell.SpellBuilder;
import me.jishuna.spells.api.spell.part.SpellPart;
import me.jishuna.spells.api.spell.part.SpellPartRegistry;

public class SpellBuilderInventory extends CustomInventory {
    private final ItemStack targetItem;

    private final SpellBuilder builder;
    private final List<SpellPart> allParts;

    public SpellBuilderInventory(ItemStack item, SpellPartRegistry registry, SpellBuilder builder) {
        super(Bukkit.createInventory(null, 54, "Test"));
        this.targetItem = item;
        this.allParts = new ArrayList<>(registry.getAllParts());
        this.builder = builder;

        addClickConsumer(event -> event.setCancelled(true));
        addCloseConsumer(this::finalizeSpell);
        
        refreshOptions();
        refreshSpell();
    }

    public void refreshOptions() {
        int start = 0;

        for (int i = 0; i < 27; i++) {
            int index = start + i;
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

    public void refreshSpell() {
        int start = 0;

        for (int i = 0; i < 9; i++) {
            int index = start + i;
            SpellPart part = builder.getPart(index);
            if (part == SpellPart.EMPTY) {
                setItem(i + 36, null);
                removeButton(i + 36);
            } else {
                addButton(i + 36, ItemBuilder.create(Material.PAPER).name(part.getKey().toString()).build(),
                        this::removePart);
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

    private void removePart(InventoryClickEvent event) {
        int start = 0;

        this.builder.clearPart(start + (event.getSlot() - 36));
        refreshSpell();
    }

    private void finalizeSpell(InventoryCloseEvent event) {
        ItemMeta meta = this.targetItem.getItemMeta();
        meta.getPersistentDataContainer().set(NamespacedKey.fromString("spells:spell"), Spells.SPELL_TYPE,
                this.builder.toSpell());
        this.targetItem.setItemMeta(meta);
    }
}
