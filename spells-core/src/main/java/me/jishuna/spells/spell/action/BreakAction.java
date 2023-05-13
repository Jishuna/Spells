package me.jishuna.spells.spell.action;

import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ActionPart;

public class BreakAction extends ActionPart {
    public static final BreakAction INSTANCE = new BreakAction();

    private BreakAction() {
        super(NamespacedKey.fromString("action:break"));
    }

    @Override
    public void processBlock(Block target, SpellCaster caster, SpellContext context, ModifierData data) {
        target.breakNaturally();
    }
}
