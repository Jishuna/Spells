package me.jishuna.spells.api.spell;

import java.util.List;

import me.jishuna.spells.api.spell.part.ModifierPart;
import me.jishuna.spells.api.spell.part.SpellPart;

public class SpellContext {
    private final Spell spell;
    private int index;
    private boolean cancelled = false;

    public SpellContext(Spell spell) {
        this.spell = spell;
    }

    public boolean hasPartsLeft() {
        return this.index < this.spell.getPartCount();
    }

    public boolean isValid() {
        return !this.cancelled && hasPartsLeft();
    }

    public void cancel() {
        this.cancelled = true;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public Spell getNextSubspell() {
        List<SpellPart> parts = this.spell.getParts();
        Spell.Builder builder = new Spell.Builder();
        int count = 0;

        while (this.index < parts.size()) {
            SpellPart part = parts.get(this.index);

            if (!(part instanceof ModifierPart) && count > 0) {
                break;
            }

            builder.part(part);
            count++;
            this.index++;
        }

        return builder.build();
    }
}
