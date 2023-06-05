package me.jishuna.spells.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.jishuna.spells.Spells;
import me.jishuna.spells.api.playerdata.PlayerSpellData;
import me.jishuna.spells.api.spell.Spell;
import me.jishuna.spells.api.spell.SpellBuilder;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.PlayerSpellCaster;
import me.jishuna.spells.api.spell.util.SpellUtil;
import me.jishuna.spells.inventory.SpellBuilderInventory;

public class CastingListener implements Listener {
    private final Spells plugin;

    public CastingListener(Spells plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getItem() == null || event.getItem().getType() != Material.BOOK) {
            return;
        }

        Player player = event.getPlayer();
        PlayerSpellData data = plugin.getPlayerManager().getData(player.getUniqueId());
        if (data == null) {
            return;
        }

        PlayerSpellCaster caster = new PlayerSpellCaster(data);
        ItemStack item = event.getItem();
        Spell spell = SpellUtil.getSpell(item);

        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            SpellBuilder builder = spell == null ? new SpellBuilder() : SpellBuilder.modifySpell(spell);
            SpellBuilderInventory inventory = new SpellBuilderInventory(this.plugin, data, item, builder);
            this.plugin.getInventoryManager().openInventory(player, inventory);
            event.setCancelled(true);
        } else {
            if (spell == null) {
                return;
            }

            SpellExecutor executor = new SpellExecutor(this.plugin, caster, spell);

            if (event.getClickedBlock() == null) {
                executor.handleCast(player.getWorld());
            } else {
                executor.handleBlockCast(event.getClickedBlock(), event.getBlockFace());
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        PlayerSpellData data = plugin.getPlayerManager().getData(player.getUniqueId());
        if (data == null) {
            return;
        }

        PlayerSpellCaster caster = new PlayerSpellCaster(data);
        ItemStack item = player.getEquipment().getItem(event.getHand());
        Spell spell = SpellUtil.getSpell(item);
        if (spell == null) {
            return;
        }

        SpellExecutor executor = new SpellExecutor(this.plugin, caster, spell);
        executor.handleEntityCast(event.getRightClicked());

        event.setCancelled(true);
    }
}
