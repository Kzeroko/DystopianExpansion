package net.kzeroko.dcmexpansion.registry;

import net.kzeroko.dcmexpansion.DcmExpansion;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DcmSounds
{
	public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DcmExpansion.MOD_ID);
	public static final RegistryObject<SoundEvent> ADRENALINE_OPEN = register("item.adrenaline.open");
	public static final RegistryObject<SoundEvent> ADRENALINE_INJECT = register("item.adrenaline.inject");
	public static final RegistryObject<SoundEvent> MODERNMEDKIT_1 = register("item.modernmedkit.1");
	public static final RegistryObject<SoundEvent> MODERNMEDKIT_2 = register("item.modernmedkit.2");
	public static final RegistryObject<SoundEvent> MODERNMEDKIT_3 = register("item.modernmedkit.3");
	public static final RegistryObject<SoundEvent> MODERNMEDKIT_4 = register("item.modernmedkit.4");
	public static final RegistryObject<SoundEvent> MODERNMEDKIT_FAST_1 = register("item.modernmedkit_fast.1");
	public static final RegistryObject<SoundEvent> MODERNMEDKIT_FAST_2 = register("item.modernmedkit_fast.2");
	public static final RegistryObject<SoundEvent> MODERNMEDKIT_FAST_3 = register("item.modernmedkit_fast.3");
	public static final RegistryObject<SoundEvent> MODERNMEDKIT_FAST_4 = register("item.modernmedkit_fast.4");
	public static final RegistryObject<SoundEvent> WEATHER_TESTER = register("item.weathertester.test");

	private static RegistryObject<SoundEvent> register(String key) {
		return REGISTER.register(key, () -> new SoundEvent(new ResourceLocation(DcmExpansion.MOD_ID, key)));
	}
}
