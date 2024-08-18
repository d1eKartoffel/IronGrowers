package ua.d1ekart0ffel.irongrowers.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.KelpPlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import ua.d1ekart0ffel.irongrowers.blockentities.IGrowerCrop;

@Mixin(KelpPlantBlock.class)
public class KelpBodyMixin implements IGrowerCrop {

    @Override
    public boolean ironGrowers$growCrop(Level level, BlockPos pos, BlockState state) {
        int i = 2;
        if (level.getBlockState(pos.above(i)).is(Blocks.KELP_PLANT)) {
            return false;
        }
        if (level.getBlockState(pos.above(i-1)).is(Blocks.KELP) && level.getBlockState(pos.above(i)).is(Blocks.WATER)) {
            level.setBlockAndUpdate(pos.above(i-1), Blocks.KELP_PLANT.defaultBlockState());
            level.setBlockAndUpdate(pos.above(i), Blocks.KELP.defaultBlockState());
            return true;
        } else {
            return false;
        }
    }
}
