package me.jishuna.spells.spell.action;

import org.bukkit.NamespacedKey;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ActionPart;
import me.jishuna.spells.api.spell.target.SpellTarget;

public class DelayAction extends ActionPart {
    public static final DelayAction INSTANCE = new DelayAction();

    private DelayAction() {
        super(NamespacedKey.fromString("action:delay"));
    }

    @Override
    public void process(SpellTarget target, SpellCaster caster, SpellContext context, ModifierData data,
            SpellExecutor executor) {
        if (context.hasPartsLeft()) {
            int delay = 20 + (20 * data.getProlongAmount());
            executor.setCancelled(true);

            SpellExecutor delayedExecutor = executor.cloneRemaining();
            delayedExecutor.executeDelayed(delay);
        }
    }
}
