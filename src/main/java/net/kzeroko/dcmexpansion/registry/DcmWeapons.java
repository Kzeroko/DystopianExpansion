package net.kzeroko.dcmexpansion.registry;

import net.kzeroko.dcmexpansion.DcmExpansion;
import net.kzeroko.dcmexpansion.item.EnergyMeleeItem;
import net.kzeroko.dcmexpansion.item.melee.StunRod;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DcmWeapons {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, DcmExpansion.MOD_ID);
    public static final RegistryObject<EnergyMeleeItem> STUN_ROD = REGISTER.register( "stun_rod",
            () -> new StunRod(
                    20, 20000, 10,
                    18, -2.2F
            ));

    private static Item.Properties makeWeaponProp() {
        return new Item.Properties().stacksTo(1).tab(DcmExpansion.WEAPONS).fireResistant();
    }
}
