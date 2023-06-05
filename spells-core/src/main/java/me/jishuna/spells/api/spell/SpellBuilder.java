package me.jishuna.spells.api.spell;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;

import me.jishuna.spells.api.GlobalSettings;
import me.jishuna.spells.api.spell.part.ModifierPart;
import me.jishuna.spells.api.spell.part.SpellPart;

public class SpellBuilder {
    private final SpellPart[] parts;
    private Color color = Color.WHITE;

    public SpellBuilder() {
        this.parts = new SpellPart[GlobalSettings.MAX_PARTS];
        Arrays.fill(this.parts, SpellPart.EMPTY);
    }

    private SpellBuilder(List<SpellPart> parts, Color color) {
        this.parts = createPartsArray(parts);
        this.color = color;
    }

    public boolean addPart(SpellPart newPart) {
        for (int i = 0; i < parts.length; i++) {
            SpellPart part = parts[i];
            if (part == SpellPart.EMPTY) {
                parts[i] = newPart;
                return true;
            }
        }
        return false;
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

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public int getSize() {
        return parts.length;
    }

    public Spell toSpell() {
        List<SpellPart> partList = Arrays.asList(this.parts);
        return new Spell(partList, this.color);
    }

    private SpellPart[] createPartsArray(List<SpellPart> parts) {
        if (parts.size() > GlobalSettings.MAX_PARTS) {
            return parts.subList(0, GlobalSettings.MAX_PARTS).toArray(SpellPart[]::new);
        }
        return parts.toArray(SpellPart[]::new);
    }

    public static SpellBuilder modifySpell(Spell spell) {
        return new SpellBuilder(spell.getParts(), spell.getColor());
    }
}
