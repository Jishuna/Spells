package me.jishuna.spells.api.spell.caster;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public interface SpellCaster {
    
    public LivingEntity getEntity();
    
    public default Location getLocation() {
        return getEntity().getLocation();
    }
    
    public int getMana();
}
