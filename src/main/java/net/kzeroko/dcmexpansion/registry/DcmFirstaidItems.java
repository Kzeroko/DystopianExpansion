package net.kzeroko.dcmexpansion.registry;

import ichttt.mods.firstaid.api.healing.ItemHealing;
import ichttt.mods.firstaid.common.damagesystem.PartHealer;
import net.kzeroko.dcmexpansion.DcmExpansion;
import net.kzeroko.dcmexpansion.config.DcmExpansionConfig;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.IntSupplier;

public class DcmFirstaidItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DcmExpansion.MOD_ID);

    public static final RegistryObject<Item> BANDAGE_PRO = ITEMS.register("bandage_pro", () ->
            ItemHealing.create((new Item.Properties()).stacksTo(3), (stack) -> {
                IntSupplier secondsPerHeal = () -> DcmExpansionConfig.SERVER_SIDE.bandage_pro.secondsPerHeal.get() * 20;
                IntSupplier totalHeals = DcmExpansionConfig.SERVER_SIDE.bandage_pro.totalHeals::get;
                return new PartHealer(secondsPerHeal, totalHeals, stack);
            }, (stack) -> DcmExpansionConfig.SERVER_SIDE.bandage_pro.applyTime.get()));

}