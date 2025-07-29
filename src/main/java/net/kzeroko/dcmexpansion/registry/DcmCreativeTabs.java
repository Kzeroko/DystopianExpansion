package net.kzeroko.dcmexpansion.registry;

import net.kzeroko.dcmexpansion.DcmExpansion;
import net.kzeroko.dcmexpansion.registry.modintegration.*;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DcmCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DcmExpansion.MOD_ID);

    public static final RegistryObject<CreativeModeTab> HEALING_TAB = CREATIVE_MODE_TABS.register("healingItems", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.dcmexpansion.healingItems").withStyle(ChatFormatting.AQUA))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> DcmHealingItems.FIRSTAID_KIT.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.acceptAll(regEntries(DcmHealingItems.ITEMS));
            }).build());

    public static final RegistryObject<CreativeModeTab> INTEGRATION_TAB = CREATIVE_MODE_TABS.register("modIntegrationItems", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.dcmexpansion.modIntegrationItems").withStyle(ChatFormatting.AQUA))
            .withTabsBefore(HEALING_TAB.getId())
            .icon(() -> DcmIntegrationItems.REPAIRKIT_T3.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.acceptAll(regEntries(DcmIntegrationItems.ITEMS, ColdSweatItems.ITEMS, ImmersiveAircraftItems.ITEMS, PneumaticCraftItems.ITEMS, TaczItems.ITEMS, WeatherItems.ITEMS));
            }).build());

    public static final RegistryObject<CreativeModeTab> FOODNDRINKS_TAB = CREATIVE_MODE_TABS.register("foodsAndDrinks", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.dcmexpansion.foodsAndDrinks").withStyle(ChatFormatting.AQUA))
            .withTabsBefore(INTEGRATION_TAB.getId())
            .icon(() -> DcmFoodNDrinksItems.WATERBOTTLE_FILLED.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.acceptAll(regEntries(DcmFoodNDrinksItems.ITEMS));
            }).build());

    public static final RegistryObject<CreativeModeTab> WEAPONS_TAB = CREATIVE_MODE_TABS.register("weapons", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.dcmexpansion.weapons").withStyle(ChatFormatting.AQUA))
            .withTabsBefore(FOODNDRINKS_TAB.getId())
            .icon(() -> DcmWeapons.STUN_ROD.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.acceptAll(regEntries(DcmWeapons.ITEMS));
            }).build());

    @SafeVarargs
    private static Collection<ItemStack> regEntries(DeferredRegister<Item>... deferredRegs) {
        List<ItemStack> combinedEntries = new ArrayList<>();
        for (DeferredRegister<Item> deferredReg : deferredRegs) {
            combinedEntries.addAll(deferredReg.getEntries().stream()
                    .map(entry -> entry.get().getDefaultInstance())
                    .toList());
        }
        return combinedEntries;
    }
}
