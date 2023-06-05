package me.jishuna.spells.spell.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.CookingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.RecipeChoice.MaterialChoice;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ActionPart;
import net.md_5.bungee.api.ChatColor;

public class SmeltAction extends ActionPart {
    public static final SmeltAction INSTANCE = new SmeltAction();
    private static Map<Material, Material> smeltMap;

    private SmeltAction() {
        super(NamespacedKey.fromString("action:smelt"), 15);

        setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Smelt");
        setDefaultLore("Smelts target blocks or items as if they were placed in a furnace.");

        setRecipe(Material.SMOKER, Material.BLAST_FURNACE, Material.STONE);
    }

    @Override
    public void processEntity(Entity target, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor executor) {
        if (!(target instanceof Item item)) {
            return;
        }

        if (smeltMap == null) {
            cacheSmeltingRecipes();
        }

        ItemStack stack = item.getItemStack();
        Material result = smeltMap.get(stack.getType());

        if (result == null || !result.isItem()) {
            return;
        }

        stack.setType(result);
        item.setItemStack(stack);
    }

    @Override
    public void processBlock(Block target, BlockFace targetFace, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor executor) {
        if (smeltMap == null) {
            cacheSmeltingRecipes();
        }

        Material result = smeltMap.get(target.getType());

        if (result == null || !result.isBlock()) {
            return;
        }

        target.setType(result);
    }

    private static void cacheSmeltingRecipes() {
        smeltMap = new HashMap<>();
        Iterator<Recipe> recipes = Bukkit.recipeIterator();

        while (recipes.hasNext()) {
            Recipe recipe = recipes.next();

            if (!(recipe instanceof CookingRecipe<?> cookingRecipe)) {
                continue;
            }

            RecipeChoice choice = cookingRecipe.getInputChoice();
            if (choice instanceof MaterialChoice materialChoice) {
                for (Material material : materialChoice.getChoices()) {
                    smeltMap.put(material, cookingRecipe.getResult().getType());
                }
            }
        }
    }
}
