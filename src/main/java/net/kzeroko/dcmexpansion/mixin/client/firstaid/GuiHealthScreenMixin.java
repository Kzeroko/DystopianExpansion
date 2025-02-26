package net.kzeroko.dcmexpansion.mixin.client.firstaid;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import ichttt.mods.firstaid.api.healing.ItemHealing;
import ichttt.mods.firstaid.client.gui.GuiHealthScreen;
import net.kzeroko.dcmexpansion.registry.DcmEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@OnlyIn(Dist.CLIENT)
@Mixin(value = GuiHealthScreen.class, priority = 1001, remap = false)
public abstract class ItemHealingMixin {

    @WrapOperation(
            method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Lichttt/mods/firstaid/api/healing/ItemHealing;getApplyTime(Lnet/minecraft/world/item/ItemStack;)I"
            )
    )
    private int modifyFirstAidPartHealingTime(ItemHealing instance, ItemStack itemStack, Operation<Integer> original) {

        int originalTime = instance.getApplyTime(itemStack);

        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;

        if (player != null && player.hasEffect(DcmEffects.FAST_HEAL.get())) {
            long modifiedTime = (long) originalTime / 2L;
            return (int) Math.max(0L, modifiedTime);
        }

        return originalTime;
    }
}
