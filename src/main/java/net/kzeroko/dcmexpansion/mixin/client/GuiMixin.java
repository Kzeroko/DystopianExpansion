package net.kzeroko.dcmexpansion.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.kzeroko.dcmexpansion.DcmExpansion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
@Mixin(value = Gui.class, priority = 9999)
public class GuiMixin extends GuiComponent {
    @Shadow @Nullable protected Component overlayMessageString;
    @Final
    @Shadow protected Minecraft minecraft;
    @Unique
    private static final ResourceLocation DCM_GUI = new ResourceLocation(DcmExpansion.MOD_ID, "textures/gui/dcm_gui");

    /*
    @Inject(method = "renderPlayerHealth", at = @At("HEAD"), cancellable = true)
    private void onRenderPlayerHealth(PoseStack pPoseStack, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "renderVehicleHealth", at = @At("HEAD"), cancellable = true)
    private void onRenderVehicleHealth(PoseStack poseStack, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "renderJumpMeter", at = @At("HEAD"), cancellable = true)
    private void onRenderJumpMeter(PoseStack pPoseStack, int pX, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "renderExperienceBar", at = @At("HEAD"), cancellable = true)
    private void onRenderExperienceBar(PoseStack pPoseStack, int pX, CallbackInfo ci) {
        ci.cancel();
    }
    */

    /*@Inject(method = "render", at = @At("TAIL"))
    private void renderCustomStuff(PoseStack matrixStack, float pPartialTick, CallbackInfo ci) {

        if (this.minecraft.gameMode != null && this.minecraft.gameMode.canHurtPlayer() && !this.minecraft.options.hideGui) {

            matrixStack.pushPose();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, DCM_GUI);
            blit(matrixStack, 10, 10, 32, 0, 32, 32);

            matrixStack.popPose();
            RenderSystem.disableBlend();
        }

    }*/

    @ModifyConstant(
            method = "setOverlayMessage",
            constant = @Constant(intValue = 60)
    )
    private int modifyOverlayMessageTime(int constant) {
        if (this.overlayMessageString != null && this.overlayMessageString instanceof TranslatableComponent translatable
                && translatable.getKey().startsWith("message.dcmexpansion.weather")) {
            return 160;
        }
        return constant;
    }

}