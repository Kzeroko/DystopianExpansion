package net.kzeroko.dcmexpansion.mixin.common;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemFrame.class, priority = 1005)
public class ItemFrameMixin {
    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void hurtFilter(DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean> cir) {

        // Vanilla does the same thing, we need more logic
        if (pSource.getEntity() instanceof Player player && player.isCreative()) return;

        ItemFrame itemFrame = (ItemFrame) (Object) this;

        if (itemFrame.isInvulnerable()) { // && (pSource.isExplosion() || pSource.isProjectile() || pSource.getEntity() != null)
            cir.setReturnValue(false);
            cir.cancel();
        }

    }

    @Inject(method = "kill", at = @At("HEAD"), cancellable = true)
    private void killFilter(CallbackInfo ci) {
        ItemFrame itemFrame = (ItemFrame) (Object) this;

        if (itemFrame.isInvulnerable()) {
            ci.cancel();
        }
    }

    @Inject(method = "removeFramedMap", at = @At("HEAD"), cancellable = true)
    private void removeFilter(CallbackInfo ci) {
        ItemFrame itemFrame = (ItemFrame) (Object) this;

        if (itemFrame.isInvulnerable()) {
            ci.cancel();
        }
    }

}
