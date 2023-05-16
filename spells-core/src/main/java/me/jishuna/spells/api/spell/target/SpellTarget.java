package me.jishuna.spells.api.spell.target;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;

import com.google.common.collect.ImmutableSet;

public abstract class SpellTarget {
    private final Location origin;

    protected final Collection<Entity> entities;
    protected final Collection<Block> blocks;

    protected SpellTarget(Location origin, Collection<Entity> entities, Collection<Block> blocks) {
        this.origin = origin;
        this.entities = ImmutableSet.copyOf(entities);
        this.blocks = ImmutableSet.copyOf(blocks);
    }

    public Location getOrigin() {
        return origin.clone();
    }

    public Collection<Entity> getTargetEntities() {
        return this.entities;
    }

    public Collection<Block> getTargetBlocks() {
        return this.blocks;
    }

    public SpellTarget filterEntities(Predicate<Entity> entityFilter) {
        return filter(entityFilter, b -> true);
    }

    public SpellTarget filterBlocks(Predicate<Block> blockFilter) {
        return filter(e -> true, blockFilter);
    }

    public abstract SpellTarget filter(Predicate<Entity> entityFilter, Predicate<Block> blockFilter);

    public static Collection<Block> getBlocks(World world, BoundingBox bounds) {
        Set<Block> blocks = new HashSet<>();

        for (int x = bounds.getMin().getBlockX(); x < bounds.getMax().getBlockX(); x++) {
            for (int y = bounds.getMin().getBlockY(); y < bounds.getMax().getBlockY(); y++) {
                for (int z = bounds.getMin().getBlockZ(); z < bounds.getMax().getBlockZ(); z++) {
                    blocks.add(world.getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }
}
