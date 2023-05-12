package me.jishuna.spells.api.spell.target;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;

import com.google.common.collect.ImmutableList;

public class BlockTarget implements SpellTarget {
    private final List<Block> blocks;
    private final Location origin;

    private BlockTarget(List<Block> blocks, Location origin) {
        this.blocks = blocks;
        this.origin = origin;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public Location getOrigin() {
        return origin;
    }

    public static BlockTarget single(Block block, Location origin) {
        return new BlockTarget(Arrays.asList(block), origin);
    }

    public static class Builder {
        private final ImmutableList.Builder<Block> listBuilder = new ImmutableList.Builder<>();
        private Location origin;

        public Builder add(Block block) {
            listBuilder.add(block);
            return this;
        }

        public Builder addAll(Collection<Block> block) {
            listBuilder.addAll(block);
            return this;
        }

        public Builder origin(Location origin) {
            this.origin = origin;
            return this;
        }

        public BlockTarget build() {
            return new BlockTarget(listBuilder.build(), this.origin);
        }
    }
}
