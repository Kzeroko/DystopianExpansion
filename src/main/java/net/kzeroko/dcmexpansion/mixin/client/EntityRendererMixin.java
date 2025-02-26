package net.kzeroko.dcmexpansion.mixin.client;

import net.kzeroko.dcmexpansion.config.DcmExpansionConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.entity.EntityRenderer;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    @Inject(method = "renderNameTag", cancellable = true, at = @At(value = "HEAD"))
    private void cancelRenderNameTag(CallbackInfo ci) {
        if (!DcmExpansionConfig.CLIENT_SIDE.showPlayerNameplate.get()) {
            ci.cancel();
        }
    }

}
