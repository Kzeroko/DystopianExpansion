package net.kzeroko.dcmexpansion.mixin.common.securitycraft;

import net.geforcemods.securitycraft.blockentities.BlockPocketBlockEntity;
import net.geforcemods.securitycraft.blocks.BlockPocketWallBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.smileycorp.hordes.hordeevent.capability.HordeEvent;
import net.smileycorp.hordes.hordeevent.capability.HordeSavedData;
import net.smileycorp.hordes.hordeevent.capability.HordeSpawn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockPocketWallBlock.class)
public class BlockPocketWallBlockMixin {
    @Inject(method = "getCollisionShape", at = @At("HEAD"), cancellable = true)
    public void getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext collisionContext, CallbackInfoReturnable<VoxelShape> cir) {
        if (collisionContext instanceof EntityCollisionContext ctx && ctx.getEntity() instanceof Player player && level.getBlockEntity(pos) instanceof BlockPocketBlockEntity be) {

            if (player.level().isClientSide()) return;

            ServerPlayer spe = HordeSpawn.getHordePlayer(player);
            if (spe != null) {
                HordeEvent horde = HordeSavedData.getData((ServerLevel)player.level()).getEvent(spe);
                if (horde != null && horde.isActive(spe) && spe.level().isDay()) {
                    player.displayClientMessage(
                            Component.translatable("message.dcmexpansion.horde_disable_pocket_block").withStyle(ChatFormatting.AQUA),
                            true
                    );
                    cir.setReturnValue(Shapes.block());
                    cir.cancel();
                }

            }

        }
    }
}