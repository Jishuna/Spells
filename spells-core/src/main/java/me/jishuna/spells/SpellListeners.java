package me.jishuna.spells;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.jishuna.spells.api.spell.Spell;
import me.jishuna.spells.api.spell.SpellBuilder;
import me.jishuna.spells.api.spell.SpellExecutor;
import me.jishuna.spells.api.spell.caster.PlayerSpellCaster;
import me.jishuna.spells.playerdata.PlayerSpellData;

public class SpellListeners implements Listener {
    private final Spells plugin;

    public SpellListeners(Spells plugin) {
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
        Spell spell = Utils.getSpell(item);

        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            SpellBuilderInventory inventory = new SpellBuilderInventory(item, this.plugin, spell == null ? new SpellBuilder(20) : SpellBuilder.modifySpell(spell));
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
        Spell spell = Utils.getSpell(item);
        if (spell == null) {
            return;
        }

        SpellExecutor executor = new SpellExecutor(this.plugin, caster, spell);
        executor.handleEntityCast(event.getRightClicked());

        event.setCancelled(true);
    }
}
