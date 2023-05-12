package me.jishuna.spells.spell.modifier;

import org.bukkit.NamespacedKey;

import me.jishuna.spells.api.spell.part.ModifierPart;

public class ProlongModifier extends ModifierPart {
    public static final ProlongModifier INSTANCE = new ProlongModifier();

    private ProlongModifier() {
        super(NamespacedKey.fromString("spells:modifier_prolong"));
    }

}
