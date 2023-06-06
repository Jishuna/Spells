package me.jishuna.spells;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.jishlib.MessageHandler;
import me.jishuna.jishlib.config.ConfigReloadable;
import me.jishuna.jishlib.config.ConfigurationManager;
import me.jishuna.jishlib.inventory.CustomInventoryListener;
import me.jishuna.jishlib.inventory.CustomInventoryManager;
import me.jishuna.spells.api.GlobalSettings;
import me.jishuna.spells.api.SpellsAPI;
import me.jishuna.spells.api.altar.AltarManager;
import me.jishuna.spells.api.altar.recipe.ingredient.AltarRecipeIngredient;
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

    private RegistryHolder registryHolder;
    private CustomInventoryManager inventoryManager;
    private ConfigurationManager configurationManager;
    private PlayerManager playerManager;
    private AltarManager altarManager;
    private StorageAdapter storageAdapter;

    @Override
    public void onEnable() {
        loadConfigs();

        this.registryHolder = new RegistryHolder(this);
        this.playerManager = new PlayerManager(this);
        this.inventoryManager = new CustomInventoryManager();
        this.altarManager = new AltarManager(this);
        this.storageAdapter = YamlStorageAdapter.create(this);

        SpellsAPI.initialize(this);

        this.registryHolder.initialize();

        registerEvents();

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

    private void loadConfigs() {
        this.configurationManager = new ConfigurationManager(this);
        this.configurationManager.registerTypeAdapter(AltarRecipeIngredient.class, new RecipeIngredientAdapter());

        ConfigReloadable<GlobalSettings> globalSettings = this.configurationManager.createStaticReloadable(new File(getDataFolder(), "config/general.yml"), GlobalSettings.class);
        globalSettings.saveDefaults().load();

        MessageHandler.initalize(this.configurationManager, new File(getDataFolder(), "config/messages.yml"), this.getResource("config/messages.yml"));
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
