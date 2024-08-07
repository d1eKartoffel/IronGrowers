package ua.d1ekart0ffel.irongrowers.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import ua.d1ekart0ffel.irongrowers.IronGrowers;
import ua.d1ekart0ffel.irongrowers.blockentities.GrowerBlockEntity;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, IronGrowers.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GrowerBlockEntity>> GROWER_BLOCK_ENTITY = BLOCK_ENTITIES.register("grower_block_entity",
            ()-> BlockEntityType.Builder.of(GrowerBlockEntity::new,
                    BlockRegistry.COPPER_GROWER_BLOCK.get(),
                    BlockRegistry.IRON_GROWER_BLOCK.get(),
                    BlockRegistry.GOLD_GROWER_BLOCK.get(),
                    BlockRegistry.DIAMOND_GROWER_BLOCK.get(),
                    BlockRegistry.NETHERITE_GROWER_BLOCK.get()).build(null));

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITIES.register(modEventBus);
    }
}
