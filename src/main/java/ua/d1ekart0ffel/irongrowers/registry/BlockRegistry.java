package ua.d1ekart0ffel.irongrowers.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLConfig;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import ua.d1ekart0ffel.irongrowers.Config;
import ua.d1ekart0ffel.irongrowers.IronGrowers;
import ua.d1ekart0ffel.irongrowers.blocks.GrowerBlock;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, IronGrowers.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, IronGrowers.MODID);

    public static final DeferredHolder<Block, GrowerBlock> COPPER_GROWER_BLOCK = BLOCKS.register(
            "copper_grower", ()-> new GrowerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK), Config.GROWTH_INTERVAL_COPPER.get(), Config.RADIUS_COPPER.get(), Config.GROWTH_CHANCE_COPPER.get()));

    public static final DeferredHolder<Block, GrowerBlock> IRON_GROWER_BLOCK = BLOCKS.register(
            "iron_grower", ()-> new GrowerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), Config.GROWTH_INTERVAL_IRON.get(), Config.RADIUS_IRON.get(),Config.GROWTH_CHANCE_IRON.get()));

    public static final DeferredHolder<Block, GrowerBlock> GOLD_GROWER_BLOCK = BLOCKS.register(
            "gold_grower", ()-> new GrowerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK), Config.GROWTH_INTERVAL_GOLD.get(), Config.RADIUS_GOLD.get(), Config.GROWTH_CHANCE_GOLD.get()));

    public static final DeferredHolder<Block, GrowerBlock> DIAMOND_GROWER_BLOCK = BLOCKS.register(
            "diamond_grower", ()-> new GrowerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK), Config.GROWTH_INTERVAL_DIAMOND.get(),Config.RADIUS_DIAMOND.get(),Config.GROWTH_CHANCE_DIAMOND.get()));

    public static final DeferredHolder<Block, GrowerBlock> NETHERITE_GROWER_BLOCK = BLOCKS.register(
            "netherite_grower", ()-> new GrowerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK), Config.GROWTH_INTERVAL_NETHERITE.get(), Config.RADIUS_NETHERITE.get(), Config.GROWTH_CHANCE_NETHERITE.get()));

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);

        for (DeferredHolder<? extends Block, ? extends Block> block : BLOCKS.getEntries())
            ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));

        ITEMS.register(modEventBus);
    }
}
