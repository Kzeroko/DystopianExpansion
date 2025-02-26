package net.kzeroko.dcmexpansion.mixin.common;

import net.kzeroko.dcmexpansion.registry.DcmEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity {
    protected MobMixin(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(at = @At("HEAD"), method = "isNoAi", cancellable = true)
    private void electrifiedMobDisableAI(CallbackInfoReturnable<Boolean> cir) {
        Mob mob = (Mob) (Object) this;
        if(mob.hasEffect(DcmEffects.ELECTRIFIED.get())) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }

}