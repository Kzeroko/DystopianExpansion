package net.kzeroko.dcmexpansion.registry;

import net.kzeroko.dcmexpansion.DcmExpansion;
import net.kzeroko.dcmexpansion.effect.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DcmEffects {

    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, DcmExpansion.MOD_ID);
    
    public static RegistryObject<MobEffect> ANTI_INFECTION = EFFECTS.register("anti_infection", AntiInfectionEffect::new);
    public static RegistryObject<MobEffect> FAST_HEAL = EFFECTS.register("fast_heal", FastHealEffect::new);
    public static RegistryObject<MobEffect> ELECTRIFIED = EFFECTS.register("electrified", ElectrifiedEffect::new);
    public static RegistryObject<MobEffect> HAZARD_GAS = EFFECTS.register("hazard_gas", HazardGasEffect::new);
    public static RegistryObject<MobEffect> HAZARD_GRACE = EFFECTS.register("hazard_grace", HazardGraceEffect::new);

}