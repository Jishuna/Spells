package me.jishuna.spells.api.spell.target;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.block.Block;

public class BlockTarget extends SpellTarget {
    private final Block originBlock;

    private BlockTarget(Block block, double radius, double height) {
        super(block.getLocation().add(0.5, 0.5, 0.5), radius, height);
        this.originBlock = block;
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

    public static BlockTarget create(Block block) {
        return new BlockTarget(block, 0, 0);
    }

    public static BlockTarget create(Block block, double radius, double height) {
        return new BlockTarget(block, radius, height);
    }
}
