package net.kzeroko.dcmexpansion.registry;

import net.kzeroko.dcmexpansion.DcmExpansion;
import net.kzeroko.dcmexpansion.item.DrinkableItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DcmFoodNDrinksItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DcmExpansion.MOD_ID);

    public static final RegistryObject<Item> WATERBOTTLE_EMPTY = ITEMS.register( "waterbottle_empty", () -> new Item(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> WATERBOTTLE_FILLED = ITEMS.register( "waterbottle_filled", () -> new DrinkableItem(new Item.Properties().craftRemainder(WATERBOTTLE_EMPTY.get())));

}