package ua.d1ekart0ffel.irongrowers.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import ua.d1ekart0ffel.irongrowers.blocks.GrowerBlock;

@Mixin(FarmBlock.class)
public abstract class WaterMixin {

    @WrapOperation(
            method = "isNearWater",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;canBeHydrated(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/material/FluidState;Lnet/minecraft/core/BlockPos;)Z")
    )
    private static boolean isGrowerBlock(BlockState instance, BlockGetter blockGetter, BlockPos pos, FluidState fluidState, BlockPos blockPos, Operation<Boolean> original) {
        return original.call(instance, blockGetter, pos, fluidState, blockPos) || blockGetter.getBlockState(blockPos).getBlock() instanceof GrowerBlock;
    }
}



