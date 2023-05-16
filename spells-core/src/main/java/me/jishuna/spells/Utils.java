package me.jishuna.spells;

import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import me.jishuna.spells.api.spell.Spell;

public class Utils {
    public static Vector negate(Vector vector) {
        return vector.clone().multiply(-1);
    }

    public static void outlineBoundingBox(World world, BoundingBox bounds) {
        Vector pointA = new Vector(bounds.getMinX(), bounds.getMinY(), bounds.getMinZ());
        Vector pointB = new Vector(bounds.getMaxX(), bounds.getMinY(), bounds.getMaxZ());
        Vector pointC = new Vector(bounds.getMaxX(), bounds.getMaxY(), bounds.getMinZ());
        Vector pointD = new Vector(bounds.getMinX(), bounds.getMaxY(), bounds.getMaxZ());

        Vector sizeX = new Vector(bounds.getWidthX(), 0, 0);
        Vector sizeY = new Vector(0, bounds.getHeight(), 0);
        Vector sizeZ = new Vector(0, 0, bounds.getWidthZ());

        drawParticleLines(world, sizeX, 0.5, pointA, pointD);
        drawParticleLines(world, sizeY, 0.5, pointA, pointB);
        drawParticleLines(world, sizeZ, 0.5, pointA, pointC);

        drawParticleLines(world, negate(sizeX), 0.5, pointB, pointC);
        drawParticleLines(world, negate(sizeY), 0.5, pointC, pointD);
        drawParticleLines(world, negate(sizeZ), 0.5, pointB, pointD);
    }

    private static void drawParticleLines(World world, Vector path, double spacing, Vector... origins) {
        for (Vector origin : origins) {
            for (double distance = 0; distance <= path.length(); distance += spacing) {
                Vector position = origin.clone().add(path.clone().normalize().multiply(distance));

                world.spawnParticle(Particle.VILLAGER_HAPPY, position.toLocation(world), 1);
            }
        }
    }

    public static Spell getSpell(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return null;
        }

        return item.getItemMeta().getPersistentDataContainer().get(NamespacedKey.fromString("spells:spell"),
                Spells.SPELL_TYPE);
    }
    
    public static boolean hasSpell(ItemStack item) {
        return getSpell(item) != null;
    }
}
