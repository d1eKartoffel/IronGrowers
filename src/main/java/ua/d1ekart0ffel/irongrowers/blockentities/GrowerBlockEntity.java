package ua.d1ekart0ffel.irongrowers.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import ua.d1ekart0ffel.irongrowers.registry.BlockEntityRegistry;

public class GrowerBlockEntity extends BlockEntity {
    public GrowerBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.GROWER_BLOCK_ENTITY.get(), pos, blockState);
    }

    private int tickCount;

    public static void tick(Level level, BlockPos pos, BlockState state, GrowerBlockEntity blockEntity, int growthSpeed, int radius, int growthChance) {

        blockEntity.tickCount++;
        if (level.isClientSide() || blockEntity.tickCount % growthSpeed != 0) {
            return;
        }

        ServerLevel server = (ServerLevel) level;

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        int x1 = x + radius + 1;
        int z1 = z + radius;
        int x2 = x - radius;
        int z2 = z - radius;

        for (int i = 0; i < Math.pow(1 + radius * 2, 2); i++) {

            BlockPos blockScanPos = null;
            if (x1 > x2) {
                x1--;
                blockScanPos = new BlockPos(x1, y, z1);
            } else if (z1 > z2) {
                z1--;
                x1 = x + radius;
                blockScanPos = new BlockPos(x1, y, z1);
            }
            if (blockScanPos != null && level.getRandom().nextInt(100) <= growthChance) {
                BlockState cropstate = level.getBlockState(blockScanPos);

                if (cropstate.getBlock() instanceof CropBlock cropBlock) {
                    int age = cropBlock.getAge(cropstate);
                    int maxAge = cropBlock.getMaxAge();

                    if (age < maxAge) {
                        level.setBlock(blockScanPos, cropBlock.getStateForAge(age + 1), 2);
                        server.sendParticles(ParticleTypes.HAPPY_VILLAGER, blockScanPos.getX()+0.5, blockScanPos.getY()+0.5,blockScanPos.getZ()+0.5, 5, 0.25, 0.25, 0.25, 0.1);
                    }
                }
            }
        }
    }
}

