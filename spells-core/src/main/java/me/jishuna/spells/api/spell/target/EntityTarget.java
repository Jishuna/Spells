package me.jishuna.spells.api.spell.target;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;

public class EntityTarget extends SpellTarget {
    private EntityTarget(Location location, Collection<Entity> entities, Collection<Block> blocks) {
        super(location, entities, blocks);
    }

    @Override
    public EntityTarget filter(Predicate<Entity> entityFilter, Predicate<Block> blockFilter) {
        Set<Entity> entities = this.entities.stream().filter(entityFilter).collect(Collectors.toSet());
        Set<Block> blocks = this.blocks.stream().filter(blockFilter).collect(Collectors.toSet());

        return new EntityTarget(getOrigin(), entities, blocks);
    }

    public static EntityTarget create(Entity entity) {
        return create(entity.getLocation(), Set.of(entity), 0, 0);
    }

    public static EntityTarget create(Location location, Collection<Entity> current, double radius, double height) {
        Set<Entity> entities = new HashSet<>(current);
        BoundingBox bounds = BoundingBox.of(location, radius, height / 2, radius);

        entities.addAll(location.getWorld().getNearbyEntities(bounds));

        return new EntityTarget(location, entities, SpellTarget.getBlocks(location.getWorld(), bounds));
    }
}
