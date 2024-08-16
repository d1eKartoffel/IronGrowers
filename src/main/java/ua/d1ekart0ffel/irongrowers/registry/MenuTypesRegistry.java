package ua.d1ekart0ffel.irongrowers.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;
import ua.d1ekart0ffel.irongrowers.IronGrowers;
import ua.d1ekart0ffel.irongrowers.screen.GrowerBlockMenu;

import java.util.function.Supplier;

public class MenuTypesRegistry {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BuiltInRegistries.MENU, IronGrowers.MODID);

    public static final Supplier<MenuType<GrowerBlockMenu>> GROWER_BLOCK_MENU = MENU_TYPES
            .register("grower_block_menu", () -> IMenuTypeExtension.create(GrowerBlockMenu::new));

    public static void register(IEventBus modEventBus) {
        MENU_TYPES.register(modEventBus);
    }
}