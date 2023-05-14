package me.jishuna.spells.api.spell;

import java.util.Arrays;
import java.util.List;

import me.jishuna.spells.api.spell.part.SpellPart;

public class SpellBuilder {
    private final SpellPart[] parts;

    public SpellBuilder(int size) {
        this.parts = new SpellPart[size];
        Arrays.fill(this.parts, SpellPart.EMPTY);
    }

    private SpellBuilder(List<SpellPart> parts) {
        this.parts = parts.toArray(SpellPart[]::new);
    }

    public int addPart(SpellPart newPart) {
        for (int i = 0; i < parts.length; i++) {
            SpellPart part = parts[i];
            if (part == SpellPart.EMPTY) {
                parts[i] = newPart;
                return i;
            }
        }
        return -1;
    }

    public void clearPart(int index) {
        if (index >= parts.length) {
            return;
        }

        parts[index] = SpellPart.EMPTY;
    }

    public SpellPart getPart(int index) {
        if (index >= parts.length) {
            return SpellPart.EMPTY;
        }
        return parts[index];
    }

    public int getSize() {
        return parts.length;
    }

    public Spell toSpell() {
        List<SpellPart> partList = Arrays.asList(this.parts);
        return new Spell(partList);
    }

    public static SpellBuilder modifySpell(Spell spell) {
        return new SpellBuilder(spell.getParts());
    }
}
