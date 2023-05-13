package me.jishuna.spells.api.spell.target;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class BlockTarget extends SpellTarget {
    private final Block originBlock;
    private final BlockFace hitFace;

    private BlockTarget(Block block, BlockFace face, double radius, double height) {
        super(block.getLocation().add(0.5, 0.5, 0.5), radius, height);
        this.originBlock = block;
        this.hitFace = face;
    }

    @Override
    public Collection<Block> getTargetBlocks() {
        Set<Block> blocks = new HashSet<>(super.getTargetBlocks());
        blocks.add(this.originBlock);

        return blocks;
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
