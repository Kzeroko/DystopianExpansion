package net.kzeroko.dcmexpansion.registry;

import net.kzeroko.dcmexpansion.DcmExpansion;
import net.kzeroko.dcmexpansion.item.EnergyMaterialItem;
import net.kzeroko.dcmexpansion.item.MaterialItem;
import net.kzeroko.dcmexpansion.item.RepairKitItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DcmIntegrationItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DcmExpansion.MOD_ID);

    public static final RegistryObject<Item> SCREEN_COMPONENT = ITEMS.register( "screen_component", ()-> new MaterialItem(64, Rarity.UNCOMMON));
    public static final RegistryObject<Item> LIGHT_EMIT_COMPONENT = ITEMS.register( "light_emit_component", ()-> new MaterialItem(64, Rarity.UNCOMMON));
    public static final RegistryObject<Item> ERIS_VIRUS_JAR = ITEMS.register( "eris_virus_jar", ()-> new MaterialItem(3, Rarity.EPIC));
    public static final RegistryObject<Item> APOCALYPSE_VIRUS_JAR = ITEMS.register( "apocalypse_virus_jar", ()-> new MaterialItem(1, Rarity.EPIC));
    public static final RegistryObject<Item> GOLD_BAR = ITEMS.register( "gold_bar", ()-> new MaterialItem(16, Rarity.EPIC));
    public static final RegistryObject<Item> MOBILE_PHONE_A = ITEMS.register( "mobile_phone_a", ()-> new MaterialItem(6, Rarity.UNCOMMON));
    public static final RegistryObject<Item> FILLED_CANISTER = ITEMS.register( "filled_canister", ()-> new MaterialItem(16, Rarity.UNCOMMON));
    public static final RegistryObject<Item> TELLA_CPU = ITEMS.register( "tella_cpu", ()-> new MaterialItem(1, Rarity.EPIC));
    public static final RegistryObject<Item> AIRCRAFT_CANISTER_T1 = ITEMS.register( "aircraft_canister_t1", ()-> new MaterialItem(16, Rarity.RARE));
    public static final RegistryObject<Item> AIRCRAFT_CANISTER_T2 = ITEMS.register( "aircraft_canister_t2", ()-> new MaterialItem(16, Rarity.RARE));
    public static final RegistryObject<Item> AIRCRAFT_CANISTER_T3 = ITEMS.register( "aircraft_canister_t3", ()-> new MaterialItem(16, Rarity.RARE));
    public static final RegistryObject<Item> REPAIRKIT_T1 = ITEMS.register( "repairkit_t1", ()-> new RepairKitItem(0.15F));
    public static final RegistryObject<Item> REPAIRKIT_T2 = ITEMS.register( "repairkit_t2", ()-> new RepairKitItem(0.45F));
    public static final RegistryObject<Item> REPAIRKIT_T3 = ITEMS.register( "repairkit_t3", ()-> new RepairKitItem(1.0F));
    public static final RegistryObject<Item> COMPRESSED_ENERGY_TABLET = ITEMS.register( "compressed_energy_tablet", ()-> new EnergyMaterialItem(20, 1500000, Rarity.RARE));

    /*
    @SubscribeEvent
    public static void onItemRegister(RegisterEvent event) {
        if (event.getRegistryKey().equals(ForgeRegistries.ITEMS.getRegistryKey())) {

        }
    }
    */
}