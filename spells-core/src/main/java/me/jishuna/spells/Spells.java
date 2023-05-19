package me.jishuna.spells;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.jishlib.config.ConfigurationManager;
import me.jishuna.jishlib.inventory.CustomInventoryListener;
import me.jishuna.jishlib.inventory.CustomInventoryManager;
import me.jishuna.spells.api.spell.part.SpellPartRegistry;
import me.jishuna.spells.api.spell.pdc.SpellPartType;
import me.jishuna.spells.api.spell.pdc.SpellType;
import me.jishuna.spells.playerdata.PlayerManager;

public class Spells extends JavaPlugin {
    public static final SpellType SPELL_TYPE = new SpellType();
    public static SpellPartType spellPartType;

    private SpellPartRegistry spellPartRegistry;
    private CustomInventoryManager inventoryManager;
    private ConfigurationManager configurationManager;
    private PlayerManager playerManager;

    @Override
    public void onEnable() {
        this.configurationManager = new ConfigurationManager(this);
        this.spellPartRegistry = new SpellPartRegistry(this);
        this.playerManager = new PlayerManager(this);
        this.inventoryManager = new CustomInventoryManager();

        spellPartType = new SpellPartType(this.spellPartRegistry);

        this.spellPartRegistry.initialize();

        Bukkit.getPluginManager().registerEvents(new CustomInventoryListener(this.inventoryManager), this);
        Bukkit.getPluginManager().registerEvents(new SpellListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new ConnectionListener(this.playerManager), this);

        getCommand("cast").setExecutor(new SpellCommand(this));

        for (Player player : Bukkit.getOnlinePlayers()) {
            this.playerManager.loadPlayer(player.getUniqueId());
            this.playerManager.onJoin(player);
        }
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            this.playerManager.removePlayer(player.getUniqueId());
        }
    }

    public SpellPartRegistry getSpellPartRegistry() {
        return spellPartRegistry;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public CustomInventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public ConfigurationManager getConfigurationManager() {
        return configurationManager;
    }
}
