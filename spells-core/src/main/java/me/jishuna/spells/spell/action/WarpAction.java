package me.jishuna.spells.spell.action;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ActionPart;
import me.jishuna.spells.api.spell.target.BlockTarget;
import me.jishuna.spells.api.spell.target.SpellTarget;

public class WarpAction extends ActionPart {
    public static final WarpAction INSTANCE = new WarpAction();

    private WarpAction() {
        super(NamespacedKey.fromString("action:warp"));
    }

    @Override
    public void process(SpellTarget target, SpellCaster caster, SpellContext context, ModifierData data) {
        Location warpLocatiom;
        LivingEntity entity = caster.getEntity();
        if (target instanceof BlockTarget blockTarget) {
            warpLocatiom = blockTarget.getOriginBlock().getRelative(blockTarget.getHitFace()).getLocation();
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