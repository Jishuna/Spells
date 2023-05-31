package me.jishuna.spells.storage;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import me.jishuna.spells.Spells;
import me.jishuna.spells.api.spell.part.SpellPart;

public class YamlStorageAdapter implements StorageAdapter {
    private final Spells plugin;
    private final File storageFolder;

    private YamlStorageAdapter(File storageFolder, Spells plugin) {
        this.plugin = plugin;
        this.storageFolder = storageFolder;
    }

    @Override
    public void initalize() {
        // NO-OP
    }

    @Override
    public CompletableFuture<Set<SpellPart>> getUnlockedParts(UUID id) {
        return CompletableFuture.supplyAsync(() -> {
            Set<SpellPart> parts = new HashSet<>();

            File file = getDataFile(id, false);
            if (file == null) {
                return parts;
            }

            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            for (String part : config.getStringList("unlocked-parts")) {
                this.plugin.getSpellPartRegistry().find(part).ifPresent(parts::add);
            }

            return parts;
        });
    }

    @Override
    public void unlockPart(UUID id, SpellPart part) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            File file = getDataFile(id, true);
            if (file == null) {
                return;
            }

            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            List<String> parts = config.getStringList("unlocked-parts");
            parts.add(part.getKey().toString());
            config.set("unlocked-parts", parts);

            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private File getDataFile(UUID id, boolean create) {
        File file = new File(storageFolder, id.toString() + ".yml");
        if (!file.exists() && create) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return file.exists() ? file : null;
    }

    public static YamlStorageAdapter create(Spells plugin) {
        File storageFolder = new File(plugin.getDataFolder(), "storage");
        if (!storageFolder.exists()) {
            storageFolder.mkdirs();
        }

        return new YamlStorageAdapter(storageFolder, plugin);
    }

}
