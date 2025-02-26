package net.kzeroko.dcmexpansion.registry;

import net.kzeroko.dcmexpansion.DcmExpansion;
import net.kzeroko.dcmexpansion.item.DrinkableItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DcmMiscItems {

    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, DcmExpansion.MOD_ID);
    public static final RegistryObject<Item> WATERBOTTLE_EMPTY = REGISTER.register( "waterbottle_empty", () -> new Item(new Item.Properties().stacksTo(64).tab(DcmExpansion.FOODS_AND_DRINKS)));
    public static final RegistryObject<Item> WATERBOTTLE_FILLED = REGISTER.register( "waterbottle_filled", () -> new DrinkableItem(new Item.Properties().craftRemainder(WATERBOTTLE_EMPTY.get())));

}