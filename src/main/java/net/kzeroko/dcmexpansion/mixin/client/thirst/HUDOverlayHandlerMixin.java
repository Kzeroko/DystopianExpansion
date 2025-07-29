package net.kzeroko.dcmexpansion.mixin.client.thirst;

import dev.ghen.thirst.foundation.gui.appleskin.HUDOverlayHandler;
import net.kzeroko.dcmexpansion.config.DcmExpansionConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(HUDOverlayHandler.class)
public class HUDOverlayHandlerMixin {

    @Inject(method = "renderExhaustion", at = @At("HEAD"), cancellable = true, remap = false)
    private static void onRenderPlayerThirstExhaustion(ForgeGui gui, GuiGraphics mStack, CallbackInfo ci) {
        if (!DcmExpansionConfig.CLIENT_SIDE.showThirst.get()) {
            ci.cancel();
        }
    }

    @Inject(method = "renderThirstOverlay", at = @At("HEAD"), cancellable = true, remap = false)
    private static void onRenderPlayerThirstOverlay(GuiGraphics guiGraphics, CallbackInfo ci) {
        if (!DcmExpansionConfig.CLIENT_SIDE.showThirst.get()) {
            ci.cancel();
        }
    }
}