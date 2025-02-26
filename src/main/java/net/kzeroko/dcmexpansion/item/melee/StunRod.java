package net.kzeroko.dcmexpansion.item.melee;

import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.math.FloatingLong;
import mekanism.common.util.StorageUtils;
import net.kzeroko.dcmexpansion.item.EnergyMeleeItem;
import net.kzeroko.dcmexpansion.registry.DcmEffects;
import net.kzeroko.dcmexpansion.util.EnergyUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class StunRod extends EnergyMeleeItem {
    public StunRod(double chargeRate, double capacity, int costOnHit, int pAttackDamageModifier, float pAttackSpeedModifier) {
        super(chargeRate, capacity, pAttackDamageModifier, costOnHit, pAttackSpeedModifier);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if (attacker instanceof Player player && !player.level.isClientSide()) {

            IEnergyContainer energyContainer = StorageUtils.getEnergyContainer(stack, 0);
            FloatingLong energy = energyContainer == null ? FloatingLong.ZERO : energyContainer.getEnergy();
            FloatingLong energyCost = FloatingLong.create(EnergyUtil.convertEnergy(getCostOnHit(), EnergyUtil.Type.FE, EnergyUtil.Type.J));

            if (energyContainer != null && !energy.isZero() && !energy.smallerThan(energyCost)) {
                if (target.getMaxHealth() < 500.0F
                        && (target.getMobType() == MobType.UNDEAD || target instanceof Enemy)
                ) {
                    if (!target.hasEffect(DcmEffects.ELECTRIFIED.get())) {
                        target.addEffect(new MobEffectInstance(DcmEffects.ELECTRIFIED.get(), 60, 0, true, true));
                    }
                    energyContainer.extract(energyCost, Action.EXECUTE, AutomationType.MANUAL);
                }
            }

        }
        return super.hurtEnemy(stack, target, attacker);
    }

}
