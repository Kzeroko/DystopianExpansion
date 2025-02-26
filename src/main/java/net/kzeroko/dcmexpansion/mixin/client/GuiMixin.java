package net.kzeroko.dcmexpansion.mixin.client;

import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
@Mixin(value = Gui.class, priority = 9999)
public class GuiMixin {

    @Shadow @Nullable protected Component overlayMessageString;

    @ModifyConstant(
            method = "setOverlayMessage",
            constant = @Constant(intValue = 60)
    )
    private int modifyOverlayMessageTime(int constant) {
        if (this.overlayMessageString != null && this.overlayMessageString.getContents() instanceof TranslatableContents translatable
                && translatable.getKey().startsWith("message.dcmexpansion.weather")) {
            return 160;
        }
        return constant;
    }

}