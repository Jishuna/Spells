package me.jishuna.spells.api.spell;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.target.BlockTarget;
import me.jishuna.spells.api.spell.target.EntityTarget;
import me.jishuna.spells.spell.shape.ProjectileShape;

public class SpellProjectile extends BukkitRunnable {
    private final SpellCaster caster;
    private Location location;
    private final Vector velocity;
    private final SpellExecutor resolver;

    public SpellProjectile(SpellCaster caster, Location location, Vector velocity, SpellExecutor resolver) {
        this.caster = caster;
        this.location = location;
        this.velocity = velocity;
        this.resolver = resolver;
    }

    @Override
    public void run() {
        for (Entity entity : location.getWorld().getNearbyEntities(BoundingBox.of(location,
                ProjectileShape.PROJECTILE_SIZE, ProjectileShape.PROJECTILE_SIZE, ProjectileShape.PROJECTILE_SIZE))) {
            if (entity == caster.getEntity()) {
                continue;
            }

            resolver.resolve(EntityTarget.create(entity));
            this.cancel();
            return;
        }

        if (!location.getBlock().isPassable()) {
            resolver.resolve(BlockTarget.create(location.getBlock()));
            this.cancel();
            return;
        }

        this.location.add(this.velocity);
        this.location.getWorld().spawnParticle(Particle.REDSTONE, location, 1, 0, 0, 0,
                new DustOptions(Color.ORANGE, 1));
    }

    public void shoot(Plugin plugin) {
        runTaskTimer(plugin, 0, 1);
    }
}