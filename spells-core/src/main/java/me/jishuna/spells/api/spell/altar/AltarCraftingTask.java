package me.jishuna.spells.api.spell.altar;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;

import me.jishuna.spells.api.spell.part.SpellPart;

public class AltarCraftingTask extends BukkitRunnable {
    private final Player player;
    private final Location center;
    private final SpellPart part;
    private final ItemDisplay display;

    private int recipeIndex;
    private ItemStack currentIngredient;

    public AltarCraftingTask(Player player, Location center, SpellPart part) {
        this.player = player;
        this.center = center;
        this.part = part;

        this.currentIngredient = new ItemStack(part.getRecipe().get(0));

        this.display = this.center.getWorld().spawn(this.center.clone().add(0, 4, 0), ItemDisplay.class);
        this.display.setItemStack(this.currentIngredient);
    }

    @Override
    public void run() {
        if (this.player.getLocation().distanceSquared(this.center) > 20 * 20) {
            this.cancel();
            this.player.sendMessage("distance");
            return;
        }

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

    }

    private void nextIngredient() {
        this.currentIngredient = new ItemStack(this.part.getRecipe().get(++recipeIndex));
        this.display.setItemStack(this.currentIngredient);
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();

        if (this.display.isValid()) {
            this.display.remove();
        }
    }
}
