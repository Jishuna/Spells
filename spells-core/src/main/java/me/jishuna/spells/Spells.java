package me.jishuna.spells;

import org.bukkit.plugin.java.JavaPlugin;

import me.jishuna.spells.api.spell.part.SpellPartRegistry;

public class Spells extends JavaPlugin {
    private SpellPartRegistry spellPartRegistry;

    @Override
    public void onEnable() {
        this.spellPartRegistry = new SpellPartRegistry();

        getCommand("cast").setExecutor(new SpellCommand(this));
    }

    public SpellPartRegistry getSpellPartRegistry() {
        return spellPartRegistry;
    }
}
