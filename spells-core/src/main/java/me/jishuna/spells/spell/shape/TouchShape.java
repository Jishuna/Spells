package me.jishuna.spells.spell.shape;

import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ShapePart;
import me.jishuna.spells.api.spell.target.BlockTarget;
import me.jishuna.spells.api.spell.target.EntityTarget;
import net.md_5.bungee.api.ChatColor;

public class TouchShape extends ShapePart {
    public static final TouchShape INSTANCE = new TouchShape();

    private TouchShape() {
        super(NamespacedKey.fromString("shape:touch"), 15);

        setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Touch");
        setDefaultLore("Targets the block or entity you click on, has a short range.");
    }

    @Override
    public void cast(SpellCaster caster, World world, SpellContext context, ModifierData data, SpellExecutor resolver) {
        // No target
    }

    @Override
    public void castOnEntity(Entity entity, World world, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor resolver) {
        resolver.execute(EntityTarget.create(entity));
    }

    @Override
    public void castOnBlock(Block block, BlockFace face, World world, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor resolver) {
        resolver.execute(BlockTarget.create(block, face));
    }
}
