package me.jishuna.spells.api.spell;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;

import me.jishuna.spells.Spells;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ActionPart;
import me.jishuna.spells.api.spell.part.FilterPart;
import me.jishuna.spells.api.spell.part.ShapePart;
import me.jishuna.spells.api.spell.part.SpellPart;
import me.jishuna.spells.api.spell.part.SubshapePart;
import me.jishuna.spells.api.spell.target.SpellTarget;

public class SpellExecutor {
    private final Spells plugin;
    private final SpellCaster caster;
    private final SpellContext context;
    private SpellTarget target;
    private boolean cancelled;

    public SpellExecutor(Spells plugin, SpellCaster caster, Spell spell) {
        this.plugin = plugin;
        this.caster = caster;
        this.context = new SpellContext(spell);
    }

    private SpellExecutor(Spells plugin, SpellCaster caster, Spell spell, SpellTarget target) {
        this.plugin = plugin;
        this.caster = caster;
        this.context = new SpellContext(spell);
        this.target = target;
    }

    private boolean canCast() {
        return this.caster.hasMana(10);
    }

    public void handleCast(World world) {
        if (!canCast()) {
            return;
        }

        Spell subspell = context.getNextSubspell();
        ModifierData data = ModifierData.fromSpell(subspell);

        if (subspell.isValid() && subspell.getParts().get(0) instanceof ShapePart shape) {
            shape.cast(caster, world, context, data, this);
            caster.removeMana(10);
        }
    }

    public void handleBlockCast(Block block, BlockFace face) {
        if (!canCast()) {
            return;
        }

        Spell subspell = context.getNextSubspell();
        ModifierData data = ModifierData.fromSpell(subspell);

        if (subspell.isValid() && subspell.getParts().get(0) instanceof ShapePart shape) {
            shape.castOnBlock(block, face, block.getWorld(), caster, context, data, this);
            caster.removeMana(10);
        }
    }

    public void handleEntityCast(Entity entity) {
        if (!canCast()) {
            return;
        }

        Spell subspell = context.getNextSubspell();
        ModifierData data = ModifierData.fromSpell(subspell);

        if (subspell.isValid() && subspell.getParts().get(0) instanceof ShapePart shape) {
            shape.castOnEntity(entity, entity.getWorld(), caster, context, data, this);
            caster.removeMana(10);
        }
    }

    public SpellExecutor cloneRemaining() {
        return new SpellExecutor(this.plugin, this.caster, this.context.getRemaining(), this.target);
    }

    public void execute(SpellTarget target) {
        setTarget(target);
        execute();
    }

    public void execute() {
        while (context.hasPartsLeft()) {
            Spell spell = context.getNextSubspell();
            if (this.cancelled || !spell.isValid()) {
                break;
            }

            SpellPart firstPart = spell.getParts().get(0);
            ModifierData data = ModifierData.fromSpell(spell);
            if (firstPart instanceof ActionPart actionPart) {
                actionPart.process(this.target, caster, context, data, this);
            } else if (firstPart instanceof SubshapePart subShapePart) {
                this.target = subShapePart.cast(this.target, this.caster, this.caster.getEntity().getWorld(), context,
                        data, this);
            } else if (firstPart instanceof FilterPart filterPart) {
                filterPart.process(this.target, caster, context, data, this);
            }
        }
    }

    public void executeDelayed(int ticks) {
        Bukkit.getScheduler().runTaskLater(this.plugin, () -> execute(), ticks);
    }

    public Spells getPlugin() {
        return plugin;
    }

    public void setTarget(SpellTarget target) {
        this.target = target;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
