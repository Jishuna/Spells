package me.jishuna.spells.spell.modifier;

import org.bukkit.NamespacedKey;

import me.jishuna.spells.api.spell.part.ModifierPart;

public class PierceModifier extends ModifierPart {
    public static final PierceModifier INSTANCE = new PierceModifier();

    private PierceModifier() {
        super(NamespacedKey.fromString("modifier:pierce"));
    }

}
