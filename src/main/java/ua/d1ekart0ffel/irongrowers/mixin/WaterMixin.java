package ua.d1ekart0ffel.irongrowers.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import ua.d1ekart0ffel.irongrowers.blocks.GrowerBlock;

@Mixin(FarmBlock.class)
public abstract class WaterMixin {
    /**
     * @author d1eKart0ffel
     * @reason In order to make FarmLand check nearby for GrowerBlock and if true turn FarmLand state to .MOISTURE (in randomTick method)
     */
    @Overwrite
    private static boolean isNearWater(LevelReader level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 1, 4))) {
            if (state.canBeHydrated(level, pos, level.getFluidState(blockpos), blockpos) || level.getBlockState(blockpos).getBlock() instanceof GrowerBlock) {
                return true;
            }
        }
        return net.neoforged.neoforge.common.FarmlandWaterManager.hasBlockWaterTicket(level, pos);
    }
}



