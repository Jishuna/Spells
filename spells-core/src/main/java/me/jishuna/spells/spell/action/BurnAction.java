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
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ActionPart;

public class BurnAction extends ActionPart {
    public static final BurnAction INSTANCE = new BurnAction();
    private static final BlockData FIRE = Material.FIRE.createBlockData();

    @Comment("The base duration in ticks to burn entities for.")
    @ConfigEntry("actions.burn.base-duration")
    public static int BASE_DURATION = 60;

    @Comment("The additional duration in ticks to burn entities per prolong modifier.")
    @ConfigEntry("actions.burn.bonus-duration")
    public static int BONUS_DURATION = 40;

    private BurnAction() {
        super(NamespacedKey.fromString("action:burn"));
    }

    @Override
    public void processEntity(Entity target, SpellCaster caster, SpellContext context, ModifierData data) {
        int duration = BASE_DURATION + (BONUS_DURATION * data.getProlongAmount());
        target.setFireTicks(duration);
    }

    @Override
    public void processBlock(Block target, BlockFace targetFace, SpellCaster caster, SpellContext context,
            ModifierData data) {
        target = target.getRelative(BlockFace.UP);
        if (target.getType().isAir() && target.canPlace(FIRE)) {
            target.setType(Material.FIRE);
        }
    }
}
