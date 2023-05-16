package me.jishuna.spells.spell.action;

import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ActionPart;

public class BreakAction extends ActionPart {
    public static final BreakAction INSTANCE = new BreakAction();

    private BreakAction() {
        super(NamespacedKey.fromString("action:break"));
    }

    @Override
    public void processBlock(Block target, BlockFace targetFace, SpellCaster caster, SpellContext context,
            ModifierData data, SpellExecutor executor) {
        target.breakNaturally();
    }
}
