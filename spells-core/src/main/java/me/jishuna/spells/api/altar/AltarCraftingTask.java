package me.jishuna.spells.api.altar;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.Quaternionf;

import me.jishuna.spells.api.altar.recipe.AltarRecipe;
import me.jishuna.spells.api.altar.recipe.ingredient.AltarRecipeIngredient;

public class AltarCraftingTask extends BukkitRunnable {
    private final Player player;
    private final Location center;
    private final AltarRecipe recipe;
    private final ItemDisplay display;

    private int recipeIndex;
    private AltarRecipeIngredient currentIngredient;

    public AltarCraftingTask(Player player, Location center, AltarRecipe recipe) {
        this.player = player;
        this.center = center;
        this.recipe = recipe;

        this.currentIngredient = recipe.getIngredients().get(0);

        this.display = this.center.getWorld().spawn(this.center.clone().add(0, 4, 0), ItemDisplay.class);
        this.display.setItemStack(this.currentIngredient.asItemStack());
    }

    @Override
    public void run() {
        if (this.player.getLocation().distanceSquared(this.center) > 20 * 20) {
            this.cancel();
            this.player.sendMessage("distance");
            return;
        }

        checkIngredients();
        rotateDisplay();

        Location origin = this.center.clone().add(0, 1, 0);
        origin.getWorld().spawnParticle(Particle.SPELL_WITCH, origin, 1, 0, 0, 0, 0);
    }

    private void rotateDisplay() {
        Transformation transform = this.display.getTransformation();
        Quaternionf leftRotation = transform.getLeftRotation();
        leftRotation.rotateY(0.04f);

        this.display.setInterpolationDelay(0);
        this.display.setInterpolationDuration(2);
        this.display.setTransformation(new Transformation(transform.getTranslation(), leftRotation, transform.getScale(), leftRotation));
    }

    private void checkIngredients() {
        for (Entity entity : this.center.getWorld().getNearbyEntities(this.center, 2, 2, 2, Item.class::isInstance)) {
            Item item = (Item) entity;
            ItemStack itemstack = item.getItemStack();

            if (this.currentIngredient.matches(itemstack)) {
                itemstack.setAmount(itemstack.getAmount() - 1);
                if (itemstack.getAmount() > 0) {
                    item.setItemStack(itemstack);
                } else {
                    item.remove();
                }

                if (this.recipeIndex < this.recipe.getIngredients().size() - 1) {
                    nextIngredient();
                } else {
                    finishCrafting();
                }
            }
        }
    }

    private void finishCrafting() {
        Item item = this.center.getWorld().dropItem(this.center.clone().add(0, 1, 0), this.recipe.getOutput());
        item.setVelocity(new Vector());

        this.cancel();
    }

    private void nextIngredient() {
        this.currentIngredient = this.recipe.getIngredients().get(++recipeIndex);
        this.display.setItemStack(this.currentIngredient.asItemStack());
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();

        if (this.display.isValid()) {
            this.display.remove();
        }
    }
}
