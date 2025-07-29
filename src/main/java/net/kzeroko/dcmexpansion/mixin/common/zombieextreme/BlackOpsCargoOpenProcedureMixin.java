package net.kzeroko.dcmexpansion.mixin.common.zombieextreme;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.netty.buffer.Unpooled;
import net.kzeroko.dcmexpansion.item.KeycardItem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import zombie_extreme.procedures.BlackOpsCargoOpenProcedure;
import zombie_extreme.world.inventory.BlackOpsCargoGUIMenu;

@Mixin(value = BlackOpsCargoOpenProcedure.class, remap = false)
public abstract class BlackOpsCargoOpenProcedureMixin {

    @WrapMethod(method = "execute")
    private static void test(LevelAccessor world, double x, double y, double z, Entity entity, Operation<Void> original) {
        try {
            dcmexpansion$execute(world, x, y, z, entity);
        } catch (Exception e) {
            //original.call(world, x, y, z, entity);
        }
    }

    @Unique
    private static void dcmexpansion$execute(LevelAccessor world, double x, double y, double z, Entity entity) {
        if (entity == null) {
            return;
        }

        ItemStack heldItem = ItemStack.EMPTY;
        if (entity instanceof LivingEntity livingEntity) {
            heldItem = livingEntity.getMainHandItem();
        }

        if (heldItem.getItem() instanceof KeycardItem keycard && keycard.isOpenSecret()) {
            if (entity instanceof ServerPlayer player) {
                heldItem.getItem().damageItem(heldItem, 1, player, sp -> sp.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                BlockPos blockPos = BlockPos.containing(x, y, z);
                NetworkHooks.openScreen(player, new MenuProvider() {
                    @Override
                    public @NotNull Component getDisplayName() {
                        return Component.literal("BlackOpsCargoGUI");
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player) {
                        return new BlackOpsCargoGUIMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(blockPos));
                    }
                }, blockPos);
            }

            if (world instanceof Level level) {
                BlockPos blockPos = BlockPos.containing(x, y, z);
                SoundEvent soundEvent = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("zombie_extreme:open_military_cargo"));
                if (soundEvent != null) {
                    if (!level.isClientSide()) {
                        level.playSound(null, blockPos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
                    } else {
                        level.playLocalSound(x, y, z, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F, false);
                    }
                }
            }
        }
    }
}