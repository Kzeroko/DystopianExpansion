package net.kzeroko.dcmexpansion.registry.modintegration;

import net.kzeroko.dcmexpansion.DcmExpansion;
import net.kzeroko.dcmexpansion.item.weather.WeatherTesterItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WeatherItems {

    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, DcmExpansion.MOD_ID);
    public static final RegistryObject<Item> WEATHER_TESTER_NORMAL =
            REGISTER.register( "weather_tester_normal",
                    ()-> new WeatherTesterItem(5, 2000, 60, 500));
    public static final RegistryObject<Item> WEATHER_TESTER_ELITE =
            REGISTER.register( "weather_tester_elite",
                    ()-> new WeatherTesterItem(15, 4000, 20, 1500));

}