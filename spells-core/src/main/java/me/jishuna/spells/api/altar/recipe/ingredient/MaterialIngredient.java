package me.jishuna.spells.api.altar.recipe.ingredient;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MaterialIngredient implements AltarRecipeIngredient {
    private final Material material;

    public MaterialIngredient(Material material) {
        this.material = material;
    }

    @Override
    public ItemStack asItemStack() {
        return new ItemStack(this.material);
    }

    @Override
    public String asString() {
        return this.material.name();
    }

    @Override
    public boolean matches(ItemStack item) {
        return item != null && item.getType() == this.material;
    }
}
