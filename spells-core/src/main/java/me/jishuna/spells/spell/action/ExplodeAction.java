package me.jishuna.spells.spell.action;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
        explode(target.getLocation(), caster, data);
    }

    @Override
    public void processBlock(Block target, BlockFace targetFace, SpellCaster caster, SpellContext context,
            ModifierData data) {
        explode(target.getLocation().add(targetFace.getModX() + 0.5, targetFace.getModY() + 0.5,
                targetFace.getModZ() + 0.5), caster, data);
    }

    private void explode(Location location, SpellCaster caster, ModifierData data) {
        float power = 1f + (0.5f * data.getEmpowerAmount());

        location.getWorld().createExplosion(location, power, false, true, caster.getEntity());
    }
}
