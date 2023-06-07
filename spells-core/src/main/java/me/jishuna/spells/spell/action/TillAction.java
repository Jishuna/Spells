package me.jishuna.spells.spell.action;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ActionPart;
import net.md_5.bungee.api.ChatColor;

public class TillAction extends ActionPart {
    public static final TillAction INSTANCE = new TillAction();

    private TillAction() {
        super(NamespacedKey.fromString("action:till"), 15);

        setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Till");
        setDefaultLore("Tills grass and dirt into farmland.");

        setRecipe(Material.IRON_HOE, Material.STONE);
    }

    @Override
    public void processBlock(Block target, BlockFace targetFace, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor executor) {
        if (Tag.DIRT.isTagged(target.getType()) && target.getRelative(BlockFace.UP).getType().isAir()) {
            target.setType(Material.FARMLAND);
        }
    }
}
