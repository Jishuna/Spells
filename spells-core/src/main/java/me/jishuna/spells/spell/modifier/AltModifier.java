package me.jishuna.spells.spell.modifier;

import org.bukkit.NamespacedKey;

import me.jishuna.spells.api.spell.part.ModifierPart;
import net.md_5.bungee.api.ChatColor;

public class AltModifier extends ModifierPart {
    public static final AltModifier INSTANCE = new AltModifier();

    private AltModifier() {
        super(NamespacedKey.fromString("modifier:alt"), 15);

        setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Alternate");
        setDefaultLore("Changes the function of certain shapes and actions.");
    }
}
