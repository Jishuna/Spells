package me.jishuna.spells.spell.action;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.LivingEntity;

import me.jishuna.jishlib.config.ConfigEntry;
import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ActionPart;

public class BurnAction extends ActionPart {
    public static final BurnAction INSTANCE = new BurnAction();
    private static final BlockData FIRE = Material.FIRE.createBlockData();

    @ConfigEntry("actions.burn.base-duration")
    public static int BASE_DURATION = 60;
    @ConfigEntry("actions.burn.bonus-duration")
    public static int BONUS_DURATION = 40;

    private BurnAction() {
        super(NamespacedKey.fromString("spells:action_burn"));
    }

    @Override
    public void processEntity(LivingEntity target, SpellCaster caster, SpellContext context, ModifierData data) {
        int duration = BASE_DURATION + (BONUS_DURATION * data.getProlongAmount());
        target.setFireTicks(duration);
    }

    @Override
    public void processBlock(Block target, SpellCaster caster, SpellContext context, ModifierData data) {
        target = target.getRelative(BlockFace.UP);
        if (target.getType().isAir() && target.canPlace(FIRE)) {
            target.setType(Material.FIRE);
        }
    }
}
