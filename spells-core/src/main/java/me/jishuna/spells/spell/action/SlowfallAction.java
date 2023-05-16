package me.jishuna.spells.spell.action;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ActionPart;

public class SlowfallAction extends ActionPart {
    public static final SlowfallAction INSTANCE = new SlowfallAction();

    @Comment("The base duration in ticks for the slowfalling effect.")
    @ConfigEntry("actions.slowfall.base-duration")
    public static int BASE_DURATION = 100;

    @Comment("The additional duration in ticks for the slowfalling effect per prolong modifier.")
    @ConfigEntry("actions.slowfall.bonus-duration")
    public static int BONUS_DURATION = 40;

    private SlowfallAction() {
        super(NamespacedKey.fromString("action:slowfall"));
    }

    @Override
    public void processEntity(Entity target, SpellCaster caster, SpellContext context, ModifierData data,
            SpellExecutor executor) {
        if (target instanceof LivingEntity entity) {
            int duration = BASE_DURATION + (BONUS_DURATION * data.getProlongAmount());
            int level = data.getEmpowerAmount();

            entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, duration, level, true));
        }
    }
}
