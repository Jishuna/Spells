package me.jishuna.spells.api.spell;

import org.bukkit.Color;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.target.BlockTarget;
import me.jishuna.spells.api.spell.target.EntityTarget;

public class SpellProjectile extends BukkitRunnable {
    private final SpellCaster caster;
    private Location location;
    private final Vector velocity;
    private final SpellExecutor resolver;
    private final double size;
    private final Color color;

    private int length;
    private volatile boolean running = false;

    public SpellProjectile(SpellCaster caster, Location location, Vector velocity, SpellExecutor resolver, Color color, double size, int length) {
        this.caster = caster;
        this.location = location;
        this.velocity = velocity;
        this.resolver = resolver;
        this.size = size;
        this.color = color;
        this.length = length;
    }

    @Override
    public void run() {
        if (this.length-- <= 0) {
            this.cancel();
        }

        RayTraceResult result = location.getWorld().rayTrace(location, velocity, 1, FluidCollisionMode.NEVER, true, this.size, e -> e != caster.getEntity());

        if (result != null) {
            Entity entity = result.getHitEntity();
            Block block = result.getHitBlock();
            if (entity != null) {
                resolver.execute(EntityTarget.create(entity));
            } else if (block != null) {
                resolver.execute(BlockTarget.create(block, result.getHitBlockFace()));
            }
            this.cancel();
        }

        this.location.add(this.velocity);
        this.location.getWorld().spawnParticle(Particle.REDSTONE, location, 3, 0.05, 0.05, 0.05, new DustOptions(this.color, 1));
    }

    @Override
    public synchronized void cancel() {
        if (running) {
            super.cancel();
        }
    }

    public void runTask(Plugin plugin, int delay, int period) {
        runTaskTimer(plugin, delay, period);
        running = true;
    }
}