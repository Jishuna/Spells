package me.jishuna.spells.api.spell;

import java.util.Collections;
import java.util.List;

import org.bukkit.Color;

import me.jishuna.spells.api.spell.part.ModifierPart;
import me.jishuna.spells.api.spell.part.SpellPart;

public class SpellContext {
    private final Spell spell;
    private int index;

    public SpellContext(Spell spell) {
        this.spell = spell;
    }

    public boolean hasPartsLeft() {
        return this.index < this.spell.getPartCount();
    }

    public Spell getNextSubspell() {
        List<SpellPart> parts = this.spell.getParts();
        SpellBuilder builder = new SpellBuilder();
        int count = 0;

        while (hasPartsLeft()) {
            SpellPart part = parts.get(this.index);

            if (!(part instanceof ModifierPart) && count > 0) {
                break;
            }

            builder.addPart(part);
            count++;
            this.index++;
        }

        return builder.toSpell();
    }
    
    public Color getSpellColor() {
        return this.spell.getColor();
    }

    public Spell getRemaining() {
        if (this.hasPartsLeft()) {
            List<SpellPart> parts = this.spell.getParts();
            return new Spell(parts.subList(index, parts.size()), this.spell.getColor());
        } else {
            return new Spell(Collections.emptyList(), this.spell.getColor());
        }
    }
}
