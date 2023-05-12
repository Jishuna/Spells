package me.jishuna.spells.api.spell.target;

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

    protected SpellTarget(Location origin, double radius) {
        this.origin = origin;
        if (radius > 0) {
            this.bounds = BoundingBox.of(origin, radius, radius, radius);
        } else {
            this.bounds = null;
        }
    }

    public Location getOrigin() {
        return origin;
    }

    public Collection<Entity> getTargetEntities() {
        if (bounds == null) {
            return Collections.emptySet();
        }

        if (this.entityCache == null) {
            this.entityCache = origin.getWorld().getNearbyEntities(this.bounds);
        }

        return this.entityCache;
    }

    public Collection<Block> getTargetBlocks() {
        if (bounds == null) {
            return Collections.emptySet();
        }

        if (this.blockCache == null) {
            this.blockCache = getBlocks();
        }

        return this.blockCache;
    }

    private Collection<Block> getBlocks() {
        Set<Block> blocks = new HashSet<>();

        for (int x = bounds.getMin().getBlockX(); x <= bounds.getMax().getBlockX(); x++) {
            for (int y = bounds.getMin().getBlockY(); y <= bounds.getMax().getBlockY(); y++) {
                for (int z = bounds.getMin().getBlockZ(); z <= bounds.getMax().getBlockZ(); z++) {
                    blocks.add(this.origin.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }
}
