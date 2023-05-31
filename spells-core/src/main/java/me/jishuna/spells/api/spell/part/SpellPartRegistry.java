package me.jishuna.spells.api.spell.part;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.NamespacedKey;

import com.google.common.collect.ImmutableSet;

import me.jishuna.jishlib.config.ReloadableObject;
import me.jishuna.spells.Spells;
import me.jishuna.spells.spell.action.BreakAction;
import me.jishuna.spells.spell.action.BurnAction;
import me.jishuna.spells.spell.action.CollectAction;
import me.jishuna.spells.spell.action.DelayAction;
import me.jishuna.spells.spell.action.ExplodeAction;
import me.jishuna.spells.spell.action.FangAction;
import me.jishuna.spells.spell.action.HarmAction;
import me.jishuna.spells.spell.action.HealAction;
import me.jishuna.spells.spell.action.LaunchAction;
import me.jishuna.spells.spell.action.LightAction;
import me.jishuna.spells.spell.action.LightningAction;
import me.jishuna.spells.spell.action.RecallAction;
import me.jishuna.spells.spell.action.SlowfallAction;
import me.jishuna.spells.spell.action.SmeltAction;
import me.jishuna.spells.spell.action.TillAction;
import me.jishuna.spells.spell.action.WarpAction;
import me.jishuna.spells.spell.action.WorkbenchAction;
import me.jishuna.spells.spell.filter.block.BlockFilter;
import me.jishuna.spells.spell.filter.block.LogFilter;
import me.jishuna.spells.spell.filter.entity.AnimalFilter;
import me.jishuna.spells.spell.filter.entity.EntityFilter;
import me.jishuna.spells.spell.filter.entity.MonsterFilter;
import me.jishuna.spells.spell.filter.entity.PlayerFilter;
import me.jishuna.spells.spell.filter.entity.VillagerFilter;
import me.jishuna.spells.spell.modifier.AltModifier;
import me.jishuna.spells.spell.modifier.EmpowerModifier;
import me.jishuna.spells.spell.modifier.PierceModifier;
import me.jishuna.spells.spell.modifier.ProlongModifier;
import me.jishuna.spells.spell.shape.BeamShape;
import me.jishuna.spells.spell.shape.ProjectileShape;
import me.jishuna.spells.spell.shape.SelfShape;
import me.jishuna.spells.spell.shape.TouchShape;
import me.jishuna.spells.spell.subshape.AreaSubshape;

public class SpellPartRegistry {
    private final Map<NamespacedKey, SpellPart> registryMap = new LinkedHashMap<>();
    private final Set<ReloadableObject<? extends SpellPart>> reloadables = new HashSet<>();

    private final Spells plugin;

    public SpellPartRegistry(Spells plugin) {
        this.plugin = plugin;
    }

    public void initialize() {
        addDefaults();
    }

    public void register(SpellPart part) {
        if (!registryMap.containsKey(part.getKey())) {
            this.registryMap.put(part.getKey(), part);
            if (part == SpellPart.EMPTY) {
                return;
            }

            File configFile = new File(plugin.getDataFolder(), "config/" + part.getConfigFolder() + part.getKey().getKey() + ".yml");

            ReloadableObject<? extends SpellPart> reloadable = plugin.getConfigurationManager().createReloadable(configFile, part);
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
        register(AltModifier.INSTANCE);
        register(PierceModifier.INSTANCE);

        // Actions
        register(BurnAction.INSTANCE);
        register(CollectAction.INSTANCE);
        register(BreakAction.INSTANCE);
        register(ExplodeAction.INSTANCE);
        register(LightAction.INSTANCE);
        register(HarmAction.INSTANCE);
        register(HealAction.INSTANCE);
        register(WarpAction.INSTANCE);
        register(RecallAction.INSTANCE);
        register(LaunchAction.INSTANCE);
        register(LightningAction.INSTANCE);
        register(SmeltAction.INSTANCE);
        register(DelayAction.INSTANCE);
        register(SlowfallAction.INSTANCE);
        register(TillAction.INSTANCE);
        register(FangAction.INSTANCE);
        register(WorkbenchAction.INSTANCE);

        // Block Filters
        register(BlockFilter.INSTANCE);
        register(LogFilter.INSTANCE);

        // Entity Filters
        register(EntityFilter.INSTANCE);
        register(PlayerFilter.INSTANCE);
        register(VillagerFilter.INSTANCE);
        register(MonsterFilter.INSTANCE);
        register(AnimalFilter.INSTANCE);
    }
}
