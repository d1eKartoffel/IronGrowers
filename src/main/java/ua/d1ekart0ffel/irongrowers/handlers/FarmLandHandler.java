package ua.d1ekart0ffel.irongrowers.handlers;

import net.minecraft.core.BlockPos;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import ua.d1ekart0ffel.irongrowers.blocks.GrowerBlock;

@EventBusSubscriber(modid = "irongrowers")
public class FarmLandHandler {
    @SubscribeEvent
    public static void onFarmlandTrample(BlockEvent.FarmlandTrampleEvent event) {
        BlockPos pos = event.getPos();
        for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 1, 4))) {
            if (event.getLevel().getBlockState(blockpos).getBlock() instanceof GrowerBlock) {
                event.setCanceled(true);
            }
        }
    }
}
