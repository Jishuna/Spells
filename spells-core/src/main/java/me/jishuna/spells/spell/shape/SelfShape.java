package me.jishuna.spells.spell.shape;

import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ShapePart;
import me.jishuna.spells.api.spell.target.EntityTarget;

public class SelfShape extends ShapePart {
    public static final SelfShape INSTANCE = new SelfShape();

    private SelfShape() {
        super(NamespacedKey.fromString("spells:shape_self"));
    }

    @Override
    public void cast(SpellCaster caster, World world, SpellContext context, ModifierData data, SpellExecutor resolver) {
        resolver.resolve(EntityTarget.single(caster.getEntity(), caster.getLocation()));
    }

    @Override
    public void castOnEntity(LivingEntity entity, World world, SpellCaster caster, SpellContext context,
            ModifierData data, SpellExecutor resolver) {
        cast(caster, world, context, data, resolver);
    }

    @Override
    public void castOnBlock(Block block, World world, SpellCaster caster, SpellContext context, ModifierData data,
            SpellExecutor resolver) {
        cast(caster, world, context, data, resolver);
    }

}
