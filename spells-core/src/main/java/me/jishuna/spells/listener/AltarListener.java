package me.jishuna.spells.listener;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import me.jishuna.jishlib.LocationUtils;
import me.jishuna.spells.Spells;
import me.jishuna.spells.api.altar.AltarManager;
import me.jishuna.spells.inventory.AltarRecipeInventory;

public class AltarListener implements Listener {
    private final Spells plugin;

    public AltarListener(Spells plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAltarInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        AltarManager manager = this.plugin.getAltarManager();
        Location center = LocationUtils.centerLocation(event.getClickedBlock().getLocation(), false);
        if (manager.isAltarInUse(center)) {
            return;
        }

        if (!manager.isValidStructure(center)) {
            return;
        }

        AltarRecipeInventory inventory = new AltarRecipeInventory(this.plugin, event.getPlayer(), center);
        this.plugin.getInventoryManager().openInventory(event.getPlayer(), inventory);
    }
}
