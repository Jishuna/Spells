package me.jishuna.spells.spell.action;

import org.bukkit.Material;
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
import me.jishuna.spells.spell.modifier.EmpowerModifier;
import me.jishuna.spells.spell.modifier.ProlongModifier;
import net.md_5.bungee.api.ChatColor;

public class HealAction extends ActionPart {
    public static final HealAction INSTANCE = new HealAction();

    @Comment("The additional duration in ticks for the regeneration effect per prolong modifier.")
    @ConfigEntry("bonus-duration")
    public static int BONUS_DURATION = 40;

    @Comment("The base duration in ticks for the regeneration effect.")
    @ConfigEntry("base-duration")
    public static int BASE_DURATION = 100;

    private HealAction() {
        super(NamespacedKey.fromString("action:heal"), 15);

        setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Heal");
        setDefaultLore("Heals the target. Prolong will replace the instant healing with a regeneration effect, with additional prolong modifiers increasing the duration of the regeneration. Empower will increase the healing amount or the level of poison.");

        addAllowedModifiers(EmpowerModifier.INSTANCE, ProlongModifier.INSTANCE);

        setRecipe(Material.GLISTERING_MELON_SLICE, Material.GHAST_TEAR, Material.STONE);
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

                entity.setHealth(Math.min(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue(), entity.getHealth() + health));
            }
        }
    }
}
