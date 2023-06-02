package me.jishuna.spells.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import me.jishuna.spells.api.playerdata.PlayerManager;

import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionListener implements Listener {
    private final PlayerManager playerManager;

    public ConnectionListener(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPrelogin(AsyncPlayerPreLoginEvent event) {
        if (event.getLoginResult() == Result.ALLOWED) {
            this.playerManager.loadPlayer(event.getUniqueId());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        this.playerManager.removePlayer(event.getPlayer().getUniqueId());
    }
}
