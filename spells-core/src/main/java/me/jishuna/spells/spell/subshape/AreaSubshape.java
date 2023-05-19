package me.jishuna.spells.spell.subshape;

import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.BlockFace;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.SubshapePart;
import me.jishuna.spells.api.spell.target.BlockTarget;
import me.jishuna.spells.api.spell.target.EntityTarget;
import me.jishuna.spells.api.spell.target.SpellTarget;
import me.jishuna.spells.spell.modifier.AltModifier;
import me.jishuna.spells.spell.modifier.EmpowerModifier;
import me.jishuna.spells.spell.modifier.PierceModifier;
import net.md_5.bungee.api.ChatColor;

public class AreaSubshape extends SubshapePart {
    public static final AreaSubshape INSTANCE = new AreaSubshape();

    private AreaSubshape() {
        super(NamespacedKey.fromString("subshape:area"), 15);
        
        setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Area");
        setDefaultLore("Causes the rest of the spell to target all entities in an area. Alternate will target blocks instead. Empower will increase the radius and Pierce will increase the height of the target area.");
    
        addAllowedModifiers(AltModifier.INSTANCE, EmpowerModifier.INSTANCE, PierceModifier.INSTANCE);
    }

    @Override
    public SpellTarget cast(SpellTarget target, SpellCaster caster, World world, SpellContext context, ModifierData data, SpellExecutor resolver) {
        double radius = 1.5 + data.getEmpowerAmount();
        int height = 1 + data.getPierceAmount() * 2;
        BlockFace face = (target instanceof BlockTarget blockTarget) ? blockTarget.getFace() : BlockFace.UP;

        if (data.getCount(AltModifier.INSTANCE) > 0) {
            return BlockTarget.create(target.getOrigin(), target.getTargetBlocks(), face, radius, height);
        } else {
            return EntityTarget.create(target.getOrigin(), target.getTargetEntities(), radius, height);
        }
    }

    @Override
    public SpellTarget castOnEntity(EntityTarget target, SpellCaster caster, World world, SpellContext context, ModifierData data, SpellExecutor resolver) {
        return cast(target, caster, world, context, data, resolver);
    }

    @Override
    public SpellTarget castOnBlock(BlockTarget target, SpellCaster caster, World world, SpellContext context, ModifierData data, SpellExecutor resolver) {
        return cast(target, caster, world, context, data, resolver);
    }
}
