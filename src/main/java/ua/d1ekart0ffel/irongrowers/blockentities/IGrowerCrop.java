package ua.d1ekart0ffel.irongrowers.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface IGrowerCrop {

     boolean ironGrowers$growCrop(Level level, BlockPos pos, BlockState state);
}
