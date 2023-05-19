package me.jishuna.spells.api.spell.part;

import org.bukkit.NamespacedKey;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.target.SpellTarget;

public abstract class FilterPart extends SpellPart {

    protected FilterPart(NamespacedKey key, int cost) {
        super(key, cost);
    }

    public abstract void process(SpellTarget target, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor executor);

    @Override
    public boolean isAllowedModifier(ModifierPart modifier) {
        return false; // Filters cannot have modifiers
    }

    @Override
    public String getConfigFolder() {
        return "filters/";
    }
}
