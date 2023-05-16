package me.jishuna.spells.api.spell;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import me.jishuna.spells.api.spell.part.ModifierPart;
import me.jishuna.spells.spell.modifier.EmpowerModifier;
import me.jishuna.spells.spell.modifier.PierceModifier;
import me.jishuna.spells.spell.modifier.ProlongModifier;

public class ModifierData {
    private final Map<ModifierPart, Integer> counts = new HashMap<>();

    public ModifierData(Collection<ModifierPart> parts) {
        for (ModifierPart part : parts) {
            this.counts.merge(part, 1, Integer::sum);
        }
    }

    public int getCount(ModifierPart part) {
        return this.counts.getOrDefault(part, 0);
    }

    public int getEmpowerAmount() {
        return getCount(EmpowerModifier.INSTANCE);
    }

    public int getProlongAmount() {
        return getCount(ProlongModifier.INSTANCE);
    }

    public int getPierceAmount() {
        return getCount(PierceModifier.INSTANCE);
    }

    public static ModifierData fromSpell(Spell spell) {
        return new ModifierData(spell.getParts().stream().filter(ModifierPart.class::isInstance)
                .map(ModifierPart.class::cast).toList());
    }
}
