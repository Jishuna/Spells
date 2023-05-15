package me.jishuna.spells.spell.action;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import me.jishuna.jishlib.LocationUtils;
import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ActionPart;
import me.jishuna.spells.api.spell.target.SpellTarget;

public class RecallAction extends ActionPart {
    public static final RecallAction INSTANCE = new RecallAction();

    private RecallAction() {
        super(NamespacedKey.fromString("action:recall"));
    }

    @Override
    public void process(SpellTarget target, SpellCaster caster, SpellContext context, ModifierData data) {
        if (caster.getEntity() instanceof Player player) {
            Location targetLocation = player.getBedSpawnLocation();

            if (targetLocation == null) {
                targetLocation = LocationUtils.centerLocation(player.getWorld().getSpawnLocation(), false);
            }

            player.teleport(targetLocation);
        }
    }
}
