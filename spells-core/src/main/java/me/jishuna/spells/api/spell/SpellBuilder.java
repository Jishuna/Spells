package me.jishuna.spells.api.spell;

import java.util.Arrays;
import java.util.List;

import me.jishuna.spells.api.spell.part.ModifierPart;
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

    public void clearParts() {
        Arrays.fill(this.parts, SpellPart.EMPTY);
    }

    public SpellPart getPart(int index) {
        if (index >= parts.length || index < 0) {
            return SpellPart.EMPTY;
        }
        return parts[index];
    }

    public int getTargetIndex() {
        for (int i = 0; i < parts.length; i++) {
            SpellPart part = parts[i];
            if (part == SpellPart.EMPTY) {
                return i;
            }
        }
        return -1;
    }

    public SpellPart getLastNonModifier() {
        int index = getTargetIndex() - 1;
        SpellPart part = getPart(index);

        while (part instanceof ModifierPart) {
            part = getPart(--index);
        }
        return part;
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
