package net.kzeroko.dcmexpansion.registry.modintegration;

import immersive_aircraft.item.UpgradeItem;
import immersive_aircraft.item.upgrade.AircraftStat;
import immersive_aircraft.item.upgrade.AircraftUpgrade;
import net.kzeroko.dcmexpansion.DcmExpansion;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ImmersiveAircraftItems {

    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, DcmExpansion.MOD_ID);
    public static final RegistryObject<Item> PLASMA_ENGINE = REGISTER.register( "plasma_engine", () ->
            new UpgradeItem(new Item.Properties().stacksTo(8).tab(DcmExpansion.INTEGRATION),
            (new AircraftUpgrade()).set(AircraftStat.STRENGTH, 1.25F).set(AircraftStat.FUEL, -0.15F).set(AircraftStat.ACCELERATION, 0.35F)));
    public static final RegistryObject<Item> ENHANCED_ARMOR = REGISTER.register( "enhanced_armor", () ->
            new UpgradeItem(new Item.Properties().stacksTo(8).tab(DcmExpansion.INTEGRATION),
                    (new AircraftUpgrade()).set(AircraftStat.DURABILITY, 2.0F).set(AircraftStat.FRICTION, -0.2F)));

}