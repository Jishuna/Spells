package me.jishuna.spells.api.spell.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import me.jishuna.spells.api.SpellsAPI;
import me.jishuna.spells.api.spell.Spell;
import me.jishuna.spells.api.spell.SpellBuilder;
import me.jishuna.spells.api.spell.part.ModifierPart;
import me.jishuna.spells.api.spell.part.ShapePart;
import me.jishuna.spells.api.spell.part.SpellPart;

public class SpellUtil {
    public static Spell getSpell(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return null;
        }

        return item.getItemMeta().getPersistentDataContainer().get(NamespacedKey.fromString("spells:spell"), SpellsAPI.SPELL_TYPE);
    }

    public static boolean hasSpell(ItemStack item) {
        return getSpell(item) != null;
    }

    public static List<SpellPart> filterParts(Collection<SpellPart> parts, SpellBuilder builder) {
        List<SpellPart> filteredParts = new ArrayList<>(parts);
        if (builder.getTargetIndex() == 0) {
            filteredParts = filterParts(filteredParts, ShapePart.class::isInstance);
        } else {
            filteredParts = filterParts(filteredParts, Predicate.not(ShapePart.class::isInstance));
        }

        SpellPart lastPart = builder.getLastNonModifier();
        filteredParts = filterParts(filteredParts, part -> !(part instanceof ModifierPart modifierPart) || lastPart.isAllowedModifier(modifierPart));

        return filteredParts;
    }

    private static List<SpellPart> filterParts(Collection<SpellPart> parts, Predicate<SpellPart> filter) {
        return parts.stream().filter(filter).toList();
    }
}
