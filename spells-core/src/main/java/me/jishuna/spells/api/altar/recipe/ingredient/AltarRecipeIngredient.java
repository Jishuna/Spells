package me.jishuna.spells.api.altar.recipe.ingredient;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface AltarRecipeIngredient {

    public ItemStack asItemStack();

    public String asString();

    public boolean matches(ItemStack item);

    public static AltarRecipeIngredient fromString(String string) {
        Material material = Material.matchMaterial(string);

        return material == null ? null : new MaterialIngredient(material);
    }
}
