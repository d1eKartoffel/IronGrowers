package ua.d1ekart0ffel.irongrowers.registry;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import ua.d1ekart0ffel.irongrowers.IronGrowers;
import ua.d1ekart0ffel.irongrowers.screen.GrowerBlockScreen;

@EventBusSubscriber(modid = IronGrowers.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RemoteRegistry {
    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(MenuTypesRegistry.GROWER_BLOCK_MENU.get(), GrowerBlockScreen::new);
    }
}
