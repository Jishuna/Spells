package me.jishuna.spells.spell.filter.entity;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Animals;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.FilterPart;
import me.jishuna.spells.api.spell.target.SpellTarget;

public class AnimalFilter extends FilterPart {
    public static final AnimalFilter INSTANCE = new AnimalFilter();

    protected AnimalFilter() {
        super(NamespacedKey.fromString("filter:animal"));
    }

    @Override
    public void process(SpellTarget target, SpellCaster caster, SpellContext context, ModifierData data,
            SpellExecutor executor) {
        executor.setTarget(target.filter(Animals.class::isInstance, b -> true));
    }

}
