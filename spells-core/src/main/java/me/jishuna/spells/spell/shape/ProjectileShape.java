package me.jishuna.spells.spell.shape;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;

import me.jishuna.jishlib.config.annotation.Comment;
import me.jishuna.jishlib.config.annotation.ConfigEntry;
import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.SpellProjectile;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ShapePart;

public class ProjectileShape extends ShapePart {
    public static final ProjectileShape INSTANCE = new ProjectileShape();

    @Comment("The size of the projectile, higher numbers make the projectile more lenient when checking for collision.")
    @ConfigEntry("shapes.projectile.size")
    public static double PROJECTILE_SIZE = 0.1;

    private ProjectileShape() {
        super(NamespacedKey.fromString("shape:projectile"));
    }

    @Override
    public void cast(SpellCaster caster, World world, SpellContext context, ModifierData data, SpellExecutor resolver) {
        Location location = caster.getEntity().getEyeLocation();
        SpellProjectile spellProjectile = new SpellProjectile(caster, location, location.getDirection().normalize(),
                resolver);
        spellProjectile.runTask(resolver.getPlugin(), 0, 1);
    }

    @Override
    public void castOnEntity(Entity entity, World world, SpellCaster caster, SpellContext context, ModifierData data,
            SpellExecutor resolver) {
        cast(caster, world, context, data, resolver);
    }

    @Override
    public void castOnBlock(Block block, BlockFace face, World world, SpellCaster caster, SpellContext context,
            ModifierData data, SpellExecutor resolver) {
        cast(caster, world, context, data, resolver);
    }
}
