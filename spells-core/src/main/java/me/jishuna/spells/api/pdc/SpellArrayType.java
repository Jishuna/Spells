package me.jishuna.spells.api.pdc;

import java.util.Collections;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.jishuna.spells.api.SpellsAPI;
import me.jishuna.spells.api.spell.Spell;

public class SpellArrayType implements PersistentDataType<PersistentDataContainer[], Spell[]> {

    @Override
    public Class<PersistentDataContainer[]> getPrimitiveType() {
        return PersistentDataContainer[].class;
    }

    @Override
    public Class<Spell[]> getComplexType() {
        return Spell[].class;
    }

    @Override
    public PersistentDataContainer[] toPrimitive(Spell[] complex, PersistentDataAdapterContext context) {
        PersistentDataContainer[] containers = new PersistentDataContainer[complex.length];
        int index = 0;

        for (Spell spell : complex) {
            PersistentDataContainer container;
            if (spell == null) {
                container = context.newPersistentDataContainer();
            } else {
                container = SpellsAPI.SPELL_TYPE.toPrimitive(spell, context);
            }
            
            containers[index++] = container;
        }
        return containers;
    }

    @Override
    public Spell[] fromPrimitive(PersistentDataContainer[] primitive, PersistentDataAdapterContext context) {
        Spell[] spells = new Spell[primitive.length];
        int index = 0;

        for (PersistentDataContainer container : primitive) {
            Spell spell;
            if (container == null) {
                spell = new Spell(Collections.emptyList(), "", null);
            } else {
                spell = SpellsAPI.SPELL_TYPE.fromPrimitive(container, context);
            }

            spells[index++] = spell;
        }
        return spells;
    }
}
