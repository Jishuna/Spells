package me.jishuna.spells.spell.action;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Light;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ActionPart;

public class LightAction extends ActionPart {
    public static final LightAction INSTANCE = new LightAction();

    @Comment("The base duration in ticks to make entities glow for.")
    @ConfigEntry("actions.light.base-duration")
    public static int BASE_DURATION = 100;

    @Comment("The additional duration in ticks to make entities glow for per prolong modifier.")
    @ConfigEntry("actions.light.bonus-duration")
    public static int BONUS_DURATION = 40;

    private LightAction() {
        super(NamespacedKey.fromString("action:light"));
    }

    @Override
    public void processEntity(Entity target, SpellCaster caster, SpellContext context, ModifierData data) {
        if (target instanceof LivingEntity entity) {
            int duration = BASE_DURATION + (BONUS_DURATION * data.getProlongAmount());
            entity.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, duration, 0));
        }
    }

    @Override
    public void processBlock(Block target, BlockFace targetFace, SpellCaster caster, SpellContext context,
            ModifierData data) {
        Block targetBlock = target.getRelative(targetFace);

        if (!targetBlock.getType().isAir()) {
            return;
        }

        int lightLevel = 11;
        if (data.getEmpowerAmount() > 0) {
            lightLevel = 15;
        }

        Light light = (Light) Material.LIGHT.createBlockData();
        light.setLevel(lightLevel);

        targetBlock.setBlockData(light);
    }
}
