package me.jishuna.spells.spell.action;

import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ActionPart;

public class ExplodeAction extends ActionPart {
    public static final ExplodeAction INSTANCE = new ExplodeAction();

    private ExplodeAction() {
        super(NamespacedKey.fromString("action:explode"));
    }

    @Override
    public void processEntity(Entity target, SpellCaster caster, SpellContext context, ModifierData data) {
        float power = 0.5f + (0.5f * data.getEmpowerAmount());

        target.getWorld().createExplosion(target.getLocation(), power, false, true, caster.getEntity());
    }

    @Override
    public void processBlock(Block target, SpellCaster caster, SpellContext context, ModifierData data) {
        float power = 0.5f + (0.5f * data.getEmpowerAmount());

        target.getWorld().createExplosion(target.getLocation(), power, false, true, caster.getEntity());
    }
}
