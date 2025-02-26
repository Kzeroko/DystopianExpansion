package net.kzeroko.dcmexpansion.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class DcmExpansionConfig { // TODO: Add more configurables

    public static final ForgeConfigSpec serverSpec;
    public static final ForgeConfigSpec clientSpec;
    public static final ServerSide SERVER_SIDE;
    public static final ClientSide CLIENT_SIDE;

    static {
        final Pair<ServerSide, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerSide::new);
        serverSpec = specPair.getRight();
        SERVER_SIDE = specPair.getLeft();
    }

    static {
        final Pair<ClientSide, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientSide::new);
        clientSpec = specPair.getRight();
        CLIENT_SIDE = specPair.getLeft();
    }

    public static class ServerSide {

        ServerSide(ForgeConfigSpec.Builder builder) {

            builder.comment("Server-side config for DCM Expansion");

            builder.push("dcm_medical");

            builder.push("health_kit_items");

            firstAidKitHP = builder.comment("The heal percentage of First-Aid Kit base on user's max health (Integer Only)")
                    .translation("config.dcmexpansion.firstAidKitHP")
                    .defineInRange("firstAidKitHP", 35, 1, 100);

            modernMedkitHP = builder.comment("The heal percentage of Modern Medical Device base on user's max health (Integer Only)")
                    .translation("config.dcmexpansion.modernMedkitHP")
                    .defineInRange("modernMedkitHP", 100, 1, 100);

            modernMedkitResistanceSeconds = builder.comment("Resistance duration of Modern Medical Device in seconds (Integer Only)")
                    .translation("config.dcmexpansion.modernMedkitResistanceSeconds")
                    .defineInRange("modernMedkitResistanceSeconds", 30, 1, Integer.MAX_VALUE);

            builder.pop();

            builder.push("other_items");

            adrenalineAbsorptionSeconds = builder.comment("Absorption duration of Adrenaline Syringe in seconds (Integer Only)")
                    .translation("config.dcmexpansion.adrenalineAbsorptionSeconds")
                    .defineInRange("adrenalineAbsorptionSeconds", 25, 1, Integer.MAX_VALUE);

            adrenalineSpeedSeconds = builder.comment("Speed duration of Adrenaline Syringe in seconds (Integer Only)")
                    .translation("config.dcmexpansion.adrenalineSpeedSeconds")
                    .defineInRange("adrenalineSpeedSeconds", 45, 1, Integer.MAX_VALUE);

            serumHungerSeconds = builder.comment("Hunger duration of Anti-Infection Serum in seconds (Integer Only)")
                    .translation("config.dcmexpansion.serumHungerSeconds")
                    .defineInRange("serumHungerSeconds", 30, 1, Integer.MAX_VALUE);

            serumAntiInfectionSeconds = builder.comment("Anti-Infection duration of Anti-Infection Serum in seconds (Integer Only)")
                    .translation("config.dcmexpansion.serumAntiInfectionSeconds")
                    .defineInRange("serumAntiInfectionSeconds", 1200, 1, Integer.MAX_VALUE);

            serumAntiInfectionCure = builder.comment("Whether if Anti-Infection Serum cures Infection effect")
                    .translation("config.dcmexpansion.serumAntiInfectionCure")
                    .define("serumAntiInfectionCure", true);

            builder.pop();

            builder.push("firstaid_items");

            bandage_pro = new FirstAidItems(builder, "bandage_pro", 20, 1, 4000);

            builder.pop();

            builder.pop();

            builder.push("dcm_integration");

            compressedBatteryCharge = builder.comment("Compressed Battery charge amount (Integer Only)")
                    .translation("config.dcmexpansion.compressedBatteryCharge")
                    .defineInRange("compressedBatteryCharge", 10000, 1, 297000);

            builder.pop();
        }


        public final ForgeConfigSpec.IntValue firstAidKitHP;
        public final ForgeConfigSpec.IntValue modernMedkitHP;
        public final ForgeConfigSpec.IntValue modernMedkitResistanceSeconds;
        public final ForgeConfigSpec.IntValue adrenalineAbsorptionSeconds;
        public final ForgeConfigSpec.IntValue adrenalineSpeedSeconds;
        public final ForgeConfigSpec.IntValue serumHungerSeconds;
        public final ForgeConfigSpec.IntValue serumAntiInfectionSeconds;
        public final ForgeConfigSpec.BooleanValue serumAntiInfectionCure;
        public final FirstAidItems bandage_pro;
        public final ForgeConfigSpec.IntValue compressedBatteryCharge;

        public static class FirstAidItems {

            public final ForgeConfigSpec.IntValue totalHeals;
            public final ForgeConfigSpec.IntValue secondsPerHeal;
            public final ForgeConfigSpec.IntValue applyTime;

            FirstAidItems(ForgeConfigSpec.Builder builder, String name, int initTotalHeals, int initSecondsPerHeal, int initApplyTime) {
                builder.push(name);
                totalHeals = builder
                        .comment("The total heals this item does when applied. 1 heal = half a heart")
                        .translation("firstaid.config.totalheals")
                        .defineInRange("totalsHeals", initTotalHeals, 1, Byte.MAX_VALUE);
                secondsPerHeal = builder
                        .comment("The time it takes for a single heal to trigger. Total time this item is active = this * totalHeals")
                        .translation("firstaid.config.secondsperheal")
                        .defineInRange("secondsPerHeal", initSecondsPerHeal, 1, Short.MAX_VALUE);
                applyTime = builder
                        .comment("The time it takes in the GUI to apply the item in milliseconds")
                        .translation("firstaid.config.applytime")
                        .defineInRange("applyTime", initApplyTime, 0, 16000);
                builder.pop();
            }
        }
    }

    public static class ClientSide {
        public final ForgeConfigSpec.BooleanValue showPlayerNameplate;

        public ClientSide(ForgeConfigSpec.Builder builder) {

            builder.comment("Client-side config for DCM Expansion");

            builder.push("render");

            showPlayerNameplate = builder
                    .comment("Whether the game renders player's nameplate or not")
                    .translation("config.dcmexpansion.showPlayerNameplate")
                    .define("showPlayerNameplate", false);

            builder.pop();
        }
    }

}
