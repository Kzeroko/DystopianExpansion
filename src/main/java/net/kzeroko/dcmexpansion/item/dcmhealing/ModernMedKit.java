package net.kzeroko.dcmexpansion.item.dcmhealing;

import net.kzeroko.dcmexpansion.config.DcmExpansionConfig;
import net.kzeroko.dcmexpansion.item.KitItem;
import net.kzeroko.dcmexpansion.registry.DcmEffects;
import net.kzeroko.dcmexpansion.util.HealItemUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class ModernMedKit extends KitItem {
    public ModernMedKit() {}

    @Override
    public int getHealPercentage() {
        return DcmExpansionConfig.SERVER_SIDE.modernMedkitHP.get();
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return (getUsagePlayer() != null && getUsagePlayer().hasEffect(DcmEffects.FAST_HEAL.get())) ? 50 : 100;
    }

    @Nonnull
    public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level world, @Nonnull LivingEntity le) {
        if (le instanceof Player player && !world.isClientSide()) {
            DcmExpansionConfig.ServerSide server = DcmExpansionConfig.SERVER_SIDE;
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,
                    server.modernMedkitResistanceSeconds.get() * 20,2,false,false,false));
        }
        return super.finishUsingItem(stack, world, le);
    }

    @Override
    public void onUseTick(@NotNull Level pLevel, @NotNull LivingEntity le, @NotNull ItemStack pStack, int pRemainingUseDuration) {
        Level world = le.getLevel();
        if (le instanceof Player player && !le.getLevel().isClientSide()) {
            HealItemUtil.playMedKitSound(player, world);
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> text, @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, text, pIsAdvanced);
        text.add(new TranslatableComponent("tooltip.dcmexpansion.firstaid_kit", getHealPercentage()));
        text.add(new TranslatableComponent("tooltip.dcmexpansion.modernmedkit"));
    }
}