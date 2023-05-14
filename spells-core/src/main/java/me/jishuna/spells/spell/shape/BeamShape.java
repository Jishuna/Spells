package me.jishuna.spells.spell.shape;

import org.bukkit.Color;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ShapePart;
import me.jishuna.spells.api.spell.target.BlockTarget;
import me.jishuna.spells.api.spell.target.EntityTarget;

public class BeamShape extends ShapePart {
    public static final BeamShape INSTANCE = new BeamShape();

    @Comment("The size of the beam, higher numbers make the beam more lenient when checking for collision.")
    @ConfigEntry("shapes.beam.size")
    public static double BEAM_SIZE = 0.1;

    @Comment("The bonus length of the beam per empower modifier.")
    @ConfigEntry("shapes.beam.bonus-length")
    public static int BONUS_LENGTH = 3;

    private BeamShape() {
        super(NamespacedKey.fromString("shape:beam"));
    }

    @Override
    public void cast(SpellCaster caster, World world, SpellContext context, ModifierData data, SpellExecutor resolver) {
        Location location = caster.getEntity().getEyeLocation();
        Vector velocity = location.getDirection().normalize();

        int length = 5 + (BONUS_LENGTH * data.getEmpowerAmount());

        RayTraceResult result = location.getWorld().rayTrace(location, velocity, length, FluidCollisionMode.NEVER, true,
                BEAM_SIZE, e -> e != caster.getEntity());

        if (result != null) {
            Entity entity = result.getHitEntity();
            Block block = result.getHitBlock();
            if (entity != null) {
                resolver.resolve(EntityTarget.create(entity));
            } else if (block != null) {
                resolver.resolve(BlockTarget.create(block, result.getHitBlockFace()));
            }
        }

        for (int i = 0; i < length; i++) {
            location.add(velocity);
            world.spawnParticle(Particle.REDSTONE, location, 3, 0.05, 0.05, 0.05, new DustOptions(Color.ORANGE, 1));
        }
    }

    @Override
    public void castOnEntity(Entity entity, World world, SpellCaster caster, SpellContext context, ModifierData data,
            SpellExecutor resolver) {
        cast(caster, world, context, data, resolver);
    }

    @Override
    public void castOnBlock(Block block, BlockFace face, World world, SpellCaster caster, SpellContext context,
            ModifierData data, SpellExecutor resolver) {
        cast(caster, world, context, data, resolver);
    }
}
