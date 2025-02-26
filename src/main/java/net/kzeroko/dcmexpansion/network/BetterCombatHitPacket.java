package net.kzeroko.dcmexpansion.network;

import com.momosoftworks.coldsweat.core.event.TaskScheduler;
import net.kzeroko.dcmexpansion.internal.DcmTags;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class BetterCombatHitPacket {
    int hand; // 0: mainhand, 1: offhand
    List<Integer> hitEntityIds;
    int cursorEntityId;

    public BetterCombatHitPacket(int hand, List<Integer> hitEntityIds, int cursorEntityId) {
        this.hand = hand;
        this.hitEntityIds = hitEntityIds;
        this.cursorEntityId = cursorEntityId;
    }

    public static BetterCombatHitPacket decode(FriendlyByteBuf buf) {
        return new BetterCombatHitPacket(buf.readInt(), buf.readList(FriendlyByteBuf::readInt), buf.readInt());
    }

    public static void encode(BetterCombatHitPacket packet , FriendlyByteBuf buf) {
        buf.writeInt(packet.hand);
        buf.writeCollection(packet.hitEntityIds, FriendlyByteBuf::writeInt);
        buf.writeInt(packet.cursorEntityId);
    }

    public static void handle(BetterCombatHitPacket packet, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        if (context.getDirection().getReceptionSide().isServer()) {
            context.enqueueWork(() -> {
                ServerPlayer player = context.getSender();
                if (player != null) {

                    // Get all available entities
                    List<Entity> hitEntityList = packet.hitEntityIds
                            .stream()
                            .map(id -> player.getLevel().getEntity(id))
                            .filter(Objects::nonNull)
                            .filter(entity -> entity instanceof LivingEntity)
                            .toList();

                    // We are not using it much for now
                    Entity cursorEntity = player.getLevel().getEntity(packet.cursorEntityId);

                    // We get correct itemStack stats with corresponding hand
                    boolean isMainHand = packet.hand == 0;
                    ItemStack weapon = isMainHand ? player.getMainHandItem() : player.getOffhandItem();
                    var attributeModifiers = weapon.getAttributeModifiers(isMainHand ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);

                    double weaponAttackDamage = attributeModifiers.get(Attributes.ATTACK_DAMAGE)
                            .stream()
                            .mapToDouble(AttributeModifier::getAmount)
                            .sum();

                    // We also may need to consider attack damage from player
                    double prePlayerAttackDamage = player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                    double playerAttackDamage = prePlayerAttackDamage - weaponAttackDamage;

                    // Looks for all hit entities
                    for (Entity entity : hitEntityList) {

                        LivingEntity livingEntity = (LivingEntity) entity;

                        if (livingEntity.getType().is(DcmTags.FIXED_DAMAGE_BLACKLIST)) {
                            continue;
                        }

                        float maxHealth = livingEntity.getMaxHealth();

                        // If max health is less than 500, we deal percentage damage, otherwise just skip this entity
                        if (maxHealth >= 500) continue;

                        // The base factor of every melee attack
                        float baseFactor;
                        if (maxHealth <= 80.0F) {
                            baseFactor = 0.20F;
                        } else if (maxHealth <= 250.0F) {
                            baseFactor = 0.15F;
                        } else {
                            baseFactor = 0.10F;
                        }

                        // If entity is not cursor entity, extra weapon damage is slightly lower
                        float dFactor = entity == cursorEntity ? 1.0F : 0.8F;
                        float weaponDamage = (float) (weaponAttackDamage + (playerAttackDamage > 0 ? playerAttackDamage : 0)) * dFactor;

                        // Calculate weapon damage
                        float normalizedDamage = weaponDamage / 100.0F;
                        normalizedDamage = Mth.clamp(normalizedDamage, 0.0F, 1.0F);

                        float fa = 0.4F;
                        float fb = 0.4F;
                        float fc = 0.0F;
                        float wdResult = fa * Mth.square(normalizedDamage) + fb * normalizedDamage + fc;
                        float wdFactor = Mth.clamp(wdResult, 0.0F, 0.8F);

                        float finalDamage = maxHealth * (baseFactor + wdFactor);

                        // We deal fixed percentage damage with armor piercing
                        TaskScheduler.scheduleServer(() -> {
                            livingEntity.hurt(DamageSource.playerAttack(player).bypassArmor(), finalDamage);
                        }, 1);

                        // TODO: Maybe we should cancel the original damage, IDK

                    }

                }
            });
        }

        context.setPacketHandled(true);
    }

}
