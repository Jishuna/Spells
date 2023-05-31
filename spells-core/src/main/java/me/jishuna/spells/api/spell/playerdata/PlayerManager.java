package me.jishuna.spells.api.spell.playerdata;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.Bukkit;

import me.jishuna.jishlib.collections.TypedDistributionTask;
import me.jishuna.spells.Spells;

public class PlayerManager {
    private final Spells plugin;
    private final Map<UUID, PlayerSpellData> playerData = new HashMap<>();
    private final TypedDistributionTask<PlayerSpellData> disributionTask;

    public PlayerManager(Spells plugin) {
        this.plugin = plugin;
        this.disributionTask = new TypedDistributionTask<>(this::tickPlayer, data -> false, 4);
        Bukkit.getScheduler().runTaskTimer(plugin, this.disributionTask, 0, 1);
    }

    public void loadPlayer(UUID id) {
        PlayerSpellData data = new PlayerSpellData(100, 100, id, this.plugin.getStorageAdapter());
        data.loadData();

        this.playerData.put(id, data);
        this.disributionTask.addValue(data);
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
        if (data.isOnline()) {
            data.addMana(1);
            data.tick();
        }
    }
}
