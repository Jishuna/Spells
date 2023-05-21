package me.jishuna.spells.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.jishuna.spells.playerdata.PlayerManager;

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

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.playerManager.onJoin(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.playerManager.removePlayer(event.getPlayer().getUniqueId());
    }
}
