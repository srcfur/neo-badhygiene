package com.srcfur.badhygiene.client;

import com.srcfur.badhygiene.BadHygiene;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@Mod(BadHygiene.MODID)
public class HygieneScreens {
    public HygieneScreens(IEventBus modbus){
        MENUS.register(modbus);
        modbus.register(this);
    }
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, BadHygiene.MODID);
    public static final Supplier<MenuType<WashingMachineMenu>> WASHING_MACHINE_MENU = MENUS.register("washing_machine",
            ()->new MenuType(WashingMachineMenu::new, FeatureFlags.DEFAULT_FLAGS));
    @SubscribeEvent // on the mod event bus only on the physical client
    public void registerScreens(RegisterMenuScreensEvent event) {
        event.register(WASHING_MACHINE_MENU.get(), WashingMachineScreen::new);
    }
}
