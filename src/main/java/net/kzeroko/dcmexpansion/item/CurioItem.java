package net.kzeroko.dcmexpansion.item;

import net.kzeroko.dcmexpansion.DcmExpansion;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import javax.annotation.Nullable;

public class CurioItem extends Item implements ICurioItem {

    public CurioItem(int durability) {
        super(new Properties().tab(DcmExpansion.INTEGRATION).stacksTo(1).durability(durability));
    }

    public boolean isEquippedBy(@Nullable LivingEntity entity) {
        return entity != null && CuriosApi.getCuriosHelper().findEquippedCurio(this, entity).isPresent();
    }

    @Override
    public @NotNull Rarity getRarity(@NotNull ItemStack stack) {
        return Rarity.RARE;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    protected void damageStack(SlotContext slotContext, ItemStack stack, int damage) {
        stack.hurtAndBreak(damage, slotContext.entity(), entity ->
                CuriosApi.getCuriosHelper().onBrokenCurio(slotContext)
        );
    }

    public void damageAllEquipped(LivingEntity entity, int damage) {
        if (entity instanceof Player player && player.isCreative()) return;

        CuriosApi.getCuriosHelper().getCuriosHandler(entity).ifPresent(curiosHandler ->
                curiosHandler.getCurios().forEach((identifier, stacksHandler) -> {
                    IDynamicStackHandler stacks = stacksHandler.getStacks();
                    for (int slot = 0; slot < stacks.getSlots(); slot++) {
                        ItemStack stack = stacks.getStackInSlot(slot);
                        if (!stack.isEmpty() && stack.getItem() == this) {
                            SlotContext slotContext = new SlotContext(identifier, entity, slot, false, true);
                            damageStack(slotContext, stack, damage);
                        }
                    }
                })
        );
    }

}