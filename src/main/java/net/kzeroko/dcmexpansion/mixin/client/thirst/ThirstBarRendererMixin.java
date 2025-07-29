package net.kzeroko.dcmexpansion.mixin.client.thirst;

import dev.ghen.thirst.foundation.gui.ThirstBarRenderer;
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
@Mixin(ThirstBarRenderer.class)
public class ThirstBarRendererMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true, remap = false)
    private static void onRenderPlayerThirst(ForgeGui gui, int width, int height, GuiGraphics guiGraphics, CallbackInfo ci) {
        if (!DcmExpansionConfig.CLIENT_SIDE.showThirst.get()) {
            ci.cancel();
        }
    }
}