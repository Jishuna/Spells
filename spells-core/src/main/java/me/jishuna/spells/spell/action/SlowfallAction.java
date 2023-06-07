package me.jishuna.spells.spell.action;

import org.bukkit.Material;
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
import me.jishuna.spells.spell.modifier.EmpowerModifier;
import me.jishuna.spells.spell.modifier.ProlongModifier;
import net.md_5.bungee.api.ChatColor;

public class SlowfallAction extends ActionPart {
    public static final SlowfallAction INSTANCE = new SlowfallAction();

    @Comment("The additional duration in ticks for the slowfalling effect per prolong modifier.")
    @ConfigEntry("bonus-duration")
    public static int BONUS_DURATION = 40;

    @Comment("The base duration in ticks for the slowfalling effect.")
    @ConfigEntry("base-duration")
    public static int BASE_DURATION = 100;

    private SlowfallAction() {
        super(NamespacedKey.fromString("action:slowfall"), 15);

        setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Slowfall");
        setDefaultLore("Gives the target entity the slowfalling effect. Empower will increase the level of the effect, Prolong will increase the duration of the effect.");

        addAllowedModifiers(EmpowerModifier.INSTANCE, ProlongModifier.INSTANCE);
        setRecipe(Material.FEATHER, Material.PHANTOM_MEMBRANE, Material.STONE);
    }

    @Override
    public void processEntity(Entity target, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor executor) {
        if (target instanceof LivingEntity entity) {
            int duration = BASE_DURATION + (BONUS_DURATION * data.getProlongAmount());
            int level = data.getEmpowerAmount();

            entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, duration, level, true));
        }
    }
}
