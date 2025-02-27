package net.kzeroko.dcmexpansion.internal.event;

import mcjty.incontrol.data.DataStorage;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Set;

public class GracetimeMessage {
    private static final long MORNING_TIME = 83L; // around 5 min
    private static final long DAY_LONG = 24000L;
    private boolean hasDone = false;

    @SubscribeEvent
    public void onTick(TickEvent.LevelTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.side.isServer()) {
            long dayTime = event.level.getDayTime();
            long currentTime = dayTime % DAY_LONG;

            if (currentTime == MORNING_TIME) {
                if (!hasDone) {
                    sendDayInfo(event.level);
                    hasDone = true;
                }
            } else {
                hasDone = false;
            }
        }
    }

    private void sendDayInfo(Level world) {
        DataStorage data = DataStorage.getData(world);

        Set<String> phases = data.getPhases();
        int day = data.getDaycounter();

        MutableComponent component;
        if (phases.contains("after_day3") || phases.contains("gracetime1") || phases.contains("gracetime2")) {
            component = Component.translatable("message.dcmexpansion.grace_day", day).withStyle(ChatFormatting.GREEN);
        } else {
            component = Component.translatable("message.dcmexpansion.normal_day", day).withStyle(ChatFormatting.YELLOW);
        }

        if (world.getServer() == null) return;

        world.getServer().getPlayerList().broadcastSystemMessage(component, true);
    }
}
