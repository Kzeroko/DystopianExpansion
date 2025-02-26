package net.kzeroko.dcmexpansion.network;

import net.kzeroko.dcmexpansion.DcmExpansion;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

@Mod.EventBusSubscriber(modid = DcmExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DcmNetworkHandler {
    private static final String PROTOCOL_VERSION = "0.1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(DcmExpansion.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        INSTANCE.registerMessage(0,
                BetterCombatHitPacket.class,
                BetterCombatHitPacket::encode,
                BetterCombatHitPacket::decode,
                BetterCombatHitPacket::handle // Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );
    }
}
