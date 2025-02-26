package net.kzeroko.dcmexpansion.internal;

import net.kzeroko.dcmexpansion.DcmExpansion;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class DcmTags {

    public static final TagKey<Item> REPAIRKIT_BLACKLIST = create(Registry.ITEM_REGISTRY, "repairkit_blacklist");
    public static final TagKey<Block> TEST_BLOCK_TAG = create(Registry.BLOCK_REGISTRY, "test_block");
    public static final TagKey<MobEffect> TEST_EFFECT_TAG = create(Registry.MOB_EFFECT_REGISTRY, "test_effect");
    public static final TagKey<EntityType<?>> FIXED_DAMAGE_BLACKLIST = create(Registry.ENTITY_TYPE_REGISTRY, "fixed_damage_blacklist");
    public static final TagKey<Biome> HAZARD_BIOMES = create(Registry.BIOME_REGISTRY, "hazard_biomes");

    private static <T extends IForgeRegistryEntry<T>> TagKey<T> create(ResourceKey<Registry<T>> registry, String id) {
        return TagKey.create(registry, new ResourceLocation(DcmExpansion.MOD_ID, id));
    }
}