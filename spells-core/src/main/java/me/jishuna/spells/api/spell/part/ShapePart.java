package me.jishuna.spells.api.spell.part;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;

public abstract class ShapePart extends SpellPart {
    private final Set<ModifierPart> allowedModifiers = new HashSet<>();

    protected ShapePart(NamespacedKey key, int cost) {
        super(key, cost);
    }

    public abstract void cast(SpellCaster caster, World world, SpellContext context, ModifierData data, SpellExecutor resolver);

    public abstract void castOnEntity(Entity entity, World world, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor resolver);

    public abstract void castOnBlock(Block block, BlockFace face, World world, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor resolver);

    public void addAllowedModifiers(ModifierPart... parts) {
        for (ModifierPart part : parts) {
            this.allowedModifiers.add(part);
        }
    }

    @Override
    public boolean isAllowedModifier(ModifierPart modifier) {
        return this.allowedModifiers.contains(modifier);
    }

    @Override
    public String getConfigFolder() {
        return "shapes/";
    }
}
