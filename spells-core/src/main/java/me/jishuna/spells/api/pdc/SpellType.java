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
        for (int i = 0; i < parts.size(); i++) {
            SpellPart part = parts.get(i);

            container.set(NamespacedKey.fromString("part:" + i), SpellsAPI.SPELL_PART_TYPE, part);
        }
        container.set(NamespacedKey.fromString("spell:color"), PersistentTypes.INTEGER, complex.getColor().asRGB());
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
        return builder.toSpell();
    }
}
