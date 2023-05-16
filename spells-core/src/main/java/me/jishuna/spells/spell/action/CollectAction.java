package me.jishuna.spells.spell.action;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Item;
import org.bukkit.util.Vector;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ActionPart;
import me.jishuna.spells.api.spell.target.SpellTarget;

public class CollectAction extends ActionPart {
    public static final CollectAction INSTANCE = new CollectAction();

    private CollectAction() {
        super(NamespacedKey.fromString("action:collect"));
    }

    @Override
    public void process(SpellTarget target, SpellCaster caster, SpellContext context, ModifierData data,
            SpellExecutor executor) {
        for (Entity entity : target.getTargetEntities()) {
            if (entity instanceof Item || entity instanceof ExperienceOrb) {
                entity.teleport(caster.getLocation());
                entity.setVelocity(new Vector());

                if (entity instanceof Item item && item.getPickupDelay() <= 10) {
                    item.setPickupDelay(0);
                }
            }
        }
    }
}
