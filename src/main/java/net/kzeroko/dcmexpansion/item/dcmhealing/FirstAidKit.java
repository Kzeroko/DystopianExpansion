package net.kzeroko.dcmexpansion.item.dcmhealing;

import net.kzeroko.dcmexpansion.config.DcmExpansionConfig;
import net.kzeroko.dcmexpansion.item.KitItem;
import net.kzeroko.dcmexpansion.registry.DcmEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FirstAidKit extends KitItem {
    public FirstAidKit() {}

    @Override
    public int getHealPercentage() {
        return DcmExpansionConfig.SERVER_SIDE.firstAidKitHP.get();
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return (getUsagePlayer() != null && getUsagePlayer().hasEffect(DcmEffects.FAST_HEAL.get())) ? 50 : 100;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> text, @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, text, pIsAdvanced);
        text.add(Component.translatable("tooltip.dcmexpansion.firstaid_kit", getHealPercentage()));
    }
}