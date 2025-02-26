package net.kzeroko.dcmexpansion.registry.modintegration;

import net.kzeroko.dcmexpansion.DcmExpansion;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ImmersiveAircraftItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DcmExpansion.MOD_ID);

    public static final RegistryObject<Item> PLASMA_ENGINE = ITEMS.register( "plasma_engine", () ->
            new Item(new Item.Properties().stacksTo(8)));
    public static final RegistryObject<Item> ENHANCED_ARMOR = ITEMS.register( "enhanced_armor", () ->
            new Item(new Item.Properties().stacksTo(8)));
}