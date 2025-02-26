package net.kzeroko.dcmexpansion.mixin.common;

import net.kzeroko.dcmexpansion.internal.DcmDamageSources;
import net.kzeroko.dcmexpansion.registry.DcmEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.smileycorp.hordes.common.infection.HordesInfection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(at = @At("HEAD"), method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z", cancellable = true)
    private void immuneEffect(MobEffectInstance effectInstance, Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if(entity instanceof Player player) {
            if(effectInstance.getEffect() == HordesInfection.INFECTED.get() && player.hasEffect(DcmEffects.ANTI_INFECTION.get())) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void onTick(CallbackInfo info) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity.hasEffect(HordesInfection.INFECTED.get()) && entity.hasEffect(DcmEffects.ANTI_INFECTION.get())){
            entity.removeEffect(HordesInfection.INFECTED.get());
        }
    }

    @Inject(at = @At("HEAD"), method = "hurt", cancellable = true)
    private void onHurt(DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (pSource == DcmDamageSources.HAZARD_GAS && entity.hasEffect(DcmEffects.HAZARD_GRACE.get())){
            cir.setReturnValue(false);
        }
    }
}