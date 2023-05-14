package me.jishuna.spells.api.spell.target;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;

public abstract class SpellTarget {
    private final Location origin;
    private final BoundingBox bounds;

    private Collection<Entity> entityCache;
    private Collection<Block> blockCache;

    private SpellTarget(Location origin, double radius, double height) {
        this.origin = origin;
        this.bounds = BoundingBox.of(origin, radius, height, radius);

        if (radius > 0 || height > 0) {
            this.blockCache = getBlocks();
            this.entityCache = origin.getWorld().getNearbyEntities(this.bounds);
        }
    }

    protected SpellTarget(Block block, double radius, double height) {
        this(block.getLocation().add(0.5, 0.5, 0.5), radius, height);

        if (radius <= 0 && height <= 0) {
            this.blockCache = Arrays.asList(block);
            this.entityCache = Collections.emptySet();
        }
    }

    protected SpellTarget(Entity entity, double radius, double height) {
        this(entity.getLocation(), radius, height);

        if (radius <= 0 && height <= 0) {
            this.entityCache = Arrays.asList(entity);
            this.blockCache = Collections.emptySet();
        }
    }

    public Location getOrigin() {
        return origin;
    }

    public Collection<Entity> getTargetEntities() {
        return this.entityCache;
    }

    public Collection<Block> getTargetBlocks() {
        return this.blockCache;
    }

    private Collection<Block> getBlocks() {
        Set<Block> blocks = new HashSet<>();

        for (int x = bounds.getMin().getBlockX(); x < bounds.getMax().getBlockX(); x++) {
            for (int y = bounds.getMin().getBlockY(); y < bounds.getMax().getBlockY(); y++) {
                for (int z = bounds.getMin().getBlockZ(); z < bounds.getMax().getBlockZ(); z++) {
                    blocks.add(this.origin.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }
}
