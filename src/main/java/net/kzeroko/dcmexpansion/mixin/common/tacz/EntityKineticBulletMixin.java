package net.kzeroko.dcmexpansion.mixin.common.tacz;

import com.tacz.guns.entity.EntityKineticBullet;
import com.tacz.guns.util.TacHitResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityKineticBullet.class, priority = 1006, remap = false)
public abstract class EntityKineticBulletMixin extends Projectile {
    protected EntityKineticBulletMixin(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "onHitEntity", at = @At("HEAD"))
    public void onBulletHitEntity(TacHitResult result, Vec3 startVec, Vec3 endVec, CallbackInfo ci) {

        //if (result.getEntity() instanceof Player player && !player.level().isClientSide()) {
            //EntityKineticBullet.MaybeMultipartEntity parts = EntityKineticBullet.MaybeMultipartEntity.of(player);

        //}

    }

}