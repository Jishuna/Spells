package me.jishuna.spells.api;

import me.jishuna.spells.Spells;
import me.jishuna.spells.api.pdc.SpellPartType;
import me.jishuna.spells.api.pdc.SpellType;

public class SpellsAPI {
    private static Spells plugin;
    public static final SpellType SPELL_TYPE = new SpellType();
    public static SpellPartType SPELL_PART_TYPE;

    private SpellsAPI() {
    }

    public static void initialize(Spells instance) {
        if (plugin == null) {
            plugin = instance;
            SPELL_PART_TYPE = new SpellPartType(instance.getRegistryHolder().getSpellPartRegistry());
        }
    }
    
    public boolean isInitialized() {
        return plugin != null;
    }
    
    

}
