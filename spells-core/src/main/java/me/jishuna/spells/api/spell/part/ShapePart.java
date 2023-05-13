package me.jishuna.spells.api.spell.part;

import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;

public abstract class ShapePart extends SpellPart {

    protected ShapePart(NamespacedKey key) {
        super(key);
    }

    public abstract void cast(SpellCaster caster, World world, SpellContext context, ModifierData data,
            SpellExecutor resolver);

    public abstract void castOnEntity(Entity entity, World world, SpellCaster caster, SpellContext context,
            ModifierData data, SpellExecutor resolver);

    public abstract void castOnBlock(Block block, BlockFace face, World world, SpellCaster caster, SpellContext context,
            ModifierData data, SpellExecutor resolver);

}
