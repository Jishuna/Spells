package me.jishuna.spells.api.altar.recipe;

import java.util.Collections;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import me.jishuna.jishlib.items.ItemBuilder;
import me.jishuna.spells.api.altar.recipe.ingredient.AltarRecipeIngredient;
import net.md_5.bungee.api.ChatColor;

public class AltarRecipe {
    private final ItemStack output;
    private final List<AltarRecipeIngredient> ingredients;
    private final ItemStack icon;

    public AltarRecipe(ItemStack output, List<AltarRecipeIngredient> ingredients) {
        this.output = output;
        this.ingredients = ingredients;
        this.icon = createIcon();
    }

    public ItemStack getOutput() {
        return output.clone();
    }

    public List<AltarRecipeIngredient> getIngredients() {
        return Collections.unmodifiableList(this.ingredients);
    }

    public ItemStack getIcon() {
        return this.icon.clone();
    }

    private ItemStack createIcon() {
        ItemBuilder builder = ItemBuilder.modifyItem(getOutput());
        builder.lore(" ", ChatColor.GRAY + "Ingredients:");

        for (AltarRecipeIngredient ingredient : this.ingredients) {
            if (ingredient == null) {
                continue;
            }
            builder.lore(ChatColor.GRAY + "  - " + ingredient.asString().toLowerCase().replace("_", " "));
        }

        return builder.build();
    }
}
