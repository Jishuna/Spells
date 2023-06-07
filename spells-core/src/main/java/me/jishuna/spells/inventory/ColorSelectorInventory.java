package me.jishuna.spells.inventory;

import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import me.jishuna.jishlib.MessageHandler;
import me.jishuna.jishlib.Utils;
import me.jishuna.jishlib.inventory.CustomInventory;
import me.jishuna.jishlib.items.ItemBuilder;
import me.jishuna.jishlib.pdc.PersistentTypes;
import me.jishuna.spells.Spells;
import me.jishuna.spells.api.MessageKeys;

public class ColorSelectorInventory extends CustomInventory {
    private static final NamespacedKey DUMMY = NamespacedKey.fromString("spells:dummy");
    // @formatter:off
    private final ItemStack decrease = ItemBuilder.create(Material.PLAYER_HEAD)
            .name(MessageHandler.get(MessageKeys.DECREASE_NAME))
            .lore(MessageHandler.getList(MessageKeys.DECREASE_LORE))
            .skullTexture("7437346d8bda78d525d19f540a95e4e79daeda795cbc5a13256236312cf")
            .build();

    private final ItemStack increase = ItemBuilder.create(Material.PLAYER_HEAD)
            .name(MessageHandler.get(MessageKeys.INCREASE_NAME))
            .lore(MessageHandler.getList(MessageKeys.INCREASE_LORE))
            .skullTexture("3040fe836a6c2fbd2c7a9c8ec6be5174fddf1ac20f55e366156fa5f712e10")
            .build();

    private final ItemStack filler = ItemBuilder.create(Material.ORANGE_STAINED_GLASS_PANE)
            .name(" ")
            .build();

    // @formatter:on
    private final Spells plugin;
    private final SpellBuilderInventory parent;

    private int red;
    private int blue;
    private int green;

    public ColorSelectorInventory(Spells plugin, SpellBuilderInventory parent, Color color) {
        super(Bukkit.createInventory(null, 27, MessageHandler.get(MessageKeys.COLOR_GUI_TITLE)));

        this.plugin = plugin;
        this.parent = parent;

        this.red = color.getRed();
        this.blue = color.getBlue();
        this.green = color.getGreen();

        addClickConsumer(event -> event.setCancelled(true));
        addCloseConsumer(this::setColor);

        populate();
        refreshDisplayItems();
    }

    private void populate() {
        for (int i = 0; i < 27; i++) {
            setItem(i, this.filler);
        }

        addButton(1, ItemBuilder.modifyItem(this.increase).persistentData(DUMMY, PersistentTypes.INTEGER, 1).build(), event -> changeColor(1, event.getClick(), () -> this.red, val -> this.red = val));
        addButton(3, ItemBuilder.modifyItem(this.increase).persistentData(DUMMY, PersistentTypes.INTEGER, 2).build(), event -> changeColor(1, event.getClick(), () -> this.green, val -> this.green = val));
        addButton(5, ItemBuilder.modifyItem(this.increase).persistentData(DUMMY, PersistentTypes.INTEGER, 3).build(), event -> changeColor(1, event.getClick(), () -> this.blue, val -> this.blue = val));

        addButton(19, ItemBuilder.modifyItem(this.decrease).persistentData(DUMMY, PersistentTypes.INTEGER, 1).build(), event -> changeColor(-1, event.getClick(), () -> this.red, val -> this.red = val));
        addButton(21, ItemBuilder.modifyItem(this.decrease).persistentData(DUMMY, PersistentTypes.INTEGER, 2).build(), event -> changeColor(-1, event.getClick(), () -> this.green, val -> this.green = val));
        addButton(23, ItemBuilder.modifyItem(this.decrease).persistentData(DUMMY, PersistentTypes.INTEGER, 3).build(), event -> changeColor(-1, event.getClick(), () -> this.blue, val -> this.blue = val));
    }

    private void changeColor(int amount, ClickType type, IntSupplier supplier, IntConsumer consumer) {
        int value = supplier.getAsInt();

        switch (type) {
        case RIGHT -> value += amount * 5;
        case SHIFT_LEFT -> value += amount * 10;
        case SHIFT_RIGHT -> value += amount * 50;
        case MIDDLE -> value += amount * 100;
        default -> value += amount;
        }

        consumer.accept(Utils.clamp(value, 0, 255));
        refreshDisplayItems();
    }

    private void refreshDisplayItems() {
        Color color = Color.fromRGB(this.red, this.green, this.blue);

        setItem(10, ItemBuilder.create(Material.LEATHER_CHESTPLATE).name(MessageHandler.get(MessageKeys.RED, this.red)).modify(LeatherArmorMeta.class, meta -> meta.setColor(Color.fromRGB(this.red, 0, 0))).flags(ItemFlag.HIDE_DYE, ItemFlag.HIDE_ATTRIBUTES).build());

        setItem(12, ItemBuilder.create(Material.LEATHER_CHESTPLATE).name(MessageHandler.get(MessageKeys.GREEN, this.green)).modify(LeatherArmorMeta.class, meta -> meta.setColor(Color.fromRGB(0, this.green, 0))).flags(ItemFlag.HIDE_DYE, ItemFlag.HIDE_ATTRIBUTES).build());

        setItem(14, ItemBuilder.create(Material.LEATHER_CHESTPLATE).name(MessageHandler.get(MessageKeys.BLUE, this.blue)).modify(LeatherArmorMeta.class, meta -> meta.setColor(Color.fromRGB(0, 0, this.blue))).flags(ItemFlag.HIDE_DYE, ItemFlag.HIDE_ATTRIBUTES).build());

        setItem(16, ItemBuilder.create(Material.LEATHER_CHESTPLATE).name(MessageHandler.get(MessageKeys.CURRENT_COLOR)).modify(LeatherArmorMeta.class, meta -> meta.setColor(color)).flags(ItemFlag.HIDE_DYE, ItemFlag.HIDE_ATTRIBUTES).build());
    }

    private void setColor(InventoryCloseEvent event) {
        Color color = Color.fromRGB(this.red, this.green, this.blue);
        this.parent.setColor(color);

        Bukkit.getScheduler().runTask(this.plugin, () -> this.plugin.getInventoryManager().openInventory(event.getPlayer(), this.parent));
    }
}
