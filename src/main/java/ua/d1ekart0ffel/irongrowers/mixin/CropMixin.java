package ua.d1ekart0ffel.irongrowers.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import ua.d1ekart0ffel.irongrowers.blockentities.IGrowerCrop;

@Mixin(CropBlock.class)
public abstract class CropMixin implements IGrowerCrop {

    @Shadow public abstract int getMaxAge();

    @Shadow public abstract int getAge(BlockState state);

    @Shadow @Final public static IntegerProperty AGE;

    @Override
    public boolean ironGrowers$growCrop(Level level, BlockPos pos, BlockState state) {
        int age = getAge(state);
        if (age<getMaxAge()) {
            level.setBlock(pos, state.setValue(AGE, getAge(state) + 1),2);
            return true;
        }
        return false;
    }
}
