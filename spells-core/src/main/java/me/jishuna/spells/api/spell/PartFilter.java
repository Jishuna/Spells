package me.jishuna.spells.api.spell;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableList;

import me.jishuna.spells.api.spell.part.ModifierPart;
import me.jishuna.spells.api.spell.part.ShapePart;
import me.jishuna.spells.api.spell.part.SpellPart;

public class PartFilter {
    private final List<SpellPart> parts;

    private PartFilter(Collection<SpellPart> parts) {
        this.parts = ImmutableList.copyOf(parts);
    }

    public List<SpellPart> getParts() {
        return parts;
    }

    public static PartFilter create(Collection<SpellPart> parts, SpellBuilder builder) {
        List<SpellPart> filteredParts = new ArrayList<>(parts);
        if (builder.getTargetIndex() == 0) {
            filteredParts = filterParts(filteredParts, ShapePart.class::isInstance);
        } else {
            filteredParts = filterParts(filteredParts, Predicate.not(ShapePart.class::isInstance));
        }

        SpellPart lastPart = builder.getLastNonModifier();
        filteredParts = filterParts(filteredParts, part -> !(part instanceof ModifierPart modifierPart) || lastPart.isAllowedModifier(modifierPart));

        return new PartFilter(filteredParts);
    }

    private static List<SpellPart> filterParts(Collection<SpellPart> parts, Predicate<SpellPart> filter) {
        return parts.stream().filter(filter).toList();
    }

}
