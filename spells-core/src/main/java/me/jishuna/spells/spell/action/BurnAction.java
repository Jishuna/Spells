package me.jishuna.spells.spell.action;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;

import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ActionPart;
import me.jishuna.spells.spell.modifier.ProlongModifier;
import net.md_5.bungee.api.ChatColor;

public class BurnAction extends ActionPart {
    public static final BurnAction INSTANCE = new BurnAction();
    private static final BlockData FIRE = Material.FIRE.createBlockData();

    @ConfigEntry("bonus-duration")
    @Comment("The additional duration in ticks to burn entities per prolong modifier.")
    public static int BONUS_DURATION = 40;

    @ConfigEntry("base-duration")
    @Comment("The base duration in ticks to burn entities for.")
    public static int BASE_DURATION = 60;

    private BurnAction() {
        super(NamespacedKey.fromString("action:burn"), 15);

        setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Burn");
        setDefaultLore("Lights the target block or entity on fire. Prolong will increase the duration entities are set on fire.");

        addAllowedModifiers(ProlongModifier.INSTANCE);

        setRecipe(Material.FLINT_AND_STEEL, Material.COAL, Material.STONE);
    }

    @Override
    public void processEntity(Entity target, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor executor) {
        int duration = BASE_DURATION + (BONUS_DURATION * data.getProlongAmount());
        target.setFireTicks(duration);
    }

    @Override
    public void processBlock(Block target, BlockFace targetFace, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor executor) {
        target = target.getRelative(BlockFace.UP);
        if (target.getType().isAir() && target.canPlace(FIRE)) {
            target.setType(Material.FIRE);
        }
    }
}
