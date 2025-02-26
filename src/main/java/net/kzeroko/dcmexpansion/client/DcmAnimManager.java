package net.kzeroko.dcmexpansion.client;

import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

@OnlyIn(Dist.CLIENT)
public class DcmAnimManager {
    private final List<AnimationEntry> entries = new ArrayList<>();

    public void registerAnim(KeyframeAnimation animation, Predicate<Player> condition, int priority) {
        entries.add(new AnimationEntry(animation, condition, priority));
        entries.sort(Comparator.comparingInt(AnimationEntry::priority).reversed());
    }

    public static DcmAnimManager getInstance() {
        return new DcmAnimManager();
    }

    @Nullable
    public KeyframeAnimation getFirstMatchingAnim(Player player) {
        for (AnimationEntry entry : entries) {
            if (entry.condition.test(player)) {
                return entry.anim;
            }
        }
        return null;
    }

    private record AnimationEntry(KeyframeAnimation anim, Predicate<Player> condition, int priority) {
    }
}
