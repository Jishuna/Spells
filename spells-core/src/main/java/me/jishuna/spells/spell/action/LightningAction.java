package me.jishuna.spells.spell.action;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ActionPart;
import net.md_5.bungee.api.ChatColor;

public class LightningAction extends ActionPart {
    public static final LightningAction INSTANCE = new LightningAction();

    private LightningAction() {
        super(NamespacedKey.fromString("action:lightning"), 15);
        
        setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Lightning");
        setDefaultLore("Strikes the target with lightning.");
        
        setRecipe(Material.LIGHTNING_ROD, Material.STONE);
    }

    @Override
    public void processEntity(Entity target, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor executor) {
        lightning(target.getLocation());
    }

    @Override
    public void processBlock(Block target, BlockFace targetFace, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor executor) {
        lightning(target.getRelative(targetFace).getLocation().add(0.5, 0, 0.5));
    }

    private void lightning(Location location) {
        location.getWorld().strikeLightning(location);
    }
}
