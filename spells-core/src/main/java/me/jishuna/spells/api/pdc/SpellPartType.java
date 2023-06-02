package me.jishuna.spells.api.pdc;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import me.jishuna.spells.api.registry.SpellPartRegistry;
import me.jishuna.spells.api.spell.part.SpellPart;

public class SpellPartType implements PersistentDataType<String, SpellPart> {
    private final SpellPartRegistry registry;

    public SpellPartType(SpellPartRegistry registry) {
        this.registry = registry;
    }

    @Override
    public Class<String> getPrimitiveType() {
        return String.class;
    }

    @Override
    public Class<SpellPart> getComplexType() {
        return SpellPart.class;
    }

    @Override
    public String toPrimitive(SpellPart complex, PersistentDataAdapterContext context) {
        return complex.getKey().toString();
    }

    @Override
    public SpellPart fromPrimitive(String primitive, PersistentDataAdapterContext context) {
        return registry.get(NamespacedKey.fromString(primitive));
    }

}
