package net.kzeroko.dcmexpansion.registry;

import net.kzeroko.dcmexpansion.DcmExpansion;
import net.kzeroko.dcmexpansion.effect.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DcmEffects {
    public static final DeferredRegister<MobEffect> REGISTER = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, DcmExpansion.MOD_ID);
    public static RegistryObject<MobEffect> ANTI_INFECTION = REGISTER.register("anti_infection", AntiInfectionEffect::new);
    public static RegistryObject<MobEffect> FAST_HEAL = REGISTER.register("fast_heal", FastHealEffect::new);
    public static RegistryObject<MobEffect> ELECTRIFIED = REGISTER.register("electrified", ElectrifiedEffect::new);
    public static RegistryObject<MobEffect> HAZARD_GAS = REGISTER.register("hazard_gas", HazardGasEffect::new);
    public static RegistryObject<MobEffect> HAZARD_GRACE = REGISTER.register("hazard_grace", HazardGraceEffect::new);

}