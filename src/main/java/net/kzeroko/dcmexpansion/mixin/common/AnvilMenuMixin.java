package net.kzeroko.dcmexpansion.mixin.common;

import net.kzeroko.dcmexpansion.internal.DcmTags;
import net.kzeroko.dcmexpansion.item.RepairKitItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {
    @Shadow @Final private DataSlot cost;
    @Shadow public int repairItemCountCost;

    public AnvilMenuMixin(@Nullable MenuType<?> pType, int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
        super(pType, pContainerId, pPlayerInventory, pAccess);
    }

    @Inject(method = "createResult", at = @At(value = "HEAD"), cancellable = true)
    private void updateRepairKitResult(CallbackInfo ci) {
        ItemStack leftStack = this.inputSlots.getItem(0).copy();
        ItemStack rightStack = this.inputSlots.getItem(1).copy();

        if (leftStack.isDamageableItem() && leftStack.isDamaged() && rightStack.getItem() instanceof RepairKitItem repairKit) {

            if (leftStack.is(DcmTags.REPAIRKIT_BLACKLIST)) return;

            float fixAmount = repairKit.getFixAmount();
            int damage = leftStack.getDamageValue();
            int durability = leftStack.getMaxDamage();

            int perKitRepair = (int) (durability * fixAmount);
            int kitsNeeded = (int) Math.ceil((double) damage / perKitRepair);

            int kitUsed = Math.min(kitsNeeded, rightStack.getCount());

            int totalRepair = perKitRepair * kitUsed;

            int newDamage = damage - totalRepair;
            if (newDamage < 0) newDamage = 0;

            leftStack.setDamageValue(newDamage);

            int costV = (int) Mth.clamp(fixAmount * kitUsed * 5F, 1F, 5F * kitUsed);

            this.cost.set(costV);
            this.repairItemCountCost = kitUsed;
            this.resultSlots.setItem(0, leftStack);
            this.broadcastChanges();

            ci.cancel();
        }
    }

    @Inject(method = "onTake", at = @At("HEAD"), cancellable = true)
    private void onTakeRepairKitFixing(Player player, ItemStack stack, CallbackInfo ci) {
        ItemStack repairStack = this.inputSlots.getItem(1);
        if (repairStack.getItem() instanceof RepairKitItem) {
            if (this.repairItemCountCost > 0) {
                if (!repairStack.isEmpty() && repairStack.getCount() > this.repairItemCountCost) {
                    repairStack.shrink(this.repairItemCountCost);
                    this.inputSlots.setItem(1, repairStack);
                } else {
                    this.inputSlots.setItem(1, ItemStack.EMPTY);
                }
            }

            this.inputSlots.setItem(0, ItemStack.EMPTY);

            float breakChance = net.minecraftforge.common.ForgeHooks.onAnvilRepair(player, stack, this.inputSlots.getItem(0), this.inputSlots.getItem(1));

            this.access.execute((level, blockPos) -> {
                BlockState blockstate = level.getBlockState(blockPos);
                if (!player.getAbilities().instabuild && blockstate.is(BlockTags.ANVIL) && player.getRandom().nextFloat() < breakChance) {
                    BlockState anvBlockState = AnvilBlock.damage(blockstate);
                    if (anvBlockState == null) {
                        level.removeBlock(blockPos, false);
                        level.levelEvent(1029, blockPos, 0);
                    } else {
                        level.setBlock(blockPos, anvBlockState, 2);
                        level.levelEvent(1030, blockPos, 0);
                    }
                } else {
                    level.levelEvent(1030, blockPos, 0);
                }

            });

            ci.cancel();
        }
    }


}
