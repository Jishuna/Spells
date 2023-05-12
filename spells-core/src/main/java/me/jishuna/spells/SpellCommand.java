package me.jishuna.spells;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.FluidCollisionMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.StringUtil;

import me.jishuna.jishlib.commands.SimpleCommandHandler;
import me.jishuna.spells.api.spell.Spell;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.PlayerSpellCaster;

public class SpellCommand extends SimpleCommandHandler {
    private final Spells plugin;

    protected SpellCommand(Spells plugin) {
        super("spells.cast");
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        PlayerSpellCaster caster = new PlayerSpellCaster(player);
        Spell.Builder builder = new Spell.Builder();

        for (String key : args) {
            plugin.getSpellPartRegistry().getIfPresent(key).ifPresent(builder::part);
        }

        RayTraceResult result = player.getWorld().rayTrace(player.getEyeLocation(),
                player.getEyeLocation().getDirection(), 4, FluidCollisionMode.NEVER, true, 0, e -> e != player);
        SpellExecutor resolver = new SpellExecutor(this.plugin, caster, builder.build());

        if (result == null) {
            resolver.handleCast(player.getWorld());
            return true;
        }

        if (result.getHitEntity() instanceof LivingEntity entity) {
            resolver.handleEntityCast(entity);
        } else if (result.getHitBlock() != null) {
            resolver.handleBlockCast(result.getHitBlock());
        } else {
            resolver.handleCast(player.getWorld());
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return StringUtil.copyPartialMatches(args[args.length - 1], plugin.getSpellPartRegistry().getStringKeys(),
                new ArrayList<>());
    }
}
