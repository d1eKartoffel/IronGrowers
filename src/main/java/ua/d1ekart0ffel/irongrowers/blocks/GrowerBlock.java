package ua.d1ekart0ffel.irongrowers.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import ua.d1ekart0ffel.irongrowers.blockentities.GrowerBlockEntity;
import ua.d1ekart0ffel.irongrowers.mixin.WaterMixin;
import ua.d1ekart0ffel.irongrowers.registry.BlockEntityRegistry;
import ua.d1ekart0ffel.irongrowers.registry.BlockRegistry;

public class GrowerBlock extends Block implements EntityBlock {

    public GrowerBlock(Properties properties, int growthSpeed, int radius, int growthChance) {
        super(properties);
        this.growthSpeed = growthSpeed;
        this.radius = radius;
        this.growthChance = growthChance;
    }

    private final int growthSpeed;
    private final int radius;
    private final int growthChance;

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new GrowerBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return getTicker(type, BlockEntityRegistry.GROWER_BLOCK_ENTITY.get(), (level1, pos, state1, blockEntity) -> GrowerBlockEntity.tick(level1, pos, state1, blockEntity, growthSpeed, radius, growthChance));
    }

    @Nullable
    public static <A extends BlockEntity, T extends BlockEntity> BlockEntityTicker<T> getTicker(BlockEntityType<T> innerType, BlockEntityType<A> targetType, BlockEntityTicker<? super A> ticker) {
        return targetType == innerType ? (BlockEntityTicker<T>) ticker : null;
    }
}
