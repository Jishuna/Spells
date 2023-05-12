package me.jishuna.spells.spell.subshape;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

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
        super(NamespacedKey.fromString("spells:subshape_area"));
    }

    @Override
    public EntityTarget castOnEntity(EntityTarget target, World world, SpellCaster caster, SpellContext context,
            ModifierData data, SpellExecutor resolver) {
        int radius = 1 + data.getEmpowerAmount();

        EntityTarget.Builder builder = new EntityTarget.Builder().origin(target.getOrigin());

        for (Entity entity : world.getNearbyEntities(target.getOrigin(), radius, radius, radius)) {
            if (entity instanceof LivingEntity living) {
                builder.add(living);
            }
        }
        return builder.build();
    }

    @Override
    public BlockTarget castOnBlock(BlockTarget target, World world, SpellCaster caster, SpellContext context,
            ModifierData data, SpellExecutor resolver) {
        int radius = 1 + data.getEmpowerAmount();
        Location origin = target.getOrigin();

        BlockTarget.Builder builder = new BlockTarget.Builder().origin(origin);

        for (int x = origin.getBlockX() - radius; x <= origin.getBlockX() + radius; x++) {
            for (int y = origin.getBlockY() - radius; y <= origin.getBlockY() + radius; y++) {
                for (int z = origin.getBlockZ() - radius; z <= origin.getBlockZ() + radius; z++) {
                    builder.add(world.getBlockAt(x, y, z));
                }
            }
        }
        return builder.build();
    }
}
