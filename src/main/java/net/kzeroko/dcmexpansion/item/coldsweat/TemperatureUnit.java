package net.kzeroko.dcmexpansion.item.coldsweat;

import com.momosoftworks.coldsweat.api.util.Temperature;
import com.momosoftworks.coldsweat.common.capability.handler.EntityTempManager;
import com.momosoftworks.coldsweat.common.capability.temperature.PlayerTempCap;
import com.momosoftworks.coldsweat.config.spec.MainSettingsConfig;
import com.momosoftworks.coldsweat.core.init.EffectInit;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.math.FloatingLong;
import mekanism.common.util.StorageUtils;
import net.kzeroko.dcmexpansion.item.EnergyCurioItem;
import net.kzeroko.dcmexpansion.util.EnergyUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class TemperatureUnit extends EnergyCurioItem {
    private final int costSec;
    private final int strength;
    public TemperatureUnit(double chargeRate, double capacity, int costSec, int strength) {
        super(chargeRate, capacity);
        this.costSec = costSec;
        this.strength = strength;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity le = slotContext.entity();
        if (!le.level().isClientSide() && le.tickCount % 20 == 0) {

            IEnergyContainer energyContainer = StorageUtils.getEnergyContainer(stack, 0);
            FloatingLong energy = energyContainer == null ? FloatingLong.ZERO : energyContainer.getEnergy();
            FloatingLong energyCost = FloatingLong.create(EnergyUtil.convertEnergy(costSec, EnergyUtil.Type.FE, EnergyUtil.Type.J));

            if (energyContainer != null && !energy.isZero() && !energy.smallerThan(energyCost)) {

                EntityTempManager.getTemperatureCap(slotContext.entity()).ifPresent((icap) -> {

                    if (icap instanceof PlayerTempCap cap) {
                        double capTemp = cap.getTrait(Temperature.Trait.WORLD);
                        double freezeP = MainSettingsConfig.MIN_HABITABLE_TEMPERATURE.get();
                        double burnP = MainSettingsConfig.MAX_HABITABLE_TEMPERATURE.get();

                        double realTempC = toCelsius(capTemp);
                        double realFreezeP = toCelsius(freezeP);
                        double realBurnP = toCelsius(burnP);

                        // Debug
                        /*((Player)slotContext.entity()).displayClientMessage(
                                Component.nullToEmpty("World: " + realTempC + " | " + "freezeP: " + realFreezeP + " | " + "burnP: " + realBurnP),
                                true);*/

                        boolean isOn;
                        MobEffect effect = null;
                        if (realTempC <= realFreezeP) {
                            isOn = true;
                            effect = EffectInit.WARMTH.get();
                        } else if (realTempC >= realBurnP) {
                            isOn = true;
                            effect = EffectInit.FRIGIDNESS.get();
                        } else {
                            isOn = false;
                        }

                        if (effect != null) {
                            le.addEffect(new MobEffectInstance(effect, 60, strength, true, false));
                        }

                        extractEquippedEnergy(slotContext.entity(), isOn ? energyCost : energyCost.multiply(0.5));

                    }
                });

            }

        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> text, @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, text, pIsAdvanced);
        text.add(Component.empty());
        text.add(Component.translatable("tooltip.dcmexpansion.temperature_unit.usage1").withStyle(ChatFormatting.GRAY));
        text.add(Component.translatable("tooltip.dcmexpansion.temperature_unit.usage2", costSec).withStyle(ChatFormatting.GRAY));
        text.add(Component.translatable("tooltip.dcmexpansion.temperature_unit.usage3", costSec / 2).withStyle(ChatFormatting.GRAY));
    }

    private double toCelsius(double mcTemp) {
        return Temperature.convert(mcTemp, Temperature.Units.MC, Temperature.Units.C, true);
    }

    private double toMCTemp(double celsius) {
        return Temperature.convert(celsius, Temperature.Units.C, Temperature.Units.MC, true);
    }

}
