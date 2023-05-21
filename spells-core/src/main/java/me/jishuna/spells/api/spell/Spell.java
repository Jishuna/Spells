package me.jishuna.spells.api.spell;

import java.util.List;

import com.google.common.collect.ImmutableList;

import me.jishuna.spells.api.spell.part.SpellPart;

public class Spell {
    private final List<SpellPart> parts;

    public Spell(List<SpellPart> parts) {
        this.parts = ImmutableList.copyOf(parts);
    }

    public int getTotalManaCost() {
        int cost = 0;
        for (SpellPart part : this.parts) {
            cost += part.getManaCost();
        }

        return cost;
    }

    public int getPartCount() {
        return this.parts.size();
    }

    public boolean isValid() {
        return !this.parts.isEmpty();
    }

    public List<SpellPart> getParts() {
        return parts;
    }
}
