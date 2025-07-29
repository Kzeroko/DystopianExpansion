package net.kzeroko.dcmexpansion.mixin.client.firstaid;

import com.mojang.blaze3d.vertex.PoseStack;
import ichttt.mods.firstaid.api.damagesystem.AbstractPlayerDamageModel;
import ichttt.mods.firstaid.client.util.PlayerModelRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(value = PlayerModelRenderer.class, remap = false)
public abstract class PlayerModelRendererMixin {

    @Inject(
            method = "renderPlayerHealth",
            at = @At(
                    value = "INVOKE",
                    shift = At.Shift.BEFORE,
                    target = "Lichttt/mods/firstaid/client/util/PlayerModelRenderer;drawPart(Lnet/minecraft/client/gui/GuiGraphics;ZLichttt/mods/firstaid/api/damagesystem/AbstractDamageablePart;IIII)V",
                    ordinal = 0
            )
    )
    private static void scaleModel(PoseStack stack, AbstractPlayerDamageModel damageModel, boolean fourColors, GuiGraphics guiGraphics, boolean flashState, float alpha, float partialTicks, CallbackInfo ci) {
        stack.scale(0.75F, 0.75F, 0.75F);
    }
}
