package me.jishuna.spells.api.spell;

import java.util.List;

import org.bukkit.Color;

import com.google.common.collect.ImmutableList;

import me.jishuna.spells.api.spell.part.SpellPart;

public class Spell {
    private final List<SpellPart> parts;
    private final Color color;
    private final String name;

    public Spell(List<SpellPart> parts, String name, Color color) {
        this.parts = ImmutableList.copyOf(parts);
        this.name = name;
        this.color = color;
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

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
