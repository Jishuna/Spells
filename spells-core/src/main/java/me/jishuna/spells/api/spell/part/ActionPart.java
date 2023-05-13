package me.jishuna.spells.api.spell.part;

import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.target.BlockTarget;
import me.jishuna.spells.api.spell.target.EntityTarget;
import me.jishuna.spells.api.spell.target.SpellTarget;

public class ActionPart extends SpellPart {

    protected ActionPart(NamespacedKey key) {
        super(key);
    }

    public void process(SpellTarget target, SpellCaster caster, SpellContext context, ModifierData data) {
        if (target instanceof EntityTarget entityTarget) {
            entityTarget.getTargetEntities().forEach(entity -> processEntity(entity, caster, context, data));
        }

        if (target instanceof BlockTarget blockTarget) {
            blockTarget.getTargetBlocks().forEach(block -> processBlock(block, blockTarget.getHitFace(), caster, context, data));
        }
    }

    protected void processEntity(Entity target, SpellCaster caster, SpellContext context, ModifierData data) {
        // NO-OP
    }

    protected void processBlock(Block target, BlockFace targetFace, SpellCaster caster, SpellContext context, ModifierData data) {
        // NO-OP
    }
}
