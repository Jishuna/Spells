package me.jishuna.spells.api.spell;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;

import me.jishuna.spells.Spells;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ActionPart;
import me.jishuna.spells.api.spell.part.ShapePart;
import me.jishuna.spells.api.spell.part.SpellPart;
import me.jishuna.spells.api.spell.part.SubshapePart;
import me.jishuna.spells.api.spell.target.SpellTarget;

public class SpellExecutor {
    private final Spells plugin;
    private final SpellCaster caster;
    private final SpellContext context;
    private SpellTarget target;

    public SpellExecutor(Spells plugin, SpellCaster caster, Spell spell) {
        this.plugin = plugin;
        this.caster = caster;
        this.context = new SpellContext(spell);
    }

    public void handleCast(World world) {
        Spell subspell = context.getNextSubspell();
        ModifierData data = ModifierData.fromSpell(subspell);

        if (subspell.isValid() && subspell.getParts().get(0) instanceof ShapePart shape) {
            shape.cast(caster, world, context, data, this);
        }
    }

    public void handleBlockCast(Block block) {
        Spell subspell = context.getNextSubspell();
        ModifierData data = ModifierData.fromSpell(subspell);

        if (subspell.isValid() && subspell.getParts().get(0) instanceof ShapePart shape) {
            shape.castOnBlock(block, block.getWorld(), caster, context, data, this);
        }
    }

    public void handleEntityCast(LivingEntity entity) {
        Spell subspell = context.getNextSubspell();
        ModifierData data = ModifierData.fromSpell(subspell);

        if (subspell.isValid() && subspell.getParts().get(0) instanceof ShapePart shape) {
            shape.castOnEntity(entity, entity.getWorld(), caster, context, data, this);
        }
    }

    public void resolve(SpellTarget target) {
        this.target = target;

        while (context.hasPartsLeft()) {
            Spell spell = context.getNextSubspell();

            if (!spell.isValid()) {
                continue;
            }

            SpellPart firstPart = spell.getParts().get(0);
            ModifierData data = ModifierData.fromSpell(spell);

            if (firstPart instanceof ActionPart actionPart) {
                actionPart.process(this.target, caster, context, data);
            } else if (firstPart instanceof SubshapePart subShapePart) {
                this.target = subShapePart.cast(this.target, this.caster, this.caster.getEntity().getWorld(), context,
                        data, this);
            }
        }
    }

    public Spells getPlugin() {
        return plugin;
    }
}
