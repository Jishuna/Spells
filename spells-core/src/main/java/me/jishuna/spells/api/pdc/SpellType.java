package me.jishuna.spells.api.pdc;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.jishuna.jishlib.pdc.PersistentTypes;
import me.jishuna.spells.api.SpellsAPI;
import me.jishuna.spells.api.spell.Spell;
import me.jishuna.spells.api.spell.SpellBuilder;
import me.jishuna.spells.api.spell.part.SpellPart;

public class SpellType implements PersistentDataType<PersistentDataContainer, Spell> {

    @Override
    public Class<PersistentDataContainer> getPrimitiveType() {
        return PersistentDataContainer.class;
    }

    @Override
    public Class<Spell> getComplexType() {
        return Spell.class;
    }

    @Override
    public PersistentDataContainer toPrimitive(Spell complex, PersistentDataAdapterContext context) {
        PersistentDataContainer container = context.newPersistentDataContainer();

        List<SpellPart> parts = complex.getParts();
        int index = 0;
        for (SpellPart part : parts) {
            if (part == SpellPart.EMPTY) {
                continue;
            }

            container.set(NamespacedKey.fromString("part:" + index++), SpellsAPI.SPELL_PART_TYPE, part);
        }

        int color = complex.getColor() == null ? 0xFFFFFF : complex.getColor().asRGB();
        if (color != 0xFFFFFF) {
            container.set(NamespacedKey.fromString("spell:color"), PersistentTypes.INTEGER, color);
        }

        String name = complex.getName();
        if (!name.equals("Spell")) {
            container.set(NamespacedKey.fromString("spell:name"), PersistentTypes.STRING, name);
        }

        return container;
    }

    @Override
    public Spell fromPrimitive(PersistentDataContainer primitive, PersistentDataAdapterContext context) {
        SpellBuilder builder = new SpellBuilder();
        int index = 0;

        while (true) {
            NamespacedKey key = NamespacedKey.fromString("part:" + index++);
            if (!primitive.has(key, SpellsAPI.SPELL_PART_TYPE)) {
                break;
            }

            SpellPart part = primitive.get(key, SpellsAPI.SPELL_PART_TYPE);
            builder.addPart(part);
        }

        builder.setColor(Color.fromRGB(primitive.getOrDefault(NamespacedKey.fromString("spell:color"), PersistentTypes.INTEGER, 0xFFFFFF)));
        builder.setName(primitive.getOrDefault(NamespacedKey.fromString("spell:name"), PersistentTypes.STRING, "Spell"));
        return builder.toSpell();
    }
}
