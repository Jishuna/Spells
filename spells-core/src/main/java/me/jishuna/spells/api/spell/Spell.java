package me.jishuna.spells.api.spell;

import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;

import com.google.common.collect.ImmutableList;

import me.jishuna.spells.api.spell.part.ShapePart;
import me.jishuna.spells.api.spell.part.SpellPart;

public class Spell {
    private final List<SpellPart> parts;

    private Spell(List<SpellPart> parts) {
        this.parts = parts;
    }

    public Spell getRemaining(int start) {
        List<SpellPart> subParts = Collections.emptyList();
        if (start < this.parts.size()) {
            subParts = this.parts.subList(start, this.parts.size());
        }

        return new Spell(subParts);
    }

    public ShapePart getShape() {
        if (this.parts.isEmpty()) {
            return null;
        }

        if (this.parts.get(0) instanceof ShapePart part) {
            return part;
        }

        return null;
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

    public static class Builder {
        private final ImmutableList.Builder<SpellPart> listBuilder = new ImmutableList.Builder<>();

        public Builder part(SpellPart part) {
            listBuilder.add(part);
            Bukkit.broadcastMessage(part.getKey().toString());
            return this;
        }

        public Spell build() {
            return new Spell(listBuilder.build());
        }
    }
}
