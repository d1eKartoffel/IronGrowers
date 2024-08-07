package ua.d1ekart0ffel.irongrowers.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import ua.d1ekart0ffel.irongrowers.IronGrowers;
import ua.d1ekart0ffel.irongrowers.blocks.GrowerBlock;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, IronGrowers.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, IronGrowers.MODID);

    public static final DeferredHolder<Block, GrowerBlock> COPPER_GROWER_BLOCK = BLOCKS.register(
            "copper_grower", ()-> new GrowerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK),200,4, 10));

    public static final DeferredHolder<Block, GrowerBlock> IRON_GROWER_BLOCK = BLOCKS.register(
            "iron_grower", ()-> new GrowerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK),150,4,20));

    public static final DeferredHolder<Block, GrowerBlock> GOLD_GROWER_BLOCK = BLOCKS.register(
            "gold_grower", ()-> new GrowerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK),130,4, 40));

    public static final DeferredHolder<Block, GrowerBlock> DIAMOND_GROWER_BLOCK = BLOCKS.register(
            "diamond_grower", ()-> new GrowerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK),80,4,80));

    public static final DeferredHolder<Block, GrowerBlock> NETHERITE_GROWER_BLOCK = BLOCKS.register(
            "netherite_grower", ()-> new GrowerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK),60,4, 90ё));

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);

        for (DeferredHolder<? extends Block, ? extends Block> block : BLOCKS.getEntries())
            ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));

        ITEMS.register(modEventBus);
    }
}
