package me.jishuna.spells.spell.modifier;

import org.bukkit.NamespacedKey;

import me.jishuna.spells.api.spell.part.ModifierPart;

public class AltModifier extends ModifierPart {
    public static final AltModifier INSTANCE = new AltModifier();

    private AltModifier() {
        super(NamespacedKey.fromString("modifier:alt"));
    }

}
