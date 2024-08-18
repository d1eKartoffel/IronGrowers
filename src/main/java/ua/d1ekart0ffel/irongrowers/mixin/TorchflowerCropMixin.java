package ua.d1ekart0ffel.irongrowers.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TorchflowerCropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import ua.d1ekart0ffel.irongrowers.blockentities.IGrowerCrop;

import static net.minecraft.world.level.block.TorchflowerCropBlock.AGE;

@Mixin(TorchflowerCropBlock.class)
public class TorchflowerCropMixin implements IGrowerCrop {

    @Override
    public boolean ironGrowers$growCrop(Level level, BlockPos pos, BlockState state) {
        int age = state.getValue(TorchflowerCropBlock.AGE);
        if (age<1) {
            level.setBlock(pos, state.setValue(AGE, state.getValue(TorchflowerCropBlock.AGE) + 1),2);
            return true;
        } else if (age == 1){
            level.setBlock(pos, Blocks.TORCHFLOWER.defaultBlockState(), 2);
        }
        return false;
    }
}
