package net.kzeroko.dcmexpansion.mixin.common.securitycraft;

import net.geforcemods.securitycraft.blockentities.BlockPocketBlockEntity;
import net.geforcemods.securitycraft.blocks.BlockPocketWallBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.LazyOptional;
import net.smileycorp.hordes.common.Hordes;
import net.smileycorp.hordes.common.hordeevent.capability.IHordeEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockPocketWallBlock.class)
public class BlockPocketWallBlockMixin {
    @Inject(method = "getCollisionShape", at = @At("HEAD"), cancellable = true)
    public void getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext collisionContext, CallbackInfoReturnable<VoxelShape> cir) {
        if (collisionContext instanceof EntityCollisionContext ctx && ctx.getEntity() instanceof Player player && level.getBlockEntity(pos) instanceof BlockPocketBlockEntity be) {

            if (player.level.isClientSide()) return;

            LazyOptional<IHordeEvent> hordeEvent = player.getCapability(Hordes.HORDE_EVENT, null);
            if (!player.level.isDay() && hordeEvent.resolve().isPresent() && hordeEvent.resolve().get().isHordeDay(player)) {
                player.displayClientMessage(
                        new TranslatableComponent("message.dcmexpansion.horde_disable_pocket_block").withStyle(ChatFormatting.AQUA),
                        true
                );
                cir.setReturnValue(Shapes.block());
                cir.cancel();
            }

        }
    }
}