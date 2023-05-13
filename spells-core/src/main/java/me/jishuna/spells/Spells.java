package me.jishuna.spells;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.jishlib.inventory.CustomInventoryListener;
import me.jishuna.jishlib.inventory.CustomInventoryManager;
import me.jishuna.spells.api.spell.part.SpellPartRegistry;
import me.jishuna.spells.api.spell.pdc.SpellPartType;
import me.jishuna.spells.api.spell.pdc.SpellType;

public class Spells extends JavaPlugin {
    public static final SpellType SPELL_TYPE = new SpellType();
    public static SpellPartType spellPartType;

    private SpellPartRegistry spellPartRegistry;
    private CustomInventoryManager inventoryManager;

    @Override
    public void onEnable() {
        this.spellPartRegistry = new SpellPartRegistry();
        this.inventoryManager = new CustomInventoryManager();

        Bukkit.getPluginManager().registerEvents(new CustomInventoryListener(this.inventoryManager), this);
        Bukkit.getPluginManager().registerEvents(new SpellListeners(this), this);

        getCommand("cast").setExecutor(new SpellCommand(this));
        getCommand("build").setExecutor(new BuildCommand(this));

        spellPartType = new SpellPartType(this.spellPartRegistry);
    }

    public SpellPartRegistry getSpellPartRegistry() {
        return spellPartRegistry;
    }

    public CustomInventoryManager getInventoryManager() {
        return inventoryManager;
    }
}
