package net.kzeroko.dcmexpansion.item.pncr;

import me.desht.pneumaticcraft.common.core.ModItems;
import net.kzeroko.dcmexpansion.DcmExpansion;
import net.kzeroko.dcmexpansion.config.DcmExpansionConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CompressedBattery extends Item {
    public CompressedBattery() {
        super((new Item.Properties()).tab(DcmExpansion.INTEGRATION).stacksTo(1));
    }


    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
        if (player.isCrouching()) {
            ItemStack offhandItem = player.getOffhandItem();
            ItemStack mainhandItem = player.getMainHandItem();


            if (offhandItem.is(ModItems.AMADRON_TABLET.get()) & mainhandItem.is(this)) {

                if (offhandItem.hasTag()) {

                    CompoundTag uses = mainhandItem.getOrCreateTag();
                    int cUses = uses.getInt("cb_uses");
                    if(!mainhandItem.hasTag()) {
                        uses.putInt("cb_uses", 4);
                        mainhandItem.setTag(uses);
                        addAirToTablet(offhandItem);
                        player.getCooldowns().addCooldown(this, 20 * 2);
                    } else {
                        if (player.getCooldowns().isOnCooldown(this)) {
                            return InteractionResultHolder.fail(heldStack);
                        } else if (cUses <= 0) {
                            player.sendMessage(new TranslatableComponent("message.dcmexpansion.cb_outofuse"), player.getUUID());
                            return InteractionResultHolder.fail(heldStack);
                        } else {
                            cUses--;
                            uses.putInt("cb_uses", cUses);
                            mainhandItem.setTag(uses);
                            addAirToTablet(offhandItem);
                            player.getCooldowns().addCooldown(this, 20 * 2);
                        }
                    }
                } else {
                    return InteractionResultHolder.fail(heldStack);
                }
                return InteractionResultHolder.success(heldStack);
            }
        }
        return InteractionResultHolder.pass(heldStack);
    }

    private static void addAirToTablet(ItemStack stack) {
        DcmExpansionConfig.ServerSide server = DcmExpansionConfig.SERVER_SIDE;
        CompoundTag pncr_air = stack.getTag();
        if (pncr_air != null) {
            int airValue = pncr_air.getInt("pneumaticcraft:air");
            pncr_air.putInt("pneumaticcraft:air", airValue + server.compressedBatteryCharge.get());
            stack.setTag(pncr_air);
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> text, @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, text, pIsAdvanced);
        text.add(new TranslatableComponent("tooltip.dcmexpansion.compressed_battery"));
        if (pStack.getTag() != null) {
            CompoundTag uses = pStack.getTag();
            int cUses = uses.getInt("cb_uses");
            text.add(new TranslatableComponent("tooltip.dcmexpansion.compressed_battery_amount", cUses));
        } else {
            text.add(new TranslatableComponent("tooltip.dcmexpansion.compressed_battery_amount_na"));
        }

    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack pStack) {
        return false;
    }

}