package net.kzeroko.dcmexpansion.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class CooldownUtil {

    /** Transformed access for getting exact seconds of an item's cooldown */
    public static int getCooldownSeconds(Player player, Item item) {
        var cooldowns = player.getCooldowns().cooldowns;
        var cooldownInstance = cooldowns.get(item);
        if (cooldownInstance != null) {
            int remainingTicks = cooldownInstance.endTime - player.getCooldowns().tickCount;
            if (remainingTicks > 0) {
                float cooldownSeconds = remainingTicks / 20.0F;
                return (int) cooldownSeconds;
            }
        }
        return 0;
    }
}
