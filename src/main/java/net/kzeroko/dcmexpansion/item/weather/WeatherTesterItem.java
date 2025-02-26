package net.kzeroko.dcmexpansion.item.weather;

import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.math.FloatingLong;
import mekanism.common.item.ItemEnergized;
import mekanism.common.util.StorageUtils;
import net.kzeroko.dcmexpansion.DcmExpansion;
import net.kzeroko.dcmexpansion.registry.DcmSounds;
import net.kzeroko.dcmexpansion.util.CooldownUtil;
import net.kzeroko.dcmexpansion.util.EnergyUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import weather2.ClientTickHandler;
import weather2.ServerTickHandler;
import weather2.weathersystem.WeatherManager;
import weather2.weathersystem.storm.StormObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherTesterItem extends ItemEnergized {
    private final int cooldowns;
    private final int scanDistance;

    public WeatherTesterItem(double chargeRate, double capacity, int cooldowns, int scanDistance) {
        super(
                ()-> FloatingLong.createConst(EnergyUtil.convertEnergy(chargeRate, EnergyUtil.Type.FE, EnergyUtil.Type.J)),
                ()-> FloatingLong.createConst(EnergyUtil.convertEnergy(capacity, EnergyUtil.Type.FE, EnergyUtil.Type.J)),
                (new Properties()).tab(DcmExpansion.INTEGRATION).stacksTo(1)
        );
        this.cooldowns = cooldowns;
        this.scanDistance = scanDistance;
    }


    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);

        IEnergyContainer energyContainer = StorageUtils.getEnergyContainer(heldStack, 0);
        FloatingLong energy = energyContainer == null ? FloatingLong.ZERO : energyContainer.getEnergy();
        FloatingLong energyCost = FloatingLong.create((int) EnergyUtil.convertEnergy(100, EnergyUtil.Type.FE, EnergyUtil.Type.J));

        if (!level.isClientSide() && energyContainer != null && !energy.isZero() && !energy.smallerThan(energyCost)) {
            Vec3 playerPos = player.position();

            // Get storm from player pos
            StormObject stormAny;
            WeatherManager weatherManager = getWeatherManagerFor(level);
            if (player.isCrouching()) {
                stormAny = weatherManager.getClosestStorm(playerPos, scanDistance, 4);
            } else {
                stormAny = weatherManager.getClosestStormAny(playerPos, scanDistance);
            }

            TranslatableComponent message;

            if (stormAny != null) {
                Vec3 stormPos = stormAny.posGround;
                double distance = playerPos.distanceTo(stormPos);

                List<Component> elements = new ArrayList<>();

                // Get weather tags
                if (stormAny.isCloudlessStorm()) elements.add(new TranslatableComponent("message.dcmexpansion.weather.cloudless_storm"));
                if (stormAny.isRealStorm()) elements.add(new TranslatableComponent("message.dcmexpansion.weather.real_storm"));
                if (stormAny.isTornadoFormingOrGreater()) elements.add(new TranslatableComponent("message.dcmexpansion.weather.tornado_forming"));
                if (stormAny.isCycloneFormingOrGreater()) elements.add(new TranslatableComponent("message.dcmexpansion.weather.cyclone_forming"));
                if (stormAny.isSpinning()) elements.add(new TranslatableComponent("message.dcmexpansion.weather.spinning"));
                if (stormAny.isTropicalCyclone()) elements.add(new TranslatableComponent("message.dcmexpansion.weather.tropical_cyclone"));
                if (stormAny.isHurricane()) elements.add(new TranslatableComponent("message.dcmexpansion.weather.hurricane"));
                if (stormAny.isSharknado()) elements.add(new TranslatableComponent("message.dcmexpansion.weather.sharknado"));

                double dx = stormPos.x - playerPos.x;
                double dz = stormPos.z - playerPos.z;
                String directionS = getCardinalDirection(dx, dz);

                TranslatableComponent distanceComponent = new TranslatableComponent("message.dcmexpansion.weather.distance_storm",
                        String.format("%.2f", distance) + "M",
                        new TranslatableComponent("message.dcmexpansion.weather.direction." + directionS)
                );

                String messageDelimiter = " | ";

                if (!elements.isEmpty()) {
                    distanceComponent.append(messageDelimiter);
                    for (int i = 0; i < elements.size(); i++) {
                        distanceComponent.append(elements.get(i));
                        if (i < elements.size() - 1) {
                            distanceComponent.append(messageDelimiter);
                        }
                    }
                }

                message = distanceComponent;

            }
            else {
                message = new TranslatableComponent("message.dcmexpansion.weather.no_storm");
            }

            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    DcmSounds.WEATHER_TESTER.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            player.displayClientMessage(message.withStyle(ChatFormatting.GREEN), true);

            energyContainer.extract(energyCost, Action.EXECUTE, AutomationType.MANUAL);
            player.getCooldowns().addCooldown(this, 20 * cooldowns);

            return InteractionResultHolder.success(heldStack);
        } else {
            return InteractionResultHolder.fail(heldStack);
        }

    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> text, @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, text, pIsAdvanced);

        if (pLevel != null && pLevel.isClientSide) {
            LocalPlayer clientPlayer = Minecraft.getInstance().player;
            if (clientPlayer != null) {
                Player player = pLevel.getPlayerByUUID(clientPlayer.getUUID());
                if (player != null) {
                    int cooldownSeconds = CooldownUtil.getCooldownSeconds(player, this);
                    boolean isReady = cooldownSeconds == 0;
                    text.add(new TranslatableComponent("tooltip.dcmexpansion.weather_tester.cooldown")
                            .append(new TextComponent(isReady ? (" READY") : (" " + cooldownSeconds + "S"))
                                    .withStyle(isReady ? ChatFormatting.GREEN : ChatFormatting.AQUA)));
                }
            }
        }

        text.add(new TranslatableComponent("tooltip.dcmexpansion.weather_tester.range")
                .append(new TextComponent(String.valueOf(scanDistance)).withStyle(ChatFormatting.YELLOW)));
        text.add(TextComponent.EMPTY);
        text.add(new TranslatableComponent("tooltip.dcmexpansion.weather_tester.usage1").withStyle(ChatFormatting.GRAY));
        text.add(new TranslatableComponent("tooltip.dcmexpansion.weather_tester.usage2").withStyle(ChatFormatting.GRAY));


    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack pStack) {
        return false;
    }

    public static WeatherManager getWeatherManagerFor(Level world) {
        if (world.isClientSide) {
            return getWeatherManagerClient();
        } else {
            return ServerTickHandler.getWeatherManagerFor((world.dimension()));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static WeatherManager getWeatherManagerClient() {
        return ClientTickHandler.weatherManager;
    }

    private String getCardinalDirection(double dx, double dz) {
        if (Math.abs(dx) > Math.abs(dz)) {
            return dx > 0 ? "east" : "west";
        } else {
            return dz > 0 ? "south" : "north";
        }
    }

}