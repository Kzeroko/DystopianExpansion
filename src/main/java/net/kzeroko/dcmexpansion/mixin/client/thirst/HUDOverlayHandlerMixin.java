/*package net.kzeroko.dcmexpansion.mixin.client.thirst;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.ghen.thirst.foundation.gui.appleskin.HUDOverlayHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.ForgeIngameGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(HUDOverlayHandler.class)
public class HUDOverlayHandlerMixin {

    @Inject(method = "renderExhaustion", at = @At("HEAD"), cancellable = true, remap = false)
    private static void onRenderPlayerThirstExhaustion(ForgeIngameGui gui, PoseStack mStack, CallbackInfo ci) {
        //ci.cancel();
    }

    @Inject(method = "renderThirstOverlay", at = @At("HEAD"), cancellable = true, remap = false)
    private static void onRenderPlayerThirstOverlay(PoseStack mStack, CallbackInfo ci) {
        //ci.cancel();
    }
}*/