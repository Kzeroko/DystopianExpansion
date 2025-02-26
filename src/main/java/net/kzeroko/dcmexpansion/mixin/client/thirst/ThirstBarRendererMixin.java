/*package net.kzeroko.dcmexpansion.mixin.client.thirst;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.ghen.thirst.foundation.gui.ThirstBarRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.ForgeIngameGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(ThirstBarRenderer.class)
public class ThirstBarRendererMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true, remap = false)
    private static void onRenderPlayerThirst(ForgeIngameGui gui, int screenWidth, int screenHeight, PoseStack poseStack, CallbackInfo ci) {
        //ci.cancel();
    }
}*/