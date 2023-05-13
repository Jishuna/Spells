package me.jishuna.spells.spell.shape;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import me.jishuna.jishlib.config.ConfigEntry;
import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ShapePart;
import me.jishuna.spells.api.spell.target.BlockTarget;
import me.jishuna.spells.api.spell.target.EntityTarget;

public class BeamShape extends ShapePart {
    public static final BeamShape INSTANCE = new BeamShape();

    @ConfigEntry("shapes.beam.size")
    public static double BEAM_SIZE = 0.1;

    private BeamShape() {
        super(NamespacedKey.fromString("shape:beam"));
    }

    @Override
    public void cast(SpellCaster caster, World world, SpellContext context, ModifierData data, SpellExecutor resolver) {
        Location location = caster.getEntity().getEyeLocation();
        Vector velocity = location.getDirection().normalize().multiply(0.5);

        for (int i = 0; i < 10; i++) {
            for (Entity entity : world.getNearbyEntities(BoundingBox.of(location, BEAM_SIZE, BEAM_SIZE, BEAM_SIZE))) {
                if (entity == caster.getEntity()) {
                    continue;
                }

                resolver.resolve(EntityTarget.create(entity));
                return;
            }

            if (!location.getBlock().isPassable()) {
                resolver.resolve(BlockTarget.create(location.getBlock()));
                return;
            }

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
    public void castOnBlock(Block block, World world, SpellCaster caster, SpellContext context, ModifierData data,
            SpellExecutor resolver) {
        cast(caster, world, context, data, resolver);
    }
}
