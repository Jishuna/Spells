package me.jishuna.spells.spell.action;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.ActionPart;
import me.jishuna.spells.api.spell.target.SpellTarget;
import net.md_5.bungee.api.ChatColor;

public class WorkbenchAction extends ActionPart {
    public static final WorkbenchAction INSTANCE = new WorkbenchAction();

    private WorkbenchAction() {
        super(NamespacedKey.fromString("action:workbench"), 15);

        setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Workbench");
        setDefaultLore("Opens a crafting table inventory for the caster.");
        
        setRecipe(Material.CRAFTING_TABLE, Material.STONE);
    }

    @Override
    public void process(SpellTarget target, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor executor) {
        if (caster.getEntity() instanceof Player player) {
            player.openWorkbench(null, true);
        }
    }
}
