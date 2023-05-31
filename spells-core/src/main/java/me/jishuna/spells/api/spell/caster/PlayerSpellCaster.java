package me.jishuna.spells.api.spell.caster;

import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;

import me.jishuna.spells.api.spell.playerdata.PlayerSpellData;

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
        if (data.getPlayer().getGameMode() == GameMode.CREATIVE) { // Don't require mana in creative mode.
            return true;
        }
        return this.data.hasMana(amount);
    }

    @Override
    public void removeMana(double amount) {
        if (data.getPlayer().getGameMode() == GameMode.CREATIVE) { // Don't consume mana in creative mode.
            return;
        }
        this.data.removeMana(amount);
    }

}
