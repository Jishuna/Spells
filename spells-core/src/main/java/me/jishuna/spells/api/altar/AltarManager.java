package me.jishuna.spells.api.altar;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.structure.Structure;
import org.bukkit.util.Vector;

import me.jishuna.spells.api.altar.recipe.AltarRecipe;

public class AltarManager {
    private final Map<Location, AltarCraftingTask> tasks = new HashMap<>();
    private final Plugin plugin;
    private final Structure altarStructure;

    public AltarManager(Plugin plugin) {
        this.plugin = plugin;
        this.altarStructure = loadAltarStrcture(plugin);
    }

    public boolean isValidStructure(final Location center) {
        Vector size = this.altarStructure.getSize();
        Location origin = center.clone().subtract(Math.floor(size.getX() / 2), 0, Math.floor(size.getZ() / 2));

        List<BlockState> blocks = this.altarStructure.getPalettes().get(0).getBlocks();
        for (BlockState state : blocks) {
            if (state.getType().isAir()) {
                continue;
            }

            Location target = origin.clone().add(state.getX(), state.getY(), state.getZ());
            if (!target.getBlock().getBlockData().equals(state.getBlockData())) {
                return false;
            }
        }

        return true;
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

    public Structure getAltarStructure() {
        return this.altarStructure;
    }

    private Structure loadAltarStrcture(Plugin plugin) {
        try {
            return Bukkit.getStructureManager().loadStructure(plugin.getResource("data/altar.nbt"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
