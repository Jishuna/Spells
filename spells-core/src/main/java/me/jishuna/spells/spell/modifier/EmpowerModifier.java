package me.jishuna.spells.spell.modifier;

import org.bukkit.NamespacedKey;

import me.jishuna.spells.api.spell.part.ModifierPart;
import net.md_5.bungee.api.ChatColor;

public class EmpowerModifier extends ModifierPart {
    public static final EmpowerModifier INSTANCE = new EmpowerModifier();

    private EmpowerModifier() {
        super(NamespacedKey.fromString("modifier:empower"), 15);

        setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Empower");
        setDefaultLore("Increases the power of various actions.");
    }
}
