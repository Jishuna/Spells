package me.jishuna.spells.api.spell.part;

import java.util.Objects;

import org.bukkit.NamespacedKey;

public abstract class SpellPart {
    public static final SpellPart EMPTY = new SpellPart(NamespacedKey.fromString("part:empty")) {
    };

    private final NamespacedKey key;

    protected SpellPart(NamespacedKey key) {
        this.key = key;
    }

    public NamespacedKey getKey() {
        return key;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof SpellPart part) {
            return Objects.equals(this.key, part.key);
        }
        return false;
    }
}
