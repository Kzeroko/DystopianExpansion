package net.kzeroko.dcmexpansion.item;

import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.math.FloatingLong;
import mekanism.common.item.ItemEnergized;
import mekanism.common.util.StorageUtils;
import net.kzeroko.dcmexpansion.DcmExpansion;
import net.kzeroko.dcmexpansion.util.EnergyUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class EnergyCurioItem extends ItemEnergized implements ICurioItem {

    public EnergyCurioItem(double chargeRate, double capacity) {
        super(
                ()-> FloatingLong.createConst(EnergyUtil.convertEnergy(chargeRate, EnergyUtil.Type.FE, EnergyUtil.Type.J)),
                ()-> FloatingLong.createConst(EnergyUtil.convertEnergy(capacity, EnergyUtil.Type.FE, EnergyUtil.Type.J)),
                (new Properties()).tab(DcmExpansion.INTEGRATION).stacksTo(1)
        );
    }

    public boolean isEquippedBy(@Nullable LivingEntity entity) {
        return entity != null && CuriosApi.getCuriosHelper().findEquippedCurio(this, entity).isPresent();
    }

    @Override
    public @NotNull Rarity getRarity(@NotNull ItemStack stack) {
        return Rarity.RARE;
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public void extractEquippedEnergy(LivingEntity entity, FloatingLong energyCost) {
        if (entity instanceof Player player && player.isCreative()) {
            return;
        }
        CuriosApi.getCuriosHelper().getCuriosHandler(entity).ifPresent(curiosHandler ->
                curiosHandler.getCurios().forEach((identifier, stacksHandler) -> {
                    IDynamicStackHandler stacks = stacksHandler.getStacks();
                    for (int slot = 0; slot < stacks.getSlots(); slot++) {
                        ItemStack stack = stacks.getStackInSlot(slot);
                        if (!stack.isEmpty() && stack.getItem() == this) {

                            IEnergyContainer energyContainer = StorageUtils.getEnergyContainer(stack, 0);
                            FloatingLong energy = energyContainer == null ? FloatingLong.ZERO : energyContainer.getEnergy();

                            if (energyContainer != null && !energy.isZero() && !energy.smallerThan(energyCost)) {
                                energyContainer.extract(energyCost, Action.EXECUTE, AutomationType.MANUAL);
                            }
                        }
                    }
                })
        );
    }

    protected <T extends Event, S extends LivingEntity> void addListener(EventPriority priority, Class<T> eventClass, BiConsumer<T, S> listener, Function<T, S> wearerSupplier) {
        MinecraftForge.EVENT_BUS.addListener(priority, true, eventClass, event -> {
            S wearer = wearerSupplier.apply(event);
            if (isEquippedBy(wearer)) {
                listener.accept(event, wearer);
            }
        });
    }

    protected <T extends Event, S extends LivingEntity> void addListener(Class<T> eventClass, BiConsumer<T, S> listener, Function<T, S> wearerSupplier) {
        addListener(EventPriority.NORMAL, eventClass, listener, wearerSupplier);
    }

    protected <T extends LivingEvent> void addListener(EventPriority priority, Class<T> eventClass, BiConsumer<T, LivingEntity> listener) {
        addListener(priority, eventClass, listener, LivingEvent::getEntityLiving);
    }

    protected <T extends LivingEvent> void addListener(Class<T> eventClass, BiConsumer<T, LivingEntity> listener) {
        addListener(EventPriority.NORMAL, eventClass, listener);
    }

}