package net.kzeroko.dcmexpansion.item.dcmhealing;

import net.kzeroko.dcmexpansion.DcmExpansion;
import net.kzeroko.dcmexpansion.config.DcmExpansionConfig;
import net.kzeroko.dcmexpansion.registry.DcmEffects;
import net.kzeroko.dcmexpansion.registry.DcmSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.smileycorp.hordes.common.infection.HordesInfection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class AntiInfectionSerum extends Item {
    public AntiInfectionSerum() {
        super((new Properties()).tab(DcmExpansion.HEALING).stacksTo(8));
    }

    @Nonnull
    public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level world, @Nonnull LivingEntity le) {

        if (le instanceof Player && !world.isClientSide()) {
            DcmExpansionConfig.ServerSide server = DcmExpansionConfig.SERVER_SIDE;

            le.addEffect(new MobEffectInstance(MobEffects.HUNGER,
                    server.serumHungerSeconds.get() * 20,1,false,false,false));
            le.addEffect(new MobEffectInstance(DcmEffects.ANTI_INFECTION.get(),
                    server.serumAntiInfectionSeconds.get() * 20,0,false,false,true));

            if (le.hasEffect(HordesInfection.INFECTED.get()) && server.serumAntiInfectionCure.get()) {
                le.removeEffect(HordesInfection.INFECTED.get());
            }

            world.playSound(null, le.getX(), le.getY(), le.getZ(),
                    DcmSounds.ADRENALINE_INJECT.get(), SoundSource.PLAYERS, 0.8F, 1.0F);

            stack.shrink(1);
        }

        return stack;
    }

    @Nonnull
    public UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.NONE;
    }

    @Nonnull
    public InteractionResultHolder<ItemStack> use(@NotNull Level world, Player player, @Nonnull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
                DcmSounds.ADRENALINE_OPEN.get(), SoundSource.PLAYERS, 0.85F, 1.0F);
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }

    public int getUseDuration(@NotNull ItemStack stack) {
        return 20;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> text, @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, text, pIsAdvanced);
        text.add(new TranslatableComponent("tooltip.dcmexpansion.antiinfection_serum"));
    }
}