package me.jishuna.spells.spell.filter.entity;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.FilterPart;
import me.jishuna.spells.api.spell.target.SpellTarget;

public class PlayerFilter extends FilterPart {
    public static final PlayerFilter INSTANCE = new PlayerFilter();

    protected PlayerFilter() {
        super(NamespacedKey.fromString("filter:player"));
    }

    @Override
    public void process(SpellTarget target, SpellCaster caster, SpellContext context, ModifierData data,
            SpellExecutor executor) {
        executor.setTarget(target.filter(Player.class::isInstance, b -> true));
    }

}
