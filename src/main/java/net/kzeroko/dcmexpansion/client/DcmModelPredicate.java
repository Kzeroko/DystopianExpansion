package net.kzeroko.dcmexpansion.client;

import net.kzeroko.dcmexpansion.registry.DcmWeapons;
import net.kzeroko.dcmexpansion.util.EnergyUtil;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class DcmModelPredicate {
    public static void register() {
        ItemProperties.register(DcmWeapons.STUN_ROD.get(), new ResourceLocation("uncharged"), (stack, world, entity, seed) -> {
            //
            return EnergyUtil.isStackCharged(stack) ? 0.0F : 1.0F;
        });
    }
}
