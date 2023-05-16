package me.jishuna.spells.api.spell.part;

import org.bukkit.NamespacedKey;
import org.bukkit.World;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.target.BlockTarget;
import me.jishuna.spells.api.spell.target.EntityTarget;
import me.jishuna.spells.api.spell.target.SpellTarget;

public abstract class SubshapePart extends SpellPart {

    protected SubshapePart(NamespacedKey key) {
        super(key);
    }

    public SpellTarget cast(SpellTarget target, SpellCaster caster, World world, SpellContext context,
            ModifierData data, SpellExecutor resolver) {
        if (target instanceof EntityTarget entityTarget) {
            return castOnEntity(entityTarget, caster, world, context, data, resolver);
        }

        if (target instanceof BlockTarget blockTarget) {
            return castOnBlock(blockTarget, caster, world, context, data, resolver);
        }

        return target;
    }

    public abstract SpellTarget castOnEntity(EntityTarget target, SpellCaster caster, World world, SpellContext context,
            ModifierData data, SpellExecutor resolver);

    public abstract SpellTarget castOnBlock(BlockTarget target, SpellCaster caster, World world, SpellContext context,
            ModifierData data, SpellExecutor resolver);

}
