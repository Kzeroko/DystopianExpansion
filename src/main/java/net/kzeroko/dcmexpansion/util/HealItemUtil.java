package net.kzeroko.dcmexpansion.util;

import ichttt.mods.firstaid.FirstAidConfig;
import net.kzeroko.dcmexpansion.registry.DcmEffects;
import net.kzeroko.dcmexpansion.registry.DcmSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class HealItemUtil {

    /** Play sound base on item's usage, specifically for medkit items */
    public static void playMedKitSound(Player player, Level world) {
        int usage = player.getTicksUsingItem();

        if (player.hasEffect(DcmEffects.FAST_HEAL.get())) {
            if (usage == 0) {
                world.playSound(null, player.getX(), player.getY(), player.getZ(),
                        DcmSounds.MODERNMEDKIT_FAST_1.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            }

            else if (usage == 12)  {
                world.playSound(null, player.getX(), player.getY(), player.getZ(),
                        DcmSounds.MODERNMEDKIT_FAST_2.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            }

            else if (usage == 24) {
                world.playSound(null, player.getX(), player.getY(), player.getZ(),
                        DcmSounds.MODERNMEDKIT_FAST_3.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            }

            else if (usage == 36) {
                world.playSound(null, player.getX(), player.getY(), player.getZ(),
                        DcmSounds.MODERNMEDKIT_FAST_4.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        } else {
            if (usage == 0) {
                world.playSound(null, player.getX(), player.getY(), player.getZ(),
                        DcmSounds.MODERNMEDKIT_1.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            }

            else if (usage == 25)  {
                world.playSound(null, player.getX(), player.getY(), player.getZ(),
                        DcmSounds.MODERNMEDKIT_2.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            }

            else if (usage == 50) {
                world.playSound(null, player.getX(), player.getY(), player.getZ(),
                        DcmSounds.MODERNMEDKIT_3.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            }

            else if (usage == 75) {
                world.playSound(null, player.getX(), player.getY(), player.getZ(),
                        DcmSounds.MODERNMEDKIT_4.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        }

    }

    // I don't get it ;_;
    public static int getMaxHealth() {
        FirstAidConfig.Server server = FirstAidConfig.SERVER;
        return server.maxHealthHead.get()
                + server.maxHealthLeftArm.get()
                + server.maxHealthLeftLeg.get()
                + server.maxHealthLeftFoot.get()
                + server.maxHealthBody.get()
                + server.maxHealthRightArm.get()
                + server.maxHealthRightLeg.get()
                + server.maxHealthRightFoot.get();
    }
}
