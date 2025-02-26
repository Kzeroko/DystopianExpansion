package net.kzeroko.dcmexpansion.item.coldsweat;

import com.momosoftworks.coldsweat.api.util.Temperature;
import com.momosoftworks.coldsweat.common.capability.EntityTempManager;
import com.momosoftworks.coldsweat.common.capability.PlayerTempCap;
import com.momosoftworks.coldsweat.config.ColdSweatConfig;
import com.momosoftworks.coldsweat.core.init.EffectInit;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.math.FloatingLong;
import mekanism.common.util.StorageUtils;
import net.kzeroko.dcmexpansion.item.EnergyCurioItem;
import net.kzeroko.dcmexpansion.util.EnergyUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
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
        if (!le.level.isClientSide() && le.tickCount % 20 == 0) {

            IEnergyContainer energyContainer = StorageUtils.getEnergyContainer(stack, 0);
            FloatingLong energy = energyContainer == null ? FloatingLong.ZERO : energyContainer.getEnergy();
            FloatingLong energyCost = FloatingLong.create(EnergyUtil.convertEnergy(costSec, EnergyUtil.Type.FE, EnergyUtil.Type.J));

            if (energyContainer != null && !energy.isZero() && !energy.smallerThan(energyCost)) {

                EntityTempManager.getTemperatureCap(slotContext.entity()).ifPresent((icap) -> {

                    if (icap instanceof PlayerTempCap cap) {
                        double capTemp = cap.getTemp(Temperature.Type.WORLD);
                        double freezeP = ColdSweatConfig.getInstance().getMinTempHabitable();
                        double burnP = ColdSweatConfig.getInstance().getMaxTempHabitable();

                        double realTempC = toCelsius(capTemp);
                        double realFreezeP = toCelsius(freezeP);
                        double realBurnP = toCelsius(burnP);

                        // Debug
                        /*((Player)slotContext.entity()).displayClientMessage(
                                Component.nullToEmpty("World: " + realTempC + " | " + "freezeP: " + realFreezeP + " | " + "burnP: " + realBurnP),
                                true);*/

                        if (realTempC <= realFreezeP || realTempC >= realBurnP) {
                            le.addEffect(new MobEffectInstance(EffectInit.INSULATED.get(), 60, strength, true, false));
                            extractEquippedEnergy(slotContext.entity(), energyCost);
                        } else {
                            extractEquippedEnergy(slotContext.entity(), energyCost.multiply(0.5));
                        }

                    }
                });

                /*EntityTempManager.getTemperatureCap(slotContext.entity()).ifPresent((icap) -> {

                    if (icap instanceof PlayerTempCap cap) {
                        double capTemp = cap.getTemp(Temperature.Type.CORE);
                        double freezeP = cap.getTemp(Temperature.Type.FREEZING_POINT);
                        double burnP = cap.getTemp(Temperature.Type.BURNING_POINT);

                        double realTempC = toCelsius(capTemp);
                        double realFreezeP = toCelsius(freezeP);
                        double realBurnP = toCelsius(burnP);

                        double lowerRange = realFreezeP + range * (realBurnP - realFreezeP);
                        double upperRange = realBurnP - range * (realBurnP - realFreezeP);

                        if (realTempC < lowerRange || realTempC > upperRange) {

                            if (realTempC < lowerRange) {
                                realTempC += strength;
                            }

                            if (realTempC > upperRange) {
                                realTempC -= strength;
                            }

                            cap.setTemp(Temperature.Type.CORE, toMCTemp(realTempC));
                            extractEquippedEnergy(slotContext.entity(), energyCost);

                        }

                        //cap.setTemp(Temperature.Type.CORE, toMCTemp(18)); //test
                        //extractEquippedEnergy(slotContext.entity(), energyCost); //test
                    }
                });*/
            }

        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> text, @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, text, pIsAdvanced);
        text.add(TextComponent.EMPTY);
        text.add(new TranslatableComponent("tooltip.dcmexpansion.temperature_unit.usage1").withStyle(ChatFormatting.GRAY));
        text.add(new TranslatableComponent("tooltip.dcmexpansion.temperature_unit.usage2", costSec).withStyle(ChatFormatting.GRAY));
        text.add(new TranslatableComponent("tooltip.dcmexpansion.temperature_unit.usage3", costSec / 2).withStyle(ChatFormatting.GRAY));
    }

    private double toCelsius(double mcTemp) {
        return Temperature.convertUnits(mcTemp, Temperature.Units.MC, Temperature.Units.C, true);
    }

    private double toMCTemp(double celsius) {
        return Temperature.convertUnits(celsius, Temperature.Units.C, Temperature.Units.MC, true);
    }

}
