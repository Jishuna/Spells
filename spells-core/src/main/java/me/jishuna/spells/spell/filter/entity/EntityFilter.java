package me.jishuna.spells.spell.filter.entity;

import org.bukkit.NamespacedKey;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.FilterPart;
import me.jishuna.spells.api.spell.target.SpellTarget;

public class EntityFilter extends FilterPart {
    public static final EntityFilter INSTANCE = new EntityFilter();

    protected EntityFilter() {
        super(NamespacedKey.fromString("filter:entity"));
    }

    @Override
    public void process(SpellTarget target, SpellCaster caster, SpellContext context, ModifierData data,
            SpellExecutor executor) {
        executor.setTarget(target.filter(e -> true, b -> false));
    }

}
