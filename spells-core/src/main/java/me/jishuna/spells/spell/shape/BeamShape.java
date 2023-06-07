package me.jishuna.spells.spell.shape;

import org.bukkit.Location;
import org.bukkit.Material;
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
import me.jishuna.spells.spell.modifier.EmpowerModifier;
import net.md_5.bungee.api.ChatColor;

public class BeamShape extends ShapePart {
    public static final BeamShape INSTANCE = new BeamShape();

    @Comment("The size of the beam, higher numbers make the beam more lenient when checking for collision.")
    @ConfigEntry("size")
    public static double BEAM_SIZE = 0.1;

    @Comment("The bonus length of the beam per empower modifier.")
    @ConfigEntry("bonus-length")
    public static int BONUS_LENGTH = 3;

    private BeamShape() {
        super(NamespacedKey.fromString("shape:beam"), 15);

        setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Beam");
        setDefaultLore("Launches a short range beam that instantly targets whatever it hits. Empower will increase the range of the beam.");

        addAllowedModifiers(EmpowerModifier.INSTANCE);
        setRecipe(Material.PRISMARINE_SHARD, Material.PRISMARINE_CRYSTALS, Material.STONE);
    }

    @Override
    public void cast(SpellCaster caster, World world, SpellContext context, ModifierData data, SpellExecutor resolver) {
        int length = 5 + (BONUS_LENGTH * data.getEmpowerAmount());
        Location location = caster.getEntity().getEyeLocation();

        SpellProjectile spellProjectile = new SpellProjectile(caster, location, location.getDirection().normalize(), resolver, context.getSpellColor(), BEAM_SIZE, length);

        for (int i = 0; i < length; i++) {
            spellProjectile.run();
        }
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
