package me.jishuna.spells.spell.shape;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;

import me.jishuna.jishlib.config.ConfigEntry;
import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellProjectile;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ShapePart;

public class ProjectileShape extends ShapePart {
    public static final ProjectileShape INSTANCE = new ProjectileShape();
    
    @ConfigEntry("shapes.projectile.size")
    public static double PROJECTILE_SIZE = 0.1;

    private ProjectileShape() {
        super(NamespacedKey.fromString("spells:shape_projectile"));
    }

    @Override
    public void cast(SpellCaster caster, World world, SpellContext context, ModifierData data, SpellExecutor resolver) {
        Location location = caster.getEntity().getEyeLocation();
        SpellProjectile spellProjectile = new SpellProjectile(caster, location, location.getDirection().normalize(), resolver);
        spellProjectile.shoot(resolver.getPlugin());
    }

    @Override
    public void castOnEntity(LivingEntity entity, World world, SpellCaster caster, SpellContext context,
            ModifierData data, SpellExecutor resolver) {
        cast(caster, world, context, data, resolver);
    }

    @Override
    public void castOnBlock(Block block, World world, SpellCaster caster, SpellContext context, ModifierData data,
            SpellExecutor resolver) {
        cast(caster, world, context, data, resolver);
    }
}
