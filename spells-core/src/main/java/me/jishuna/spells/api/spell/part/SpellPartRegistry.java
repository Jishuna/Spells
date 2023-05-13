package me.jishuna.spells.api.spell.part;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bukkit.NamespacedKey;

import com.google.common.collect.ImmutableSet;

import me.jishuna.spells.spell.action.BreakAction;
import me.jishuna.spells.spell.action.BurnAction;
import me.jishuna.spells.spell.action.CollectAction;
import me.jishuna.spells.spell.action.ExplodeAction;
import me.jishuna.spells.spell.action.LightAction;
import me.jishuna.spells.spell.modifier.EmpowerModifier;
import me.jishuna.spells.spell.modifier.ProlongModifier;
import me.jishuna.spells.spell.shape.BeamShape;
import me.jishuna.spells.spell.shape.ProjectileShape;
import me.jishuna.spells.spell.shape.SelfShape;
import me.jishuna.spells.spell.shape.TouchShape;
import me.jishuna.spells.spell.subshape.AreaSubshape;

public class SpellPartRegistry {
    private final Map<NamespacedKey, SpellPart> registryMap = new HashMap<>();

    public SpellPartRegistry() {
        addDefaults();
    }

    public void register(SpellPart part) {
        this.registryMap.put(part.getKey(), part);
    }

    public SpellPart get(String key) {
        return get(NamespacedKey.fromString(key));
    }

    public SpellPart get(NamespacedKey key) {
        return this.registryMap.get(key);
    }

    public Optional<SpellPart> find(String key) {
        return find(NamespacedKey.fromString(key));
    }

    public Optional<SpellPart> find(NamespacedKey key) {
        return Optional.ofNullable(this.registryMap.get(key));
    }

    public Collection<NamespacedKey> getKeys() {
        return ImmutableSet.copyOf(this.registryMap.keySet());
    }

    public Collection<String> getStringKeys() {
        return getKeys().stream().map(NamespacedKey::toString).collect(Collectors.toSet());
    }

    public Collection<SpellPart> getAllParts() {
        return ImmutableSet.copyOf(this.registryMap.values());
    }

    private void addDefaults() {
        register(SpellPart.EMPTY);

        // Shapes
        register(SelfShape.INSTANCE);
        register(TouchShape.INSTANCE);
        register(ProjectileShape.INSTANCE);
        register(BeamShape.INSTANCE);

        // Subshapes
        register(AreaSubshape.INSTANCE);

        // Modifiers
        register(EmpowerModifier.INSTANCE);
        register(ProlongModifier.INSTANCE);

        // Actions
        register(BurnAction.INSTANCE);
        register(CollectAction.INSTANCE);
        register(BreakAction.INSTANCE);
        register(ExplodeAction.INSTANCE);
        register(LightAction.INSTANCE);
    }
}
