package net.kzeroko.dcmexpansion.item;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class SimpleMeleeItem extends SwordItem {
    public SimpleMeleeItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }
}
