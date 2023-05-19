package me.jishuna.spells.api.spell.part;

import java.util.HashSet;
import java.util.Set;

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
    private final Set<ModifierPart> allowedModifiers = new HashSet<>();

    protected SubshapePart(NamespacedKey key, int cost) {
        super(key, cost);
    }

    public SpellTarget cast(SpellTarget target, SpellCaster caster, World world, SpellContext context, ModifierData data, SpellExecutor resolver) {
        if (target instanceof EntityTarget entityTarget) {
            return castOnEntity(entityTarget, caster, world, context, data, resolver);
        }

        if (target instanceof BlockTarget blockTarget) {
            return castOnBlock(blockTarget, caster, world, context, data, resolver);
        }

        return target;
    }

    public abstract SpellTarget castOnEntity(EntityTarget target, SpellCaster caster, World world, SpellContext context, ModifierData data, SpellExecutor resolver);

    public abstract SpellTarget castOnBlock(BlockTarget target, SpellCaster caster, World world, SpellContext context, ModifierData data, SpellExecutor resolver);

    public void addAllowedModifiers(ModifierPart... parts) {
        for (ModifierPart part : parts) {
            this.allowedModifiers.add(part);
        }
    }

    @Override
    public boolean isAllowedModifier(ModifierPart modifier) {
        return this.allowedModifiers.contains(modifier);
    }

    @Override
    public String getConfigFolder() {
        return "subshapes/";
    }
}
