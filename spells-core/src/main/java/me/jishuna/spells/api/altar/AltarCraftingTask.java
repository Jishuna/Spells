package me.jishuna.spells.api.altar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Lectern;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import me.jishuna.spells.api.altar.recipe.AltarRecipe;
import me.jishuna.spells.api.altar.recipe.ingredient.AltarRecipeIngredient;

public class AltarCraftingTask extends BukkitRunnable {
    private final Player player;
    private final Location center;
    private final AltarRecipe recipe;
    private final ItemDisplay display;
    private final List<BlockFace> faces = new ArrayList<>(Arrays.asList(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST));

    private int recipeIndex;
    private AltarRecipeIngredient currentIngredient;

    public AltarCraftingTask(Player player, Location center, AltarRecipe recipe) {
        this.player = player;
        this.center = center.clone().add(0, 1, 0);
        this.recipe = recipe;

        this.currentIngredient = recipe.getIngredients().get(0);

        this.display = this.center.getWorld().spawn(this.center.clone().add(0, 0.5, 0), ItemDisplay.class);
        this.display.setItemStack(this.currentIngredient.asItemStack());
        this.display.setTransformation(new Transformation(new Vector3f(), new AxisAngle4f(), new Vector3f(0.5f), new AxisAngle4f()));

        detectFaces();
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

        World world = this.center.getWorld();

        for (BlockFace face : this.faces) {
            world.spawnParticle(Particle.SPELL_WITCH, this.center.clone().add(face.getModX(), 1.1, face.getModZ()), 1, 0, 0, 0, 0);
        }
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
        for (Entity entity : this.center.getWorld().getNearbyEntities(this.center, 2.5, 2.5, 2.5, Item.class::isInstance)) {
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
        Item item = this.center.getWorld().dropItem(this.center, this.recipe.getOutput());
        item.setVelocity(new Vector());

        this.cancel();
    }

    private void nextIngredient() {
        this.currentIngredient = this.recipe.getIngredients().get(++recipeIndex);
        this.display.setItemStack(this.currentIngredient.asItemStack());
    }

    private void detectFaces() {
        BlockData data = this.center.getBlock().getRelative(BlockFace.DOWN).getBlockData();
        if (!(data instanceof Lectern lectern)) {
            return;
        }

        this.faces.remove(lectern.getFacing());
        this.faces.remove(lectern.getFacing().getOppositeFace());
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();

        if (this.display.isValid()) {
            this.display.remove();
        }
    }
}
