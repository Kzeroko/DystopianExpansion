package net.kzeroko.dcmexpansion.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

@SuppressWarnings("unused")
public class KeycardItem extends Item {
    private final boolean openMilitary;
    private final boolean openSecret;
    private final boolean openLab;

    public KeycardItem(Rarity rarity, int durability) {
        this(rarity, durability, false, false, false);
    }

    public KeycardItem(Rarity rarity, int durability, boolean openMilitary, boolean openSecret, boolean openLab) {
        super((new Properties()).stacksTo(1).rarity(rarity).durability(durability));
        this.openMilitary = openMilitary;
        this.openSecret = openSecret;
        this.openLab = openLab;
    }

    public boolean isOpenMilitary() {
        return openMilitary;
    }

    public boolean isOpenSecret() {
        return openSecret;
    }

    public boolean isOpenLab() {
        return openLab;
    }
}