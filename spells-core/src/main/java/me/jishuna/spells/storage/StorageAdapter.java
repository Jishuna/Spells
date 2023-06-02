package me.jishuna.spells.storage;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import me.jishuna.spells.api.spell.part.SpellPart;

public interface StorageAdapter {
    public void initalize();

    public CompletableFuture<Set<SpellPart>> getUnlockedParts(UUID id);

    public void unlockPart(UUID id, SpellPart part);
}
