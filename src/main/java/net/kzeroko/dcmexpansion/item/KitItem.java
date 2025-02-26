package net.kzeroko.dcmexpansion.item;

import ichttt.mods.firstaid.api.damagesystem.AbstractPlayerDamageModel;
import ichttt.mods.firstaid.common.util.CommonUtils;
import net.kzeroko.dcmexpansion.DcmExpansion;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class KitItem extends Item {
    private Player usagePlayer;

    public KitItem() {
        super((new Properties()).tab(DcmExpansion.HEALING).stacksTo(4));
    }

    public abstract int getHealPercentage();

    @Nonnull
    public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level world, @Nonnull LivingEntity le) {

        if (le instanceof Player player && !world.isClientSide()) {

            AbstractPlayerDamageModel damageModel = CommonUtils.getDamageModel(player);
            //int healValue = Ints.checkedCast(Math.round(damageModel.getCurrentMaxHealth() * getHealPercentage()));
            //HealthDistribution.manageHealth(healValue, damageModel, player, true, false);
            CommonUtils.healPlayerByPercentage((double) getHealPercentage() / 100, damageModel, player);

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
        setUsagePlayer(player);
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }

    public int getUseDuration(@NotNull ItemStack stack) {
        return 120;
    }

    public void setUsagePlayer(Player player) {
        this.usagePlayer = player;
    }

    public @Nullable Player getUsagePlayer() {
        return usagePlayer;
    }

}