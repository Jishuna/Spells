package me.jishuna.spells.api.spell.caster;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public interface SpellCaster {
    
    public LivingEntity getEntity();
    
    public default Location getLocation() {
        return getEntity().getLocation();
    }
    
    public double getMana();

    public boolean hasMana(double amount);

    public void removeMana(double amount);
}
