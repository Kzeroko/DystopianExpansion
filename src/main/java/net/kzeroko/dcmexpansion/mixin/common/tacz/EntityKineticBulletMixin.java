package net.kzeroko.dcmexpansion.mixin.common.tacz;

import com.tacz.guns.entity.EntityKineticBullet;
import com.tacz.guns.util.TacHitResult;
import net.minecraft.world.entity.EntityType;
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
        /*
        if (result.getEntity() instanceof Player player && !player.level.isClientSide()) {
            dcmexpansion$handleVestDamage(player, result, ci);
        }
        */
    }

    /*

    @Unique
    private void dcmexpansion$handleVestDamage(Player player, TacHitResult result, CallbackInfo ci) {
        @NotNull CurioItem[] vests = {TaczItems.BP_VEST_LIGHT.get(), TaczItems.BP_VEST_MEDIUM.get()};
        float[] damageFactors = {0.05F, 0.005F};

        // TODO: Handles damage factor based on projectile property, which is not static

        EntityKineticBullet.MaybeMultipartEntity parts = EntityKineticBullet.MaybeMultipartEntity.of(player);

        for (int i = 0; i < vests.length; i++) {
            if (vests[i].isEquippedBy(player)) {
                vests[i].damageAllEquipped(player, 1);

                if (!result.isHeadshot()) {
                    float damage = this.getDamage(result.getLocation()) * damageFactors[i];
                    this.tacAttackEntity(DamageSource.thrown(this, this.getOwner()), parts, damage);
                    ci.cancel();
                }

                break;
            }
        }
    }

    */
}