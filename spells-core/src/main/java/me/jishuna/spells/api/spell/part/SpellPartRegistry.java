package me.jishuna.spells.api.spell.part;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.NamespacedKey;

import com.google.common.collect.ImmutableSet;

import me.jishuna.jishlib.config.ReloadableClass;
import me.jishuna.spells.Spells;
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
    private final Set<ReloadableClass<? extends SpellPart>> reloadables = new HashSet<>();

    private final Spells plugin;
    private final File configFile;

    public SpellPartRegistry(Spells plugin) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), "config.yml");

        addDefaults();
    }

    public void register(SpellPart part) {
        if (!registryMap.containsKey(part.getKey())) {
            this.registryMap.put(part.getKey(), part);

            ReloadableClass<? extends SpellPart> reloadable = plugin.getConfigurationManager()
                    .createStaticReloadable(this.configFile, part.getClass());
            reloadable.saveDefaults().load();

            reloadables.add(reloadable);
        }
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
