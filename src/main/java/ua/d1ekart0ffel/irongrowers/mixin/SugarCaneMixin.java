package ua.d1ekart0ffel.irongrowers.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import ua.d1ekart0ffel.irongrowers.blockentities.IGrowerCrop;
import ua.d1ekart0ffel.irongrowers.blocks.GrowerBlock;

@Mixin(SugarCaneBlock.class)
public class SugarCaneMixin implements IGrowerCrop {

    @Override
    public boolean ironGrowers$growCrop(Level level, BlockPos pos, BlockState state) {
        int i = 2;
        if (level.getBlockState(pos.above(i)).is(Blocks.SUGAR_CANE)) {
            return false;
        }
         if (level.getBlockState(pos.above(i-1)).is(Blocks.SUGAR_CANE) && level.isEmptyBlock(pos.above(i))) {
            level.setBlockAndUpdate(pos.above(i), Blocks.SUGAR_CANE.defaultBlockState());
            return true;
        } else if (level.isEmptyBlock(pos.above(i-1))) {
            level.setBlockAndUpdate(pos.above(), Blocks.SUGAR_CANE.defaultBlockState());
            return true;
        } else {
            return false;
        }
    }

    @WrapOperation(
            method = "canSurvive",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;canBeHydrated(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/material/FluidState;Lnet/minecraft/core/BlockPos;)Z")
    )
    private boolean isGrowerBlock(BlockState instance, BlockGetter blockGetter, BlockPos pos, FluidState fluidState, BlockPos blockPos, Operation<Boolean> original) {
        for (BlockPos blockScanPos : BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 0, 4))) {
            pos = blockScanPos;
            if (blockGetter.getBlockState(pos).getBlock() instanceof GrowerBlock) {
                break;
            }
        }
        return original.call(instance, blockGetter, pos, fluidState, blockPos) || blockGetter.getBlockState(pos).getBlock() instanceof GrowerBlock;
    }
}
