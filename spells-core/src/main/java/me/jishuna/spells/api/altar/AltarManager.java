package me.jishuna.spells.api.altar;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.jishuna.spells.api.altar.recipe.AltarRecipe;

public class AltarManager {
    private final Map<Location, AltarCraftingTask> tasks = new HashMap<>();
    private final Plugin plugin;
    private final AltarStructure altarStructure;

    public AltarManager(Plugin plugin) {
        this.plugin = plugin;
        this.altarStructure = loadAltarStrcture(plugin);
    }

    public boolean isValidStructure(Location center, BlockFace face) {
        return this.altarStructure.isValidStructure(center, face);
    }

    public boolean isAltarInUse(Location center) {
        AltarCraftingTask task = this.tasks.get(center);
        return !(task == null || task.isCancelled());
    }

    public void startAltarTask(Player player, Location center, AltarRecipe recipe) {
        AltarCraftingTask task = new AltarCraftingTask(player, center, recipe);
        task.runTaskTimer(this.plugin, 0, 2);

        this.tasks.put(center, task);
    }

    public AltarStructure getAltarStructure() {
        return this.altarStructure;
    }

    private AltarStructure loadAltarStrcture(Plugin plugin) {
        try {
            return new AltarStructure(Bukkit.getStructureManager().loadStructure(plugin.getResource("data/altar.nbt")));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
