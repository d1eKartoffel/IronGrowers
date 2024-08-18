package ua.d1ekart0ffel.irongrowers.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.KelpBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import ua.d1ekart0ffel.irongrowers.blockentities.IGrowerCrop;

@Mixin(KelpBlock.class)
public class KelpHeadMixin implements IGrowerCrop {

    @Override
    public boolean ironGrowers$growCrop(Level level, BlockPos pos, BlockState state) {
        if (level.getBlockState(pos.above()).is(Blocks.WATER)) {
            level.setBlockAndUpdate(pos, Blocks.KELP_PLANT.defaultBlockState());
            level.setBlockAndUpdate(pos.above(), Blocks.KELP.defaultBlockState());
            return true;
        } else {
            return false;
        }
    }
}
