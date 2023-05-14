package me.jishuna.spells.api.spell.target;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class BlockTarget extends SpellTarget {
    private final Block originBlock;
    private final BlockFace hitFace;

    private BlockTarget(Block block, BlockFace face, double radius, double height) {
        super(block, radius, height);
        this.originBlock = block;
        this.hitFace = face;
    }

    public Block getOriginBlock() {
        return originBlock;
    }

    public BlockFace getHitFace() {
        return hitFace;
    }

    public static BlockTarget create(Block block, BlockFace face) {
        return new BlockTarget(block, face, 0, 0);
    }

    public static BlockTarget create(Block block, BlockFace face, double radius, double height) {
        return new BlockTarget(block, face, radius, height);
    }
}
