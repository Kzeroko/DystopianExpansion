package net.kzeroko.dcmexpansion.registry;

import net.kzeroko.dcmexpansion.DcmExpansion;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class DcmCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DcmExpansion.MOD_ID);

    public static final RegistryObject<CreativeModeTab> INTEGRATION_TAB = CREATIVE_MODE_TABS.register("integration", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> DcmIntegrationItems.REPAIRKIT_T3.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(DcmIntegrationItems.REPAIRKIT_T3.get());
            }).build());
}
