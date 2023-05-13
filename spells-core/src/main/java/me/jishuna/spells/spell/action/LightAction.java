package me.jishuna.spells.spell.action;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Light;
import org.bukkit.entity.Entity;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ActionPart;

public class LightAction extends ActionPart {
    public static final LightAction INSTANCE = new LightAction();

    private LightAction() {
        super(NamespacedKey.fromString("action:light"));
    }

    @Override
    public void processEntity(Entity target, SpellCaster caster, SpellContext context, ModifierData data) {
    }

    @Override
    public void processBlock(Block target, BlockFace targetFace, SpellCaster caster, SpellContext context,
            ModifierData data) {
        Block targetBlock = target.getRelative(targetFace);

        if (!targetBlock.getType().isAir()) {
            return;
        }

        int lightLevel = 11;
        if (data.getEmpowerAmount() > 0) {
            lightLevel = 15;
        }

        Light light = (Light) Material.LIGHT.createBlockData();
        light.setLevel(lightLevel);

        targetBlock.setBlockData(light);
    }
}
