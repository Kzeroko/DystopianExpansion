package net.kzeroko.dcmexpansion.registry;

import net.kzeroko.dcmexpansion.DcmExpansion;
import net.kzeroko.dcmexpansion.item.dcmhealing.Adrenaline;
import net.kzeroko.dcmexpansion.item.dcmhealing.AntiInfectionSerum;
import net.kzeroko.dcmexpansion.item.dcmhealing.FirstAidKit;
import net.kzeroko.dcmexpansion.item.dcmhealing.ModernMedKit;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DcmHealingItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DcmExpansion.MOD_ID);

    public static final RegistryObject<Item> ADRENALINE = ITEMS.register( "adrenaline", Adrenaline::new);
    public static final RegistryObject<Item> ANTIINFECTION_SERUM = ITEMS.register( "antiinfection_serum", AntiInfectionSerum::new);
    public static final RegistryObject<Item> MODERNMEDKIT = ITEMS.register( "modernmedkit", ModernMedKit::new);
    public static final RegistryObject<Item> FIRSTAID_KIT = ITEMS.register( "firstaid_kit", FirstAidKit::new);

}