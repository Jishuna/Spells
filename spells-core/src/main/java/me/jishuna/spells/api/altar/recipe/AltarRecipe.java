package me.jishuna.spells.api.altar.recipe;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.jishuna.jishlib.items.ItemBuilder;
import net.md_5.bungee.api.ChatColor;

public class AltarRecipe {
    private final ItemStack output;
    private final List<Material> ingredients;
    private final ItemStack icon;

    public AltarRecipe(ItemStack output, Material... ingredients) {
        this(output, Arrays.asList(ingredients));
    }

    public AltarRecipe(ItemStack output, List<Material> ingredients) {
        this.output = output;
        this.ingredients = ingredients;
        this.icon = createIcon();
    }

    public ItemStack getOutput() {
        return output.clone();
    }

    public List<Material> getIngredients() {
        return Collections.unmodifiableList(this.ingredients);
    }

    public ItemStack getIcon() {
        return this.icon.clone();
    }

    private ItemStack createIcon() {
        ItemBuilder builder = ItemBuilder.modifyItem(getOutput());
        builder.lore(ChatColor.GRAY + "Ingredients:");

        for (Material material : this.ingredients) {
            builder.lore(ChatColor.GRAY + "  - " + material.name().toLowerCase().replace("_", " "));
        }

        return builder.build();
    }

}
