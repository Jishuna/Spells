package me.jishuna.spells.playerdata;

import java.text.MessageFormat;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;

import me.jishuna.spells.api.spell.util.SpellUtil;
import net.md_5.bungee.api.ChatColor;

public class PlayerSpellData {
    private final BossBar bar = Bukkit.createBossBar("", BarColor.BLUE, BarStyle.SOLID);

    private Player player;
    private double mana;
    private double maxMana;
    private boolean manaBarDisplayed;

    public PlayerSpellData(double mana, double maxMana) {
        this.mana = mana;
        this.maxMana = maxMana;
    }

    public PlayerSpellData withPlayer(Player player) {
        this.player = player;
        return this;
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

    public void tick() {
        EntityEquipment equipment = player.getEquipment();
        if (!this.manaBarDisplayed) {
            if (SpellUtil.hasSpell(equipment.getItemInMainHand()) || SpellUtil.hasSpell(equipment.getItemInOffHand())) {
                bar.setProgress(this.mana / this.maxMana);
                bar.setTitle(ChatColor.DARK_AQUA + MessageFormat.format("Mana: {0}/{1}", this.mana, this.maxMana));
                bar.addPlayer(this.player);
                this.manaBarDisplayed = true;
            }
        } else {
            if (!SpellUtil.hasSpell(equipment.getItemInMainHand()) && !SpellUtil.hasSpell(equipment.getItemInOffHand())) {
                bar.removePlayer(this.player);
                this.manaBarDisplayed = false;
                return;
            }
            bar.setProgress(this.mana / this.maxMana);
            bar.setTitle(ChatColor.DARK_AQUA + MessageFormat.format("Mana: {0}/{1}", this.mana, this.maxMana));
        }
    }

    public Player getPlayer() {
        return player;
    }

    public double getMana() {
        return mana;
    }

    public double getMaxMana() {
        return maxMana;
    }
}
