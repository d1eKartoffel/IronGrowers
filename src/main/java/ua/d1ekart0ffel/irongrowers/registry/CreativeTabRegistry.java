package ua.d1ekart0ffel.irongrowers.registry;

import com.jcraft.jorbis.Block;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import ua.d1ekart0ffel.irongrowers.IronGrowers;

public class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, IronGrowers.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> IRON_GROWERS_TAB = CREATIVE_TABS.register("irongrowers",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.irongrowers"))
                    .icon(() -> new ItemStack(BlockRegistry.IRON_GROWER_BLOCK.get()))
                    .build());

    public static void register(IEventBus bus) {
        CREATIVE_TABS.register(bus);

        bus.addListener(CreativeTabRegistry::fillCreativeTabs);
    }

    private static void fillCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == IRON_GROWERS_TAB.get()) {
            event.accept(BlockRegistry.COPPER_GROWER_BLOCK.get());
            event.accept(BlockRegistry.IRON_GROWER_BLOCK.get());
            event.accept(BlockRegistry.GOLD_GROWER_BLOCK.get());
            event.accept(BlockRegistry.DIAMOND_GROWER_BLOCK.get());
            event.accept(BlockRegistry.NETHERITE_GROWER_BLOCK.get());
        }
    }
}
