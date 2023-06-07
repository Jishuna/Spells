package me.jishuna.spells.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.jishuna.jishlib.commands.SimpleCommandHandler;
import me.jishuna.spells.Spells;
import me.jishuna.spells.api.playerdata.PlayerSpellData;

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

        this.plugin.getRegistryHolder().getSpellPartRegistry().find(args[0]).ifPresent(data::unlockPart);
        return true;
    }
}
