package me.jishuna.spells.playerdata;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.jishuna.jishlib.collections.TypedDistributionTask;

public class PlayerManager {

    private final Map<UUID, PlayerSpellData> playerData = new HashMap<>();
    private final TypedDistributionTask<PlayerSpellData> disributionTask;

    public PlayerManager(Plugin plugin) {
        this.disributionTask = new TypedDistributionTask<>(this::tickPlayer, data -> false, 4);
        Bukkit.getScheduler().runTaskTimer(plugin, this.disributionTask, 0, 1);
    }

    public void loadPlayer(UUID id) {
        this.playerData.put(id, new PlayerSpellData(100, 100));
    }

    public void onJoin(Player player) {
        findData(player.getUniqueId()).ifPresent(data -> this.disributionTask.addValue(data.withPlayer(player)));
    }

    public void removePlayer(UUID id) {
        this.playerData.remove(id);
    }

    public PlayerSpellData getData(UUID id) {
        return this.playerData.get(id);
    }

    public Optional<PlayerSpellData> findData(UUID id) {
        return Optional.ofNullable(getData(id));
    }

    private void tickPlayer(PlayerSpellData data) {
        data.addMana(1);
        data.tick();
    }
}
