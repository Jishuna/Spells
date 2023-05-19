package me.jishuna.spells.spell.filter.entity;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.AbstractVillager;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.FilterPart;
import me.jishuna.spells.api.spell.target.SpellTarget;
import net.md_5.bungee.api.ChatColor;

public class VillagerFilter extends FilterPart {
    public static final VillagerFilter INSTANCE = new VillagerFilter();

    protected VillagerFilter() {
        super(NamespacedKey.fromString("filter:villager"), 15);

        setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Filter: Villager");
        setDefaultLore("Filters out all entities except villagers, does not effect blocks.");
    }

    @Override
    public void process(SpellTarget target, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor executor) {
        executor.setTarget(target.filter(AbstractVillager.class::isInstance, b -> true));
    }
}
