package me.jishuna.spells;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.jishlib.FileUtils;
import me.jishuna.jishlib.Localization;
import me.jishuna.jishlib.config.ConfigurationManager;
import me.jishuna.jishlib.inventory.CustomInventoryListener;
import me.jishuna.jishlib.inventory.CustomInventoryManager;
import me.jishuna.spells.api.SpellsAPI;
import me.jishuna.spells.api.altar.AltarManager;
import me.jishuna.spells.api.pdc.SpellPartType;
import me.jishuna.spells.api.pdc.SpellType;
import me.jishuna.spells.api.playerdata.PlayerManager;
import me.jishuna.spells.api.registry.RegistryHolder;
import me.jishuna.spells.command.SpellCommand;
import me.jishuna.spells.listener.AltarListener;
import me.jishuna.spells.listener.CastingListener;
import me.jishuna.spells.listener.ConnectionListener;
import me.jishuna.spells.listener.PartUnlockListener;
import me.jishuna.spells.storage.StorageAdapter;
import me.jishuna.spells.storage.YamlStorageAdapter;

public class Spells extends JavaPlugin {
    public static final SpellType SPELL_TYPE = new SpellType();
    public static SpellPartType spellPartType;

    private RegistryHolder registryHolder;
    private CustomInventoryManager inventoryManager;
    private ConfigurationManager configurationManager;
    private PlayerManager playerManager;
    private AltarManager altarManager;
    private StorageAdapter storageAdapter;

    @Override
    public void onEnable() {
        FileUtils.loadResource(this, "config/messages.yml").ifPresent(config -> Localization.getInstance().setConfig(config));

        this.configurationManager = new ConfigurationManager(this);
        this.registryHolder = new RegistryHolder(this);
        this.playerManager = new PlayerManager(this);
        this.inventoryManager = new CustomInventoryManager();
        try {
            this.altarManager = new AltarManager(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.storageAdapter = YamlStorageAdapter.create(this);

        spellPartType = new SpellPartType(this.registryHolder.getSpellPartRegistry());

        this.registryHolder.getSpellPartRegistry().initialize();

        registerEvents();

        getCommand("cast").setExecutor(new SpellCommand(this));

        SpellsAPI.initialize(this);

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

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new CustomInventoryListener(this.inventoryManager), this);
        pm.registerEvents(new CastingListener(this), this);
        pm.registerEvents(new AltarListener(this), this);
        pm.registerEvents(new PartUnlockListener(this.playerManager), this);
        pm.registerEvents(new ConnectionListener(this.playerManager), this);
    }

    public RegistryHolder getRegistryHolder() {
        return registryHolder;
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
