package me.jishuna.spells.api.spell.target;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import com.google.common.collect.ImmutableList;

public class EntityTarget implements SpellTarget {
    private final List<LivingEntity> entities;
    private final Location origin;

    private EntityTarget(List<LivingEntity> entities, Location origin) {
        this.entities = entities;
        this.origin = origin;
    }

    public List<LivingEntity> getEntities() {
        return entities;
    }

    public Location getOrigin() {
        return origin;
    }

    public static EntityTarget single(LivingEntity entity, Location origin) {
        return new EntityTarget(Arrays.asList(entity), origin);
    }

    public static class Builder {
        private final ImmutableList.Builder<LivingEntity> listBuilder = new ImmutableList.Builder<>();
        private Location origin;

        public Builder add(LivingEntity entity) {
            listBuilder.add(entity);
            return this;
        }

        public Builder addAll(Collection<LivingEntity> entities) {
            listBuilder.addAll(entities);
            return this;
        }

        public Builder origin(Location origin) {
            this.origin = origin;
            return this;
        }

        public EntityTarget build() {
            return new EntityTarget(listBuilder.build(), this.origin);
        }
    }
}
