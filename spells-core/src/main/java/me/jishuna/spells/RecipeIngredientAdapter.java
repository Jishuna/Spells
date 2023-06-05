package me.jishuna.spells;

import me.jishuna.jishlib.config.adapter.StringAdapter;
import me.jishuna.spells.api.altar.recipe.ingredient.AltarRecipeIngredient;

public class RecipeIngredientAdapter implements StringAdapter<AltarRecipeIngredient> {

    @Override
    public String toString(AltarRecipeIngredient ingredient) {
        return ingredient.asString();
    }

    @Override
    public AltarRecipeIngredient fromString(String string) {
        return AltarRecipeIngredient.fromString(string);
    }
}
