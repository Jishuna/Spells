package me.jishuna.spells.spell.action;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ActionPart;
import me.jishuna.spells.spell.modifier.EmpowerModifier;
import net.md_5.bungee.api.ChatColor;

public class ExplodeAction extends ActionPart {
    public static final ExplodeAction INSTANCE = new ExplodeAction();

    private ExplodeAction() {
        super(NamespacedKey.fromString("action:explode"), 15);

        setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Explode");
        setDefaultLore("Causes an explosion at the target. Empower will increase the size of the explosion.");

        addAllowedModifiers(EmpowerModifier.INSTANCE);

        setRecipe(Material.TNT, Material.STONE);
    }

    @Override
    public void processEntity(Entity target, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor executor) {
        explode(target.getLocation(), caster, data);
    }

    @Override
    public void processBlock(Block target, BlockFace targetFace, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor executor) {
        explode(target.getRelative(targetFace).getLocation().add(0.5, 0, 0.5), caster, data);
    }

    private void explode(Location location, SpellCaster caster, ModifierData data) {
        float power = 1f + (0.5f * data.getEmpowerAmount());

        location.getWorld().createExplosion(location, power, false, true, caster.getEntity());
    }
}
