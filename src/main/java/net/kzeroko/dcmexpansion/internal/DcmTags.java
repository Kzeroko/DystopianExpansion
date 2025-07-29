package net.kzeroko.dcmexpansion.internal;

import net.kzeroko.dcmexpansion.DcmExpansion;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

@SuppressWarnings("all")
public class DcmTags {

    public static final TagKey<Item> REPAIRKIT_BLACKLIST = TagKey.create(Registries.ITEM, new ResourceLocation(DcmExpansion.MOD_ID, "repairkit_blacklist"));
    public static final TagKey<Item> LOCKED_LOOTABLE_BLACKLIST = TagKey.create(Registries.ITEM, new ResourceLocation(DcmExpansion.MOD_ID, "locked_lootable_blacklist"));

    public static final TagKey<Block> LOCKED_LOOTABLES = TagKey.create(Registries.BLOCK, new ResourceLocation(DcmExpansion.MOD_ID, "locked_lootables"));

    public static final TagKey<MobEffect> TEST_EFFECT_TAG = TagKey.create(Registries.MOB_EFFECT, new ResourceLocation(DcmExpansion.MOD_ID, "test_effect"));

    public static final TagKey<EntityType<?>> FIXED_DAMAGE_BLACKLIST = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(DcmExpansion.MOD_ID, "fixed_damage_blacklist"));

    public static final TagKey<Biome> HAZARD_BIOMES = TagKey.create(Registries.BIOME, new ResourceLocation(DcmExpansion.MOD_ID, "hazard_biomes"));

    public static void init() {
    }
}