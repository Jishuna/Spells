package me.jishuna.spells.api.registry;

import me.jishuna.spells.Spells;

public class RegistryHolder {
    private final SpellPartRegistry partRegistry;
    private final AltarRecipeRegistry altarRecipeRegistry;

    public RegistryHolder(Spells plugin) {
        this.altarRecipeRegistry = new AltarRecipeRegistry();
        this.partRegistry = new SpellPartRegistry(plugin);
    }

    public SpellPartRegistry getSpellPartRegistry() {
        return partRegistry;
    }

    public AltarRecipeRegistry getAltarRecipeRegistry() {
        return altarRecipeRegistry;
    }

    public void initialize() {
        this.partRegistry.initialize();
    }
}
