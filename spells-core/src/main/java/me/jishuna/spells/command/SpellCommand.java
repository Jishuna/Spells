package me.jishuna.spells.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.FluidCollisionMode;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.StringUtil;

import me.jishuna.jishlib.commands.SimpleCommandHandler;
import me.jishuna.spells.Spells;
import me.jishuna.spells.api.spell.SpellBuilder;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.PlayerSpellCaster;
import me.jishuna.spells.playerdata.PlayerSpellData;

public class SpellCommand extends SimpleCommandHandler {
    private final Spells plugin;

    public SpellCommand(Spells plugin) {
        super("spells.cast");
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        PlayerSpellData data = plugin.getPlayerManager().getData(player.getUniqueId());

        if (data == null) {
            return true;
        }

        PlayerSpellCaster caster = new PlayerSpellCaster(data);
        SpellBuilder builder = new SpellBuilder(20);

        for (String key : args) {
            plugin.getSpellPartRegistry().find(key).ifPresent(builder::addPart);
        }

        RayTraceResult result = player.getWorld().rayTrace(player.getEyeLocation(), player.getEyeLocation().getDirection(), 4, FluidCollisionMode.NEVER, true, 0, e -> e != player);
        SpellExecutor resolver = new SpellExecutor(this.plugin, caster, builder.toSpell());

        if (result == null) {
            resolver.handleCast(player.getWorld());
            return true;
        }

        if (result.getHitEntity() instanceof LivingEntity entity) {
            resolver.handleEntityCast(entity);
        } else if (result.getHitBlock() != null) {
            resolver.handleBlockCast(result.getHitBlock(), result.getHitBlockFace());
        } else {
            resolver.handleCast(player.getWorld());
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> options = new ArrayList<>();
        String arg = args[args.length - 1];

        for (NamespacedKey key : this.plugin.getSpellPartRegistry().getKeys()) {
            if (StringUtil.startsWithIgnoreCase(key.toString(), arg) || StringUtil.startsWithIgnoreCase(key.getKey(), arg)) {
                options.add(key.toString());
            }
        }
        return options;
    }
}
