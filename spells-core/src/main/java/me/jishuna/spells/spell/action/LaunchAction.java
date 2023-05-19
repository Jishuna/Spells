package me.jishuna.spells.spell.action;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.util.Vector;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ActionPart;
import me.jishuna.spells.spell.modifier.EmpowerModifier;
import net.md_5.bungee.api.ChatColor;

public class LaunchAction extends ActionPart {
    public static final LaunchAction INSTANCE = new LaunchAction();

    private LaunchAction() {
        super(NamespacedKey.fromString("action:launch"), 15);
        
        setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Launch");
        setDefaultLore("Launches target blocks and entities into the air. Empower will increase the height of the launch.");
        
        addAllowedModifiers(EmpowerModifier.INSTANCE);
    }

    @Override
    public void processEntity(Entity target, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor executor) {
        double power = 0.8 + (0.2 * data.getEmpowerAmount());
        target.setVelocity(new Vector(0, power, 0));
    }

    @Override
    public void processBlock(Block target, BlockFace targetFace, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor executor) {
        Block targetBlock = target.getRelative(targetFace);

        if (!targetBlock.isPassable()) {
            return;
        }

        double power = 0.8 + (0.2 * data.getEmpowerAmount());
        BlockData blockData = target.getBlockData();
        target.setType(Material.AIR);

        FallingBlock block = target.getWorld().spawnFallingBlock(target.getLocation().add(0.5, 0, 0.5), blockData);
        block.setVelocity(new Vector(0, power, 0));
    }
}
