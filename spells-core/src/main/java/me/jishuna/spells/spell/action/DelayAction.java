package me.jishuna.spells.spell.action;

import org.bukkit.NamespacedKey;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ActionPart;
import me.jishuna.spells.api.spell.target.SpellTarget;
import me.jishuna.spells.spell.modifier.ProlongModifier;
import net.md_5.bungee.api.ChatColor;

public class DelayAction extends ActionPart {
    public static final DelayAction INSTANCE = new DelayAction();

    private DelayAction() {
        super(NamespacedKey.fromString("action:delay"), 15);

        setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Delay");
        setDefaultLore("Delays all further parts of the spell. Prolong will increase the duration of the delay.");

        addAllowedModifiers(ProlongModifier.INSTANCE);
    }

    @Override
    public void process(SpellTarget target, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor executor) {
        if (context.hasPartsLeft()) {
            int delay = 20 + (20 * data.getProlongAmount());
            executor.setCancelled(true);

            SpellExecutor delayedExecutor = executor.cloneRemaining();
            delayedExecutor.executeDelayed(delay);
        }
    }
}
