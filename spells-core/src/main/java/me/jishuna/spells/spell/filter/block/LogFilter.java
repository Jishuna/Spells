package me.jishuna.spells.spell.filter.block;

import org.bukkit.NamespacedKey;
import org.bukkit.Tag;

import me.jishuna.spells.api.spell.ModifierData;
import me.jishuna.spells.api.spell.SpellContext;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.SpellCaster;
import me.jishuna.spells.api.spell.part.FilterPart;
import me.jishuna.spells.api.spell.target.SpellTarget;
import net.md_5.bungee.api.ChatColor;

public class LogFilter extends FilterPart {
    public static final LogFilter INSTANCE = new LogFilter();

    protected LogFilter() {
        super(NamespacedKey.fromString("filter:log"), 15);

        setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Filter: Log");
        setDefaultLore("Filters out all blocks except logs, does not effect entities.");
    }

    @Override
    public void process(SpellTarget target, SpellCaster caster, SpellContext context, ModifierData data, SpellExecutor executor) {
        executor.setTarget(target.filter(e -> true, b -> Tag.LOGS.isTagged(b.getType())));
    }
}
