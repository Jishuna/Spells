package me.jishuna.spells.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.jishuna.jishlib.pdc.PersistentTypes;
import me.jishuna.spells.Spells;
import me.jishuna.spells.api.NamespacedKeys;
import me.jishuna.spells.api.SpellsAPI;
import me.jishuna.spells.api.playerdata.PlayerSpellData;
import me.jishuna.spells.api.spell.Spell;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.PlayerSpellCaster;
import me.jishuna.spells.api.spell.util.SpellUtil;
import me.jishuna.spells.inventory.WandInventory;

public class CastingListener implements Listener {
    private final Map<UUID, Integer> wandSlot = new HashMap<>();

    private final Spells plugin;

    public CastingListener(Spells plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getItem() == null || event.getItem().getType() != Material.BOOK) {
            return;
        }

        Player player = event.getPlayer();
        PlayerSpellData data = plugin.getPlayerManager().getData(player.getUniqueId());
        if (data == null) {
            return;
        }

        PlayerSpellCaster caster = new PlayerSpellCaster(data);
        ItemStack item = event.getItem();

        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            ItemMeta meta = item.getItemMeta();
            Spell[] spells = meta.getPersistentDataContainer().getOrDefault(NamespacedKey.fromString("spells:spells"), SpellsAPI.SPELL_ARRAY_TYPE, new Spell[5]);

            WandInventory inventory = new WandInventory(this.plugin, data, item, spells);
            this.plugin.getInventoryManager().openInventory(player, inventory);
        } else {
            Spell spell = SpellUtil.getSpell(item);
            if (spell == null) {
                return;
            }

            SpellExecutor executor = new SpellExecutor(this.plugin, caster, spell);

            if (event.getClickedBlock() == null) {
                executor.handleCast(player.getWorld());
            } else {
                executor.handleBlockCast(event.getClickedBlock(), event.getBlockFace());
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        PlayerSpellData data = plugin.getPlayerManager().getData(player.getUniqueId());
        if (data == null) {
            return;
        }

        PlayerSpellCaster caster = new PlayerSpellCaster(data);
        ItemStack item = player.getEquipment().getItem(event.getHand());
        Spell spell = SpellUtil.getSpell(item);
        if (spell == null) {
            return;
        }

        SpellExecutor executor = new SpellExecutor(this.plugin, caster, spell);
        executor.handleEntityCast(event.getRightClicked());

        event.setCancelled(true);
    }

    @EventHandler
    public void onSelectItem(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();

        if (!player.isSneaking()) {
            return;
        }

        Integer slot = wandSlot.get(player.getUniqueId());

        if (slot != null && slot == event.getNewSlot()) {
            return;
        }

        ItemStack item = player.getInventory().getItem(event.getPreviousSlot());

        if (item == null || !item.hasItemMeta()) {
            return;
        }

        ItemMeta meta = item.getItemMeta();

        int selectedSlot = meta.getPersistentDataContainer().getOrDefault(NamespacedKeys.ACTIVE_SPELL_SLOT, PersistentTypes.INTEGER, 0);
        selectedSlot = selectedSlot + getScrollDirection(event.getPreviousSlot(), event.getNewSlot());
        if (selectedSlot < 0) {
            selectedSlot = 5 + selectedSlot;
        }
        selectedSlot %= 5;

        meta.getPersistentDataContainer().set(NamespacedKeys.ACTIVE_SPELL_SLOT, PersistentTypes.INTEGER, selectedSlot);
        item.setItemMeta(meta);

        event.setCancelled(true);
        wandSlot.put(player.getUniqueId(), event.getPreviousSlot());
        player.sendMessage(selectedSlot + "");
    }

    private int getScrollDirection(int oldSlot, int newSlot) {
        if (oldSlot == 0 && newSlot == 8) {
            return 1;
        }

        if (oldSlot == 8 && newSlot == 0) {
            return -1;
        }

        return newSlot > oldSlot ? -1 : 1;
    }
}
