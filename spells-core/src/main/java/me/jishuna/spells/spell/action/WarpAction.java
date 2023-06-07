package me.jishuna.spells.spell.action;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ActionPart;
import me.jishuna.spells.api.spell.target.BlockTarget;
import me.jishuna.spells.api.spell.target.SpellTarget;
import net.md_5.bungee.api.ChatColor;

public class WarpAction extends ActionPart {
    public static final WarpAction INSTANCE = new WarpAction();

    private WarpAction() {
        super(NamespacedKey.fromString("action:warp"), 15);

        setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Warp");
        setDefaultLore("Teleports the caster to the target location.");

        setRecipe(Material.ENDER_PEARL, Material.STONE);
    }

    @Override
    public void process(SpellTarget target, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor executor) {
        Location warpLocatiom;
        LivingEntity entity = caster.getEntity();
        if (target instanceof BlockTarget blockTarget) {
            warpLocatiom = blockTarget.getOrigin().getBlock().getRelative(blockTarget.getFace()).getLocation();
        } else {
            warpLocatiom = target.getOrigin();
        }

        warpLocatiom.setPitch(entity.getLocation().getPitch());
        warpLocatiom.setYaw(entity.getLocation().getYaw());

        if (warpLocatiom.getBlock().isPassable() && warpLocatiom.clone().add(0, 1, 0).getBlock().isPassable()) {
            entity.teleport(warpLocatiom);
        }
    }
}
