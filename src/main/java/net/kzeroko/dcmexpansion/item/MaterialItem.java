package net.kzeroko.dcmexpansion.item;

import net.kzeroko.dcmexpansion.DcmExpansion;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class MaterialItem extends Item {
    public MaterialItem(int stackTo, Rarity rarity) {
        super((new Properties()).tab(DcmExpansion.INTEGRATION).stacksTo(stackTo).rarity(rarity));
    }
}