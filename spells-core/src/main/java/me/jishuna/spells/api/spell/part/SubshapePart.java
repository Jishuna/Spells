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
            return castOnEntity(entityTarget, world, caster, context, data, resolver);
        }

        if (target instanceof BlockTarget blockTarget) {
            return castOnBlock(blockTarget, world, caster, context, data, resolver);
        }

        return target;
    }

    public abstract EntityTarget castOnEntity(EntityTarget target, World world, SpellCaster caster,
            SpellContext context, ModifierData data, SpellExecutor resolver);

    public abstract BlockTarget castOnBlock(BlockTarget target, World world, SpellCaster caster, SpellContext context,
            ModifierData data, SpellExecutor resolver);

}