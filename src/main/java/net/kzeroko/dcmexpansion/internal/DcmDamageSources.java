package net.kzeroko.dcmexpansion.internal;

import net.minecraft.world.damagesource.DamageSource;

public class DcmDamageSources {
    public static final DamageSource HAZARD_GAS = (new DamageSource("hazard_gas")).bypassArmor().bypassInvul().bypassMagic();

    public static void init() {
    }
}