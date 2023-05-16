package me.jishuna.spells.spell.action;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
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

public class HealAction extends ActionPart {
    public static final HealAction INSTANCE = new HealAction();

    @Comment("The base duration in ticks for the regeneration effect.")
    @ConfigEntry("actions.heal.base-duration")
    public static int BASE_DURATION = 100;

    @Comment("The additional duration in ticks for the regeneration effect per prolong modifier.")
    @ConfigEntry("actions.heal.bonus-duration")
    public static int BONUS_DURATION = 40;

    private HealAction() {
        super(NamespacedKey.fromString("action:heal"));
    }

    @Override
    public void processEntity(Entity target, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor executor) {
        if (target instanceof LivingEntity entity) {
            if (data.getProlongAmount() > 0) {
                int duration = BASE_DURATION + (BONUS_DURATION * (data.getProlongAmount() - 1));
                int level = data.getEmpowerAmount();

                entity.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, duration, level, true));
            } else {
                int health = 4 + (4 * data.getEmpowerAmount());

                entity.setHealth(Math.min(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue(),
                        entity.getHealth() + health));
            }
        }
    }
}
