package net.kzeroko.dcmexpansion.internal.event;

import net.kzeroko.dcmexpansion.internal.DcmTags;
import net.kzeroko.dcmexpansion.registry.DcmEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class HazardGrace {
    @SubscribeEvent
    public static void onConditionHazardGrace(EntityJoinWorldEvent event) {
        if (!event.getWorld().isClientSide() && event.getEntity() instanceof Player player) {
            if (player.level.getBiome(player.getOnPos()).is(DcmTags.HAZARD_BIOMES)) {
                player.addEffect(new MobEffectInstance(DcmEffects.HAZARD_GRACE.get(), 30 * 20, 0, false, false, true));
            }
        }
    }
}