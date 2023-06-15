package me.jishuna.spells.api.altar;

import org.bukkit.block.data.BlockData;
import org.bukkit.util.Vector;

public record BlockInfo(Vector offset, BlockData data) {
}
