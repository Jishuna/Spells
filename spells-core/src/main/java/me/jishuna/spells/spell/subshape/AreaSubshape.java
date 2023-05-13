package me.jishuna.spells.spell.subshape;

import org.bukkit.NamespacedKey;
import org.bukkit.World;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.SubshapePart;
import me.jishuna.spells.api.spell.target.BlockTarget;
import me.jishuna.spells.api.spell.target.EntityTarget;

public class AreaSubshape extends SubshapePart {
    public static final AreaSubshape INSTANCE = new AreaSubshape();

    private AreaSubshape() {
        super(NamespacedKey.fromString("subshape:area"));
    }

    @Override
    public EntityTarget castOnEntity(EntityTarget target, World world, SpellCaster caster, SpellContext context,
            ModifierData data, SpellExecutor resolver) {
        int radius = 1 + data.getEmpowerAmount();
        int height = 1;

        return EntityTarget.create(target.getOriginEntity(), radius, height / 2d);
    }

    @Override
    public BlockTarget castOnBlock(BlockTarget target, World world, SpellCaster caster, SpellContext context,
            ModifierData data, SpellExecutor resolver) {
        int radius = 1 + data.getEmpowerAmount();
        int height = 1;

        return BlockTarget.create(target.getOriginBlock(), radius, height / 2d);
    }
}
