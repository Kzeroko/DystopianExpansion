package net.kzeroko.dcmexpansion.client.event;

import net.bettercombat.api.AttackHand;
import net.kzeroko.dcmexpansion.network.BetterCombatHitPacket;
import net.kzeroko.dcmexpansion.network.DcmNetworkHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

import java.util.List;
import java.util.stream.Collectors;

public class BetterCombatClient {

    public static void onBetterCombatHit(LocalPlayer player, AttackHand hand, List<Entity> entities, Entity cursorEntity) {
        if (player != null) {
            int handId = hand.isOffHand() ? 1 : 0;
            List<Integer> hitEntityIds = entities
                    .stream()
                    .map(Entity::getId)
                    .collect(Collectors.toList());

            int cursorEntityId = cursorEntity != null ? cursorEntity.getId() : -1;

            DcmNetworkHandler.INSTANCE.sendToServer(new BetterCombatHitPacket(handId, hitEntityIds, cursorEntityId));
        }
    }

}
