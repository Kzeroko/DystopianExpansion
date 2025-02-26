package net.kzeroko.dcmexpansion.registry;

import ichttt.mods.firstaid.api.item.ItemHealing;
import ichttt.mods.firstaid.common.damagesystem.PartHealer;
import net.kzeroko.dcmexpansion.DcmExpansion;
import net.kzeroko.dcmexpansion.config.DcmExpansionConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.*;

import java.util.function.IntSupplier;

@ObjectHolder("dcmexpansion")
public class DcmFirstaidItems {
    public static void init(IForgeRegistry<Item> ItemReg) {
        DcmExpansionConfig.ServerSide server = DcmExpansionConfig.SERVER_SIDE;

        ItemReg.register(ItemHealing.create((new Item.Properties()).stacksTo(3),
                new ResourceLocation(DcmExpansion.MOD_ID, "bandage_pro"), (stack) -> {
                IntSupplier secondsPerHeal = () -> server.bandage_pro.secondsPerHeal.get() * 20;
                IntSupplier totalHeals = server.bandage_pro.totalHeals::get;
                return new PartHealer(secondsPerHeal, totalHeals, stack);
            }, (stack) -> server.bandage_pro.applyTime.get()
        ));
    }

}