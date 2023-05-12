package me.jishuna.spells.spell.modifier;

import org.bukkit.NamespacedKey;

import me.jishuna.spells.api.spell.part.ModifierPart;

public class EmpowerModifier extends ModifierPart {
    public static final EmpowerModifier INSTANCE = new EmpowerModifier();

    private EmpowerModifier() {
        super(NamespacedKey.fromString("modifier:empower"));
    }

}
