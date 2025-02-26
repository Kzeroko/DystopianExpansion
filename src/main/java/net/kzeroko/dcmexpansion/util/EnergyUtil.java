package net.kzeroko.dcmexpansion.util;

import mekanism.api.energy.IEnergyContainer;
import mekanism.api.math.FloatingLong;
import mekanism.common.util.StorageUtils;
import net.minecraft.world.item.ItemStack;

public class EnergyUtil {
    private static final double FE_TO_J = 2.5;
    private static final double FE_TO_EU = 0.25;
    private static final double J_TO_FE = 0.4;
    private static final double J_TO_EU = 0.1;
    private static final double EU_TO_FE = 4;
    private static final double EU_TO_J = 10;

    /**
     * Energy conversion equation helper <p>
     * 1 FE = 2.5 J = 0.25 EU <p>
     * 0.4 FE = 1 J = 0.1 EU <p>
     * 4 FE = 10 J = 1 EU
     * */
    public static double convertEnergy(double value, Type from, Type to) {
        return switch (from) {
            case FE -> switch (to) {
                case FE -> value;
                case J -> feToJoules(value);
                case EU -> feToEu(value);
            };
            case J -> switch (to) {
                case FE -> joulesToFe(value);
                case J -> value;
                case EU -> joulesToEu(value);
            };
            case EU -> switch (to) {
                case FE -> euToFe(value);
                case J -> euToJoules(value);
                case EU -> value;
            };
        };
    }

    private static double feToJoules(double fe) {
        return fe * FE_TO_J;
    }

    private static double feToEu(double fe) {
        return fe * FE_TO_EU;
    }

    private static double joulesToFe(double joules) {
        return joules * J_TO_FE;
    }

    private static double joulesToEu(double joules) {
        return joules * J_TO_EU;
    }

    private static double euToFe(double eu) {
        return eu * EU_TO_FE;
    }

    private static double euToJoules(double eu) {
        return eu * EU_TO_J;
    }

    public enum Type {
        FE, J, EU;
        Type() {
        }
    }

    /** Check if an ItemStack is charged with energy */
    public static boolean isStackCharged(ItemStack stack) {
        IEnergyContainer energyContainer = StorageUtils.getEnergyContainer(stack, 0);
        FloatingLong energy = energyContainer == null ? FloatingLong.ZERO : energyContainer.getEnergy();
        return energyContainer != null && energy.greaterThan(FloatingLong.ZERO);
    }
}
