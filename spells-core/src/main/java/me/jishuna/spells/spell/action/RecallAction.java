package me.jishuna.spells.spell.action;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import me.jishuna.jishlib.LocationUtils;
import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ActionPart;
import me.jishuna.spells.api.spell.target.SpellTarget;
import net.md_5.bungee.api.ChatColor;

public class RecallAction extends ActionPart {
    public static final RecallAction INSTANCE = new RecallAction();

    private RecallAction() {
        super(NamespacedKey.fromString("action:recall"), 15);

        setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Recall");
        setDefaultLore("Teleports the target entity to their bed or the world spawn if they have not slept in a bed. Only works on players.");
    }

    @Override
    public void process(SpellTarget target, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor executor) {
        for (Entity entity : target.getTargetEntities()) {
            if (!(entity instanceof Player player)) {
                continue;
            }

            Location targetLocation = player.getBedSpawnLocation();
            if (targetLocation == null) {
                targetLocation = LocationUtils.centerLocation(player.getWorld().getSpawnLocation(), false);
            }

            player.teleport(targetLocation);
        }
    }
}
