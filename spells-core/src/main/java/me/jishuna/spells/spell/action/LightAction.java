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
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ActionPart;
import me.jishuna.spells.spell.modifier.EmpowerModifier;
import me.jishuna.spells.spell.modifier.ProlongModifier;
import net.md_5.bungee.api.ChatColor;

public class LightAction extends ActionPart {
    public static final LightAction INSTANCE = new LightAction();

    @Comment("The additional duration in ticks to make entities glow for per prolong modifier.")
    @ConfigEntry("bonus-duration")
    public static int BONUS_DURATION = 40;
    
    @Comment("The base duration in ticks to make entities glow for.")
    @ConfigEntry("base-duration")
    public static int BASE_DURATION = 100;

    private LightAction() {
        super(NamespacedKey.fromString("action:light"), 15);

        setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Light");
        setDefaultLore("Creates an invisible light at the target block or makes the target entity glow. Empower increases the light level of the light, Prolong increases the duration of the glowing effect.");
   
        addAllowedModifiers(EmpowerModifier.INSTANCE, ProlongModifier.INSTANCE);
        setRecipe(Material.TORCH, Material.GLOW_BERRIES, Material.STONE);
    }

    @Override
    public void processEntity(Entity target, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor executor) {
        if (target instanceof LivingEntity entity) {
            int duration = BASE_DURATION + (BONUS_DURATION * data.getProlongAmount());
            entity.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, duration, 0, true));
        }
    }

    @Override
    public void processBlock(Block target, BlockFace targetFace, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor executor) {
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
