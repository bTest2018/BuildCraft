package buildcraft.robotics;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.HashMap;
import java.util.Map;

public class ZonePlannerMapChunk {
    public Map<BlockPos, Integer> data = new HashMap<>();

    public ZonePlannerMapChunk load(World world, ChunkPos chunkPos) {
        data.clear();
        Chunk chunk = world.getChunkFromChunkCoords(chunkPos.chunkXPos, chunkPos.chunkZPos);
        for(int x = 0; x < 16; x++) {
            for(int z = 0; z < 16; z++) {
                BlockPos pos = world.getTopSolidOrLiquidBlock(chunkPos.getBlock(0, 0, 0).add(x, 0, z));
                pos = new BlockPos(pos.getX(), pos.getY(), pos.getZ()).subtract(chunkPos.getBlock(0, 0, 0));
                int color = 0;
                while(pos.getY() > 0 && color == 0) {
                    IBlockState state = chunk.getBlockState(pos);
                    Block block = state.getBlock();
                    MapColor mapColor = block.getMapColor(state);
                    color = mapColor.colorValue;
                    pos = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
                }
                data.put(pos, color);
            }
        }
        return this;
    }
}
