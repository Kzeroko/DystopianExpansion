package net.kzeroko.dcmexpansion.registry.modintegration;

import net.kzeroko.dcmexpansion.DcmExpansion;
import net.kzeroko.dcmexpansion.item.EnergyCurioItem;
import net.kzeroko.dcmexpansion.item.coldsweat.TemperatureUnit;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ColdSweatItems {

    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, DcmExpansion.MOD_ID);
    public static final RegistryObject<EnergyCurioItem> TEMPERATURE_UNIT_NORMAL =
            REGISTER.register( "temperature_unit_normal", ()-> new TemperatureUnit(10, 6000, 5, 4));
    public static final RegistryObject<EnergyCurioItem> TEMPERATURE_UNIT_ELITE =
            REGISTER.register( "temperature_unit_elite", ()-> new TemperatureUnit(20, 9000, 3, 9));
}