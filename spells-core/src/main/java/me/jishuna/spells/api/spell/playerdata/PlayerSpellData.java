package me.jishuna.spells.api.spell.playerdata;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;

import me.jishuna.spells.api.spell.part.SpellPart;
import me.jishuna.spells.api.spell.util.SpellUtil;
import me.jishuna.spells.storage.StorageAdapter;
import net.md_5.bungee.api.ChatColor;

public class PlayerSpellData {
    private final BossBar bar = Bukkit.createBossBar("", BarColor.BLUE, BarStyle.SOLID);
    private final UUID id;
    private final StorageAdapter adapter;

    private Player player;
    private Set<SpellPart> unlockedParts;
    private double mana;
    private double maxMana;
    private boolean manaBarDisplayed;

    public PlayerSpellData(double mana, double maxMana, UUID id, StorageAdapter adapter) {
        this.mana = mana;
        this.maxMana = maxMana;
        this.id = id;
        this.adapter = adapter;
    }

    public void tick() {
        Player player = getPlayer();
        EntityEquipment equipment = player.getEquipment();
        if (!this.manaBarDisplayed) {
            if (SpellUtil.hasSpell(equipment.getItemInMainHand()) || SpellUtil.hasSpell(equipment.getItemInOffHand())) {
                bar.setProgress(this.mana / this.maxMana);
                bar.setTitle(ChatColor.DARK_AQUA + MessageFormat.format("Mana: {0}/{1}", this.mana, this.maxMana));
                bar.addPlayer(player);
                this.manaBarDisplayed = true;
            }
        } else {
            if (!SpellUtil.hasSpell(equipment.getItemInMainHand()) && !SpellUtil.hasSpell(equipment.getItemInOffHand())) {
                bar.removePlayer(player);
                this.manaBarDisplayed = false;
                return;
            }
            bar.setProgress(this.mana / this.maxMana);
            bar.setTitle(ChatColor.DARK_AQUA + MessageFormat.format("Mana: {0}/{1}", this.mana, this.maxMana));
        }
    }

    public Player getPlayer() {
        if (this.player == null) {
            this.player = Bukkit.getPlayer(this.id);
        }
        return this.player;
    }

    public boolean isOnline() {
        Player player = getPlayer();
        return player != null && player.isOnline();
    }

    public boolean hasFullMana() {
        return this.mana >= this.maxMana;
    }

    public boolean hasMana(double amount) {
        return this.mana >= amount;
    }

    public void addMana(double amount) {
        this.mana = Math.min(this.maxMana, this.mana + amount);
    }

    public void removeMana(double amount) {
        this.mana = Math.max(this.mana - amount, 0);
    }

    public double getMana() {
        return mana;
    }

    public double getMaxMana() {
        return maxMana;
    }

    public void unlockPart(SpellPart part) {
        if (this.unlockedParts.contains(part)) {
            return;
        }

        this.unlockedParts.add(part);
        this.adapter.unlockPart(this.id, part);
    }

    public Set<SpellPart> getUnlockedParts() {
        if (this.unlockedParts == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(this.unlockedParts);
    }

    public void loadData() {
        this.adapter.getUnlockedParts(this.id).thenAccept(parts -> this.unlockedParts = parts);
    }
}
