package net.kzeroko.dcmexpansion.item;

import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class RepairKitItem extends Item {
    private final float fixAmount;
    public RepairKitItem(float fixAmount) {
        super((new Properties()).stacksTo(32).rarity(Rarity.RARE));
        this.fixAmount = fixAmount;
    }

    public float getFixAmount() {
        return Mth.clamp(fixAmount, 0.0F, 1.0F);
    }
}