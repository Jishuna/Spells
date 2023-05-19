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
import net.md_5.bungee.api.ChatColor;

public class ProjectileShape extends ShapePart {
    public static final ProjectileShape INSTANCE = new ProjectileShape();

    @Comment("The size of the projectile, higher numbers make the projectile more lenient when checking for collision.")
    @ConfigEntry("size")
    public static double PROJECTILE_SIZE = 0.1;
    
    @Comment("The range of the projectile, if it fails to hit anything within this range the spell will fail.")
    @ConfigEntry("range")
    public static int PROJECTILE_RANGE = 50;

    private ProjectileShape() {
        super(NamespacedKey.fromString("shape:projectile"), 15);
        
        setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Projectile");
        setDefaultLore("Launches a slow moving but long range projectile that targets whatever it hits.");
    }

    @Override
    public void cast(SpellCaster caster, World world, SpellContext context, ModifierData data, SpellExecutor resolver) {
        Location location = caster.getEntity().getEyeLocation();
        SpellProjectile spellProjectile = new SpellProjectile(caster, location, location.getDirection().normalize(), resolver, PROJECTILE_SIZE, 50);
        spellProjectile.runTask(resolver.getPlugin(), 0, 1);
    }

    @Override
    public void castOnEntity(Entity entity, World world, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor resolver) {
        cast(caster, world, context, data, resolver);
    }

    @Override
    public void castOnBlock(Block block, BlockFace face, World world, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor resolver) {
        cast(caster, world, context, data, resolver);
    }
}
