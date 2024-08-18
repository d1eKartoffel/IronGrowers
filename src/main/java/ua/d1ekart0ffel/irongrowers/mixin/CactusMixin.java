package ua.d1ekart0ffel.irongrowers.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import ua.d1ekart0ffel.irongrowers.blockentities.IGrowerCrop;

@Mixin(CactusBlock.class)
public class CactusMixin implements IGrowerCrop {
    @Override
    public boolean ironGrowers$growCrop(Level level, BlockPos pos, BlockState state) {
        int i = 2;
        if (level.getBlockState(pos.above(i)).is(Blocks.CACTUS)) {
            return false;
        }
        if (level.getBlockState(pos.above(i-1)).is(Blocks.CACTUS) && level.isEmptyBlock(pos.above(i))) {
            level.setBlockAndUpdate(pos.above(i), Blocks.CACTUS.defaultBlockState());
            return true;
        } else if (level.isEmptyBlock(pos.above(i-1))) {
            level.setBlockAndUpdate(pos.above(), Blocks.CACTUS.defaultBlockState());
            return true;
        } else {
            return false;
        }
    }
}
