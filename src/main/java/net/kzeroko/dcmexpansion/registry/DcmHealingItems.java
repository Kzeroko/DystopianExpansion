package net.kzeroko.dcmexpansion.registry;

import net.kzeroko.dcmexpansion.DcmExpansion;
import net.kzeroko.dcmexpansion.item.dcmhealing.*;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.*;

public class DcmHealingItems {

    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, DcmExpansion.MOD_ID);
    public static final RegistryObject<Item> ADRENALINE = REGISTER.register( "adrenaline", Adrenaline::new);
    public static final RegistryObject<Item> ANTIINFECTION_SERUM = REGISTER.register( "antiinfection_serum", AntiInfectionSerum::new);
    public static final RegistryObject<Item> MODERNMEDKIT = REGISTER.register( "modernmedkit", ModernMedKit::new);
    public static final RegistryObject<Item> FIRSTAID_KIT = REGISTER.register( "firstaid_kit", FirstAidKit::new);

}