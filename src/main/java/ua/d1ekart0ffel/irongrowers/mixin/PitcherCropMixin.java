package ua.d1ekart0ffel.irongrowers.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PitcherCropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.spongepowered.asm.mixin.Mixin;
import ua.d1ekart0ffel.irongrowers.blockentities.IGrowerCrop;

@Mixin(PitcherCropBlock.class)

public class PitcherCropMixin implements IGrowerCrop {

    @Override
    public boolean ironGrowers$growCrop(Level level, BlockPos pos, BlockState state) {
        int age = state.getValue(PitcherCropBlock.AGE)+1;
        if (age >= 5) return false;
        if (age <= 2 || (level.getBlockState(pos.above()).isAir() || level.getBlockState(pos.above()).is(Blocks.PITCHER_CROP))) {
            BlockState blockstate = state.setValue(PitcherCropBlock.AGE, age);
            level.setBlock(pos, blockstate, 2);
            if (age >= 3) {
                level.setBlock(pos.above(), blockstate.setValue(PitcherCropBlock.HALF, DoubleBlockHalf.UPPER), 3);
                return true;
            }
        }
        return false;
    }
}
