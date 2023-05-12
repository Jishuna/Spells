package me.jishuna.spells.api.spell.target;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Entity;

public class EntityTarget extends SpellTarget {
    private final Entity originEntity;

    private EntityTarget(Entity entity, double radius) {
        super(entity.getLocation(), radius);
        this.originEntity = entity;
    }

    @Override
    public Collection<Entity> getTargetEntities() {
        Set<Entity> entities = new HashSet<>(super.getTargetEntities());
        entities.add(this.originEntity);

        return entities;
    }

    public Entity getOriginEntity() {
        return originEntity;
    }

    public static EntityTarget create(Entity entity) {
        return new EntityTarget(entity, 0);
    }

    public static EntityTarget create(Entity entity, double radius) {
        return new EntityTarget(entity, radius);
    }
}
