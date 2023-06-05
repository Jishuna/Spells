package me.jishuna.spells.listener;

import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.jishuna.spells.api.SpellsAPI;
import me.jishuna.spells.api.playerdata.PlayerManager;
import me.jishuna.spells.api.spell.part.SpellPart;

public class PartUnlockListener implements Listener {

    private final PlayerManager playerManager;

    public PartUnlockListener(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        this.playerManager.findData(event.getPlayer().getUniqueId()).ifPresent(data -> {
            ItemStack item = event.getItem();
            if (item == null || !item.hasItemMeta()) {
                return;
            }

            SpellPart part = item.getItemMeta().getPersistentDataContainer().get(NamespacedKey.fromString("spells:part"), SpellsAPI.SPELL_PART_TYPE);
            if (part == null) {
                return;
            }

            if (data.unlockPart(part)) {
                item.setAmount(item.getAmount() - 1);
            }
        });
    }
}
