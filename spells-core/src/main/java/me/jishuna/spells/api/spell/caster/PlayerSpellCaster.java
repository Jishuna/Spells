package me.jishuna.spells.api.spell.caster;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class PlayerSpellCaster implements SpellCaster {
    private final Player player;

    public PlayerSpellCaster(Player player) {
        this.player = player;
    }

    @Override
    public LivingEntity getEntity() {
        return this.player;
    }

    @Override
    public int getMana() {
        return 100;
    }

}
