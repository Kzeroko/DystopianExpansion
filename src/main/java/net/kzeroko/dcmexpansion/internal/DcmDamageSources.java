package net.kzeroko.dcmexpansion.internal;

import net.kzeroko.dcmexpansion.DcmExpansion;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

public class DcmDamageSources {
    public static final ResourceKey<DamageType> HAZARD_GAS = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(DcmExpansion.MOD_ID, "hazard_gas"));

    private static Holder.Reference<DamageType> getHolder(RegistryAccess access, ResourceKey<DamageType> damageTypeKey) {
        return access.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damageTypeKey);
    }

    public static DamageSource hazardGas(RegistryAccess access) {
        return new DamageSource(getHolder(access, HAZARD_GAS));
    }

    public static void init() {
    }
}