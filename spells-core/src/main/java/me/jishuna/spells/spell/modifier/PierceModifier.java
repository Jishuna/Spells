package me.jishuna.spells.spell.modifier;

import org.bukkit.NamespacedKey;

import me.jishuna.spells.api.spell.part.ModifierPart;
import net.md_5.bungee.api.ChatColor;

public class PierceModifier extends ModifierPart {
    public static final PierceModifier INSTANCE = new PierceModifier();

    private PierceModifier() {
        super(NamespacedKey.fromString("modifier:pierce"), 15);

        setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Pierce");
        setDefaultLore("Increases the number of possible targets for various shapes and actions.");
    }
}
