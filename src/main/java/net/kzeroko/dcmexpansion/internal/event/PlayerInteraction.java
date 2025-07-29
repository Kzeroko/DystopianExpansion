package net.kzeroko.dcmexpansion.internal.event;

import net.kzeroko.dcmexpansion.internal.DcmTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerInteraction {
    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (event.getLevel().isClientSide() || !(event.getEntity() instanceof Player)) {
            return;
        }
        Level level = (Level) event.getLevel();
        BlockPos placedPos = event.getPos();
        BlockState placedState = event.getPlacedBlock();
        Item placedItem = placedState.getBlock().asItem();

        if (placedItem.builtInRegistryHolder().is(DcmTags.LOCKED_LOOTABLE_BLACKLIST)) {
            BlockPos.MutableBlockPos neighborPos = new BlockPos.MutableBlockPos();

            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        if (dx == 0 && dy == 0 && dz == 0) continue;

                        neighborPos.set(placedPos.getX() + dx, placedPos.getY() + dy, placedPos.getZ() + dz);
                        BlockState neighborState = level.getBlockState(neighborPos);

                        if (neighborState.is(DcmTags.LOCKED_LOOTABLES)) {
                            event.setCanceled(true);
                            return;
                        }
                    }
                }
            }
        }
    }

}