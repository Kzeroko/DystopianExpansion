package net.kzeroko.dcmexpansion.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.math.FloatingLong;
import mekanism.common.item.ItemEnergized;
import mekanism.common.util.StorageUtils;
import net.kzeroko.dcmexpansion.DcmExpansion;
import net.kzeroko.dcmexpansion.util.EnergyUtil;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class EnergyMeleeItem extends ItemEnergized {
    private final Multimap<Attribute, AttributeModifier> attributesOn;
    private final Multimap<Attribute, AttributeModifier> attributesOff;
    private final int costOnHit;
    public EnergyMeleeItem(double chargeRate, double capacity, int costOnHit, int pAttackDamageModifier, float pAttackSpeedModifier) {
        super(
                ()-> FloatingLong.createConst(EnergyUtil.convertEnergy(chargeRate, EnergyUtil.Type.FE, EnergyUtil.Type.J)),
                ()-> FloatingLong.createConst(EnergyUtil.convertEnergy(capacity, EnergyUtil.Type.FE, EnergyUtil.Type.J)),
                (new Properties()).tab(DcmExpansion.WEAPONS).stacksTo(1).setNoRepair().fireResistant()
        );
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builderOn = ImmutableMultimap.builder();
        builderOn.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", pAttackDamageModifier, AttributeModifier.Operation.ADDITION));
        builderOn.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", pAttackSpeedModifier, AttributeModifier.Operation.ADDITION));
        this.attributesOn = builderOn.build();

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builderOff = ImmutableMultimap.builder();
        builderOff.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (int) pAttackDamageModifier * 0.5, AttributeModifier.Operation.ADDITION));
        builderOff.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (float) pAttackSpeedModifier - 0.3F, AttributeModifier.Operation.ADDITION));
        this.attributesOff = builderOff.build();

        this.costOnHit = costOnHit;
    }

    @Nonnull
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(@Nonnull EquipmentSlot slot, @Nonnull ItemStack stack) {
        IEnergyContainer energyContainer = StorageUtils.getEnergyContainer(stack, 0);
        FloatingLong energy = energyContainer == null ? FloatingLong.ZERO : energyContainer.getEnergy();
        FloatingLong hitCost = FloatingLong.create(EnergyUtil.convertEnergy(costOnHit, EnergyUtil.Type.FE, EnergyUtil.Type.J));
        boolean isValid = energyContainer != null && !energy.isZero() && !energy.smallerThan(hitCost);
        return slot == EquipmentSlot.MAINHAND ? (isValid ? attributesOn : attributesOff) : super.getAttributeModifiers(slot, stack);
    }

    public int getCostOnHit() {
        return this.costOnHit;
    }
}
