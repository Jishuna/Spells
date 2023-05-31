package me.jishuna.spells.listener;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.jishuna.jishlib.LocationUtils;
import me.jishuna.spells.Spells;
import me.jishuna.spells.api.spell.altar.AltarManager;

public class AltarListener implements Listener {
    private final Spells plugin;

    public AltarListener(Spells plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAltarInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        AltarManager manager = this.plugin.getAltarManager();
        Location center = LocationUtils.centerLocation(event.getClickedBlock().getLocation(), false);
        if (manager.isAltarInUse(center)) {
            event.getPlayer().sendMessage("in use");
            return;
        }

        if (!manager.isValidStructure(center)) {
            return;
        }

        event.getPlayer().sendMessage("altar");
        manager.startAltarTask(event.getPlayer(), center);
    }
}
