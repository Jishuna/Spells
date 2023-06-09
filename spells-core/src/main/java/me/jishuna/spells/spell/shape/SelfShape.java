package me.jishuna.spells.spell.shape;

import org.bukkit.Material;
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
import me.jishuna.spells.api.spell.target.EntityTarget;
import net.md_5.bungee.api.ChatColor;

public class SelfShape extends ShapePart {
    public static final SelfShape INSTANCE = new SelfShape();

    private SelfShape() {
        super(NamespacedKey.fromString("shape:self"), 15);

        setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Self");
        setDefaultLore("Targets the entity casting the spell.");

        setRecipe(Material.IRON_INGOT, Material.BREAD, Material.STONE);
    }

    @Override
    public void cast(SpellCaster caster, World world, SpellContext context, ModifierData data, SpellExecutor resolver) {
        resolver.execute(EntityTarget.create(caster.getEntity()));
    }

    @Override
    public void castOnEntity(Entity entity, World world, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor resolver) {
        cast(caster, world, context, data, resolver);
    }

    @Override
    public void castOnBlock(Block block, BlockFace face, World world, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor resolver) {
        cast(caster, world, context, data, resolver);
    }
}
