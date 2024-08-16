package ua.d1ekart0ffel.irongrowers.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import ua.d1ekart0ffel.irongrowers.blockentities.GrowerBlockEntity;
import ua.d1ekart0ffel.irongrowers.registry.BlockEntityRegistry;

public class GrowerBlock extends BaseEntityBlock {

    public GrowerBlock(Properties properties, double growthInterval, int radius, int growthChance) {
        super(properties);
        this.growthInterval = growthInterval;
        this.radius = radius;
        this.growthChance = growthChance;
    }

    private final double growthInterval;
    private final int radius;
    private final int growthChance;

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    //Open menu on click
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            this.openContainer(level, pos, player);
            return InteractionResult.CONSUME;
        }
    }

    //Open menu
    protected void openContainer(Level level, BlockPos pos, Player player) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof GrowerBlockEntity) {
            player.openMenu((MenuProvider) blockentity);
        }
    }

    //Drop contents on remove
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof GrowerBlockEntity growerBlockEntity) {
                //Drops contents
                if (level instanceof ServerLevel) {
                    Containers.dropContents(level, pos, growerBlockEntity.getDroppableInventory());
                }
                super.onRemove(state, level, pos, newState, isMoving);
            } else {
                super.onRemove(state, level, pos, newState, isMoving);
            }
        }
    }

    //Creates GrowerBlockEntity when placed
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return BlockEntityRegistry.GROWER_BLOCK_ENTITY.get().create(blockPos,blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type, BlockEntityRegistry.GROWER_BLOCK_ENTITY.get(), (level1, pos, state1, blockEntity) -> GrowerBlockEntity.serverTick((ServerLevel) level1, pos, state1, blockEntity, growthInterval, radius, growthChance));
    }
}
