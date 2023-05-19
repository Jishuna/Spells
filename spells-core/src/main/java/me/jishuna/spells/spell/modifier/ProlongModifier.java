package me.jishuna.spells.spell.modifier;

import org.bukkit.NamespacedKey;

import me.jishuna.spells.api.spell.part.ModifierPart;
import net.md_5.bungee.api.ChatColor;

public class ProlongModifier extends ModifierPart {
    public static final ProlongModifier INSTANCE = new ProlongModifier();

    private ProlongModifier() {
        super(NamespacedKey.fromString("modifier:prolong"), 15);

        setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Prolong");
        setDefaultLore("Increases the duration of various actions.");
    }
}
