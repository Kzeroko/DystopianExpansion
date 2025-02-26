package net.kzeroko.dcmexpansion.effect;

import net.kzeroko.dcmexpansion.internal.DcmDamageSources;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class HazardGasEffect extends MobEffect {
    public HazardGasEffect() {
        super(MobEffectCategory.HARMFUL, 0xBFAD68FF);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        if (entity instanceof Player player && !player.level.isClientSide() && !player.isCreative()) {

            int multiplier = Math.min(amplifier + 1, 5);

            entity.hurt(DcmDamageSources.HAZARD_GAS, 2.0F * multiplier);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 25 == 0;
    }
}
