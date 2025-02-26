package net.kzeroko.dcmexpansion.item;

import mekanism.api.math.FloatingLong;
import mekanism.common.item.ItemEnergized;
import net.kzeroko.dcmexpansion.DcmExpansion;
import net.kzeroko.dcmexpansion.util.EnergyUtil;
import net.minecraft.world.item.Rarity;

public class EnergyMaterialItem extends ItemEnergized {
    public EnergyMaterialItem(double chargeRate, double capacity, Rarity rarity) {
        super(
                ()-> FloatingLong.createConst(EnergyUtil.convertEnergy(chargeRate, EnergyUtil.Type.FE, EnergyUtil.Type.J)),
                ()-> FloatingLong.createConst(EnergyUtil.convertEnergy(capacity, EnergyUtil.Type.FE, EnergyUtil.Type.J)),
                (new Properties()).tab(DcmExpansion.INTEGRATION).stacksTo(1).fireResistant().rarity(rarity)
        );
    }
}
