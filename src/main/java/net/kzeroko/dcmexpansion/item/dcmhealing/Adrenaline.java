package net.kzeroko.dcmexpansion.item.dcmhealing;

import ichttt.mods.firstaid.api.damagesystem.AbstractPlayerDamageModel;
import ichttt.mods.firstaid.common.util.CommonUtils;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class Adrenaline extends Item {
    public Adrenaline() {
        super((new Item.Properties()).tab(DcmExpansion.HEALING).stacksTo(8));
    }

    @Nonnull
    public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level world, @Nonnull LivingEntity le) {
        if (CommonUtils.hasDamageModel(le)) {
            AbstractPlayerDamageModel damageModel = CommonUtils.getDamageModel((Player)le);
            Objects.requireNonNull(damageModel).applyMorphine((Player)le);
        }

        if (le instanceof Player && !world.isClientSide()) {
            float healthP = le.getHealth() / le.getMaxHealth();
            DcmExpansionConfig.ServerSide server = DcmExpansionConfig.SERVER_SIDE;
            if (healthP < 0.5) {
                le.addEffect(new MobEffectInstance(MobEffects.ABSORPTION,
                        server.adrenalineAbsorptionSeconds.get() * 20,5,false,false,false));
            }
            else if (healthP <0.7) {
                le.addEffect(new MobEffectInstance(MobEffects.ABSORPTION,
                        server.adrenalineAbsorptionSeconds.get() * 20,3,false,false,false));
            }
            else {
                le.addEffect(new MobEffectInstance(MobEffects.ABSORPTION,
                        server.adrenalineAbsorptionSeconds.get() * 20,1,false,false,false));
            }

            if (le.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                le.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
            }


            le.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,
                    server.adrenalineSpeedSeconds.get() * 20,2,false,false,false));
            le.addEffect(new MobEffectInstance(DcmEffects.FAST_HEAL.get(),
                    server.adrenalineSpeedSeconds.get() * 20,0,false,false,true));

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
        //world.playSound(null, player.getX(), player.getY(), player.getZ(),
                //DcmSounds.ADRENALINE_OPEN.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }

    public int getUseDuration(@NotNull ItemStack stack) {
        return 20;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> text, @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, text, pIsAdvanced);
        text.add(new TranslatableComponent("tooltip.dcmexpansion.adrenaline"));
        text.add(new TranslatableComponent("firstaid.tooltip.morphine", "3:30-4:30"));
    }
}