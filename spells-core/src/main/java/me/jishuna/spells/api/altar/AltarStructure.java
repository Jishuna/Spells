package me.jishuna.spells.api.altar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.structure.Structure;
import org.bukkit.util.Vector;

public class AltarStructure {

    private final Vector lecternPos;
    private final Structure structure;
    private final Map<BlockFace, List<BlockInfo>> rotations = new HashMap<>(4);

    public AltarStructure(Structure structure) {
        this.structure = structure;
        this.lecternPos = findLectern();

        cacheRotations();
    }

    public boolean isValidStructure(Location center, BlockFace face) {
        List<BlockInfo> blocks = this.rotations.get(face);
        if (blocks == null) {
            return false;
        }

        int widthX = this.structure.getSize().getBlockX();
        int widthZ = this.structure.getSize().getBlockZ();
        Location origin = center.clone().subtract(rotate(this.lecternPos, widthX, widthZ, face));

        for (BlockInfo block : blocks) {
            BlockData data = origin.clone().add(block.offset()).getBlock().getBlockData();
            if (!data.equals(block.data())) {
                return false;
            }
        }
        return true;
    }

    public Vector getLecternPos() {
        return lecternPos;
    }

    public Structure getStructure() {
        return structure;
    }

    private Vector findLectern() {
        for (BlockState state : this.structure.getPalettes().get(0).getBlocks()) {
            if (state.getType() == Material.LECTERN) {
                return state.getLocation().toVector();
            }
        }
        return new Vector();
    }

    private void cacheRotations() {
        int widthX = this.structure.getSize().getBlockX();
        int widthZ = this.structure.getSize().getBlockZ();

        for (BlockFace face : new BlockFace[] { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST }) {
            StructureRotation rotation = toRotation(face);
            List<BlockInfo> blocks = new ArrayList<>();

            for (BlockState state : this.structure.getPalettes().get(0).getBlocks()) {
                if (state.getType().isAir()) {
                    continue;
                }

                Vector offset = state.getLocation().toVector();
                BlockData data = state.getBlockData();
                data.rotate(rotation);

                blocks.add(new BlockInfo(rotate(offset, widthX, widthZ, face), data));
            }

            this.rotations.put(face, blocks);
        }
    }

    private static Vector rotate(Vector offset, int widthX, int widthZ, BlockFace rotation) {
        Vector newOffset;
        switch (rotation) {
        case WEST:
            newOffset = new Vector(offset.getZ(), offset.getY(), widthZ - offset.getX());
            break;
        case EAST:
            newOffset = new Vector(widthX - offset.getZ(), offset.getY(), offset.getX());
            break;
        case SOUTH:
            newOffset = new Vector(widthX - offset.getX(), offset.getY(), widthZ - offset.getZ());
            break;
        case NORTH:
        default:
            newOffset = offset.clone();
        }

        return newOffset;
    }

    public static StructureRotation toRotation(BlockFace face) {
        return switch (face) {
        case NORTH -> StructureRotation.NONE;
        case EAST -> StructureRotation.CLOCKWISE_90;
        case WEST -> StructureRotation.COUNTERCLOCKWISE_90;
        case SOUTH -> StructureRotation.CLOCKWISE_180;
        default -> StructureRotation.NONE;
        };
    }
}
