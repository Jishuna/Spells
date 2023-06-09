package me.jishuna.spells.spell.filter.block;

import org.bukkit.NamespacedKey;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.FilterPart;
import me.jishuna.spells.api.spell.target.SpellTarget;
import net.md_5.bungee.api.ChatColor;

public class BlockFilter extends FilterPart {
    public static final BlockFilter INSTANCE = new BlockFilter();

    protected BlockFilter() {
        super(NamespacedKey.fromString("filter:block"), 15);

        setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Filter: Block");
        setDefaultLore("Filters out all target entities.");
    }

    @Override
    public void process(SpellTarget target, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor executor) {
        executor.setTarget(target.filter(e -> false, b -> true));
    }
}
