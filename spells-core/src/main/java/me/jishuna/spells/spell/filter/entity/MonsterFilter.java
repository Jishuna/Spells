package me.jishuna.spells.spell.filter.entity;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Monster;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.FilterPart;
import me.jishuna.spells.api.spell.target.SpellTarget;
import net.md_5.bungee.api.ChatColor;

public class MonsterFilter extends FilterPart {
    public static final MonsterFilter INSTANCE = new MonsterFilter();

    protected MonsterFilter() {
        super(NamespacedKey.fromString("filter:monster"), 15);

        setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Filter: Monster");
        setDefaultLore("Filters out all entities except monsters, does not effect blocks.");

        setRecipe(Material.ROTTEN_FLESH, Material.BONE, Material.STONE);
    }

    @Override
    public void process(SpellTarget target, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor executor) {
        executor.setTarget(target.filter(Monster.class::isInstance, b -> true));
    }
}
