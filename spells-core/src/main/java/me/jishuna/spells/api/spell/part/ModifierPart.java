package me.jishuna.spells.api.spell.part;

import org.bukkit.NamespacedKey;

public class ModifierPart extends SpellPart {

    protected ModifierPart(NamespacedKey key, int cost) {
        super(key, cost);
    }

    @Override
    public String getConfigFolder() {
        return "modifiers/";
    }

}
