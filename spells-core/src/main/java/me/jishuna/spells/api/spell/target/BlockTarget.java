package me.jishuna.spells.api.spell.target;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;

public class BlockTarget extends SpellTarget {
    private final BlockFace hitFace;

    private BlockTarget(Location location, BlockFace face, Collection<Entity> entities, Collection<Block> blocks) {
        super(location, entities, blocks);
        this.hitFace = face;
    }

    @Override
    public BlockTarget filter(Predicate<Entity> entityFilter, Predicate<Block> blockFilter) {
        Set<Entity> entities = this.entities.stream().filter(entityFilter).collect(Collectors.toSet());
        Set<Block> blocks = this.blocks.stream().filter(blockFilter).collect(Collectors.toSet());

        return new BlockTarget(getOrigin(), this.hitFace, entities, blocks);
    }

    public BlockFace getFace() {
        return hitFace;
    }

    public static BlockTarget create(Block block, BlockFace face) {
        return create(block.getLocation().add(0.5, 0.5, 0.5), Set.of(block), face, 0, 0);
    }

    public static BlockTarget create(Location location, Collection<Block> current, BlockFace face, double radius, double height) {
        Set<Block> blocks = new HashSet<>(current);
        BoundingBox bounds = BoundingBox.of(location, radius, height / 2, radius);

        blocks.addAll(SpellTarget.getBlocks(location.getWorld(), bounds));

        return new BlockTarget(location, face, location.getWorld().getNearbyEntities(bounds), blocks);
    }
}
