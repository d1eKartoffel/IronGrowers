package ua.d1ekart0ffel.irongrowers;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;
import ua.d1ekart0ffel.irongrowers.registry.BlockEntityRegistry;
import ua.d1ekart0ffel.irongrowers.registry.BlockRegistry;
import ua.d1ekart0ffel.irongrowers.registry.CreativeTabRegistry;

@Mod(IronGrowers.MODID)
public class IronGrowers {

    public static final String MODID = "irongrowers";
    private static final Logger LOGGER = LogUtils.getLogger();

    public IronGrowers(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(CommonSetup::init);
        modContainer.registerConfig(ModConfig.Type.STARTUP, Config.COMMON_CONFIG);

        BlockRegistry.register(modEventBus);
        BlockEntityRegistry.register(modEventBus);
        CreativeTabRegistry.register(modEventBus);
    }
}
