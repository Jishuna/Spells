package me.jishuna.spells.api;

import me.jishuna.spells.Spells;

public class SpellsAPI {
    private static Spells plugin;

    private SpellsAPI() {
    }

    public static void initialize(Spells instance) {
        if (plugin == null) {
            plugin = instance;
        }
    }
    
    public boolean isInitialized() {
        return plugin != null;
    }
    
    

}
