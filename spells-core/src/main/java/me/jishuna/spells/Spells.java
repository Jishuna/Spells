package me.jishuna.spells;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.jishlib.FileUtils;
import me.jishuna.jishlib.Localization;
import me.jishuna.jishlib.config.ConfigurationManager;
import me.jishuna.jishlib.inventory.CustomInventoryListener;
import me.jishuna.jishlib.inventory.CustomInventoryManager;
import me.jishuna.spells.api.spell.altar.AltarManager;
import me.jishuna.spells.api.spell.part.SpellPartRegistry;
import me.jishuna.spells.api.spell.pdc.SpellPartType;
import me.jishuna.spells.api.spell.pdc.SpellType;
import me.jishuna.spells.api.spell.playerdata.PlayerManager;
import me.jishuna.spells.command.SpellCommand;
import me.jishuna.spells.listener.AltarListener;
import me.jishuna.spells.listener.ConnectionListener;
import me.jishuna.spells.listener.SpellListeners;
import me.jishuna.spells.storage.StorageAdapter;
import me.jishuna.spells.storage.YamlStorageAdapter;

public class Spells extends JavaPlugin {
    public static final SpellType SPELL_TYPE = new SpellType();
    public static SpellPartType spellPartType;

    private SpellPartRegistry spellPartRegistry;
    private CustomInventoryManager inventoryManager;
    private ConfigurationManager configurationManager;
    private PlayerManager playerManager;
    private AltarManager altarManager;
    private StorageAdapter storageAdapter;

    @Override
    public void onEnable() {
        FileUtils.loadResource(this, "config/messages.yml").ifPresent(config -> Localization.getInstance().setConfig(config));

        this.configurationManager = new ConfigurationManager(this);
        this.spellPartRegistry = new SpellPartRegistry(this);
        this.playerManager = new PlayerManager(this);
        this.inventoryManager = new CustomInventoryManager();
        try {
            this.altarManager = new AltarManager(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.storageAdapter = YamlStorageAdapter.create(this);

        spellPartType = new SpellPartType(this.spellPartRegistry);

        this.spellPartRegistry.initialize();

        Bukkit.getPluginManager().registerEvents(new CustomInventoryListener(this.inventoryManager), this);
        Bukkit.getPluginManager().registerEvents(new SpellListeners(this), this);
        Bukkit.getPluginManager().registerEvents(new AltarListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ConnectionListener(this.playerManager), this);

        getCommand("cast").setExecutor(new SpellCommand(this));

        for (Player player : Bukkit.getOnlinePlayers()) {
            this.playerManager.loadPlayer(player.getUniqueId());
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

    public AltarManager getAltarManager() {
        return altarManager;
    }

    public StorageAdapter getStorageAdapter() {
        return storageAdapter;
    }
}
