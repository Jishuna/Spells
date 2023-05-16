package me.jishuna.spells.api.spell.caster;

import org.bukkit.entity.LivingEntity;

import me.jishuna.spells.playerdata.PlayerSpellData;

public class PlayerSpellCaster implements SpellCaster {
    private final PlayerSpellData data;

    public PlayerSpellCaster(PlayerSpellData data) {
        this.data = data;
    }

    @Override
    public LivingEntity getEntity() {
        return this.data.getPlayer();
    }

    @Override
    public double getMana() {
        return this.data.getMana();
    }
    
    @Override
    public boolean hasMana(double amount) {
        return this.data.hasMana(amount);
    }
    
    @Override
    public void removeMana(double amount) {
        this.data.removeMana(amount);
    }

}
