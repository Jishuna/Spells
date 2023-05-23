package me.jishuna.spells.spell.action;

import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EvokerFangs;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ActionPart;
import net.md_5.bungee.api.ChatColor;

public class FangAction extends ActionPart {
    public static final FangAction INSTANCE = new FangAction();

    private FangAction() {
        super(NamespacedKey.fromString("action:fangs"), 15);

        setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Fangs");
        setDefaultLore("Summons fangs from the ground to bite at the target.");
    }

    @Override
    public void processEntity(Entity target, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor executor) {
        target.getWorld().spawn(target.getLocation(), EvokerFangs.class, fang -> fang.setOwner(caster.getEntity()));
    }

    @Override
    public void processBlock(Block target, BlockFace targetFace, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor executor) {
        target.getWorld().spawn(target.getLocation().add(0, 1, 0), EvokerFangs.class, fang -> fang.setOwner(caster.getEntity()));
    }
}
