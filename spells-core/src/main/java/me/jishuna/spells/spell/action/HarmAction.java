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

public class HarmAction extends ActionPart {
    public static final HarmAction INSTANCE = new HarmAction();

    @Comment("The base duration in ticks for the poison effect.")
    @ConfigEntry("actions.harm.base-duration")
    public static int BASE_DURATION = 100;

    @Comment("The additional duration in ticks for the poison effect per prolong modifier.")
    @ConfigEntry("actions.harm.bonus-duration")
    public static int BONUS_DURATION = 40;

    private HarmAction() {
        super(NamespacedKey.fromString("action:harm"));
    }

    @Override
    public void processEntity(Entity target, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor executor) {
        if (target instanceof LivingEntity entity) {
            if (data.getProlongAmount() > 0) {
                int duration = BASE_DURATION + (BONUS_DURATION * (data.getProlongAmount() - 1));
                int level = data.getEmpowerAmount();

                entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, duration, level, true));
            } else {
                int damage = 4 + (4 * data.getEmpowerAmount());
                entity.damage(damage);
            }
        }
    }
}
