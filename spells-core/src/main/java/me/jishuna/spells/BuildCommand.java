package me.jishuna.spells;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.jishuna.jishlib.commands.SimpleCommandHandler;

public class BuildCommand extends SimpleCommandHandler {
    private final Spells plugin;

    protected BuildCommand(Spells plugin) {
        super("spells.build");
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
     //   plugin.getInventoryManager().openInventory(player, new SpellBuilderInventory(player.getEquipment().getItemInMainHand(), plugin.getSpellPartRegistry()));
        return true;
    }
}
