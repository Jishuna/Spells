package me.jishuna.spells.api.spell.target;

import org.bukkit.entity.Entity;

public class EntityTarget extends SpellTarget {
    private final Entity originEntity;

    private EntityTarget(Entity entity, double radius, double height) {
        super(entity, radius, height);
        this.originEntity = entity;
    }

    public Entity getOriginEntity() {
        return originEntity;
    }

    public static EntityTarget create(Entity entity) {
        return new EntityTarget(entity, 0, 0);
    }

    public static EntityTarget create(Entity entity, double radius, double height) {
        return new EntityTarget(entity, radius, height);
    }
}
