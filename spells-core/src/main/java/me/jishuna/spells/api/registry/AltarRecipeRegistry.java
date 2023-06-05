package me.jishuna.spells.api.registry;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.NamespacedKey;

import me.jishuna.spells.api.altar.recipe.AltarRecipe;

public class AltarRecipeRegistry {
    private final Map<NamespacedKey, AltarRecipe> altarRecipes = new LinkedHashMap<>();

    public void register(NamespacedKey key, AltarRecipe recipe) {
        this.altarRecipes.put(key, recipe);
    }

    public Collection<AltarRecipe> getRecipes() {
        return Collections.unmodifiableCollection(this.altarRecipes.values());
    }

}
