package me.jishuna.spells.api.altar.recipe;

import me.jishuna.spells.api.spell.part.SpellPart;

public class SpellPartRecipe extends AltarRecipe {
    private final SpellPart part;

    public SpellPartRecipe(SpellPart part) {
        super(part.getDisplayItem(), part.getRecipe());
        this.part = part;
    }

    public SpellPart getPart() {
        return part;
    }
}
