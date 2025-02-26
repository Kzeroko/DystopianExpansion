package net.kzeroko.dcmexpansion.mixin.client;

import com.mojang.authlib.GameProfile;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonConfiguration;
import dev.kosmx.playerAnim.api.firstPerson.FirstPersonMode;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.modifier.AbstractFadeModifier;
import dev.kosmx.playerAnim.api.layered.modifier.SpeedModifier;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.core.util.Ease;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry;
import net.kzeroko.dcmexpansion.DcmExpansion;
import net.kzeroko.dcmexpansion.client.DcmAnimManager;
import net.kzeroko.dcmexpansion.item.KitItem;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("all")
@Mixin(value = AbstractClientPlayer.class, priority = 1001)
public abstract class AbstractClientPlayerMixin extends Player {

    // Animation Manager
    @Unique
    private final DcmAnimManager animManager = DcmAnimManager.getInstance();

    // Layer
    @Unique
    private final ModifierLayer<IAnimation> animLayer = new ModifierLayer<>();

    // Basics
    @Unique
    KeyframeAnimation currentAnimation = null;

    // Anim switch
    @Unique
    private boolean animSwitch = false;
    @Unique
    private boolean modified = false;
    @Unique
    private boolean armAnimationsEnabled = true;
    @Unique
    private boolean legAnimationsEnabled = true;
    @Unique
    private boolean otherAnimationsEnabled = true;
    @Unique
    private boolean itemAnimationsEnabled = true;

    public AbstractClientPlayerMixin(Level pLevel, BlockPos pPos, float pYRot, GameProfile pGameProfile) {
        super(pLevel, pPos, pYRot, pGameProfile);
    }

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void initAnim(ClientLevel pClientLevel, GameProfile pGameProfile, CallbackInfo ci) {
        PlayerAnimationAccess.getPlayerAnimLayer((AbstractClientPlayer) (Object) this).addAnimLayer(1500, animLayer);

        animManager.registerAnim(
                PlayerAnimationRegistry.getAnimation(new ResourceLocation(DcmExpansion.MOD_ID, "kititem")),
                this::isUsingKit,
                1
        );

    }

    @Override
    public void tick() {
        super.tick();

        KeyframeAnimation anim = animManager.getFirstMatchingAnim((AbstractClientPlayer) (Object) this);

        if (anim != null && !animSwitch) {
            animSwitch = true;
        } else if (anim == null && animSwitch) {
            animSwitch = false;
            removeAnim();
        }

        if (animSwitch) {
            playAnim(anim, 1.0F, 10, true);
        }
    }

    private boolean isUsingKit(Player player) {
        return player.isUsingItem() && player.getUseItem().getItem() instanceof KitItem;
    }

    @Unique
    private void removeAnim() {
        currentAnimation = null;
        animLayer.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(10, Ease.LINEAR), null, true);
        animLayer.setupAnim(1.0F / 20.0F);
        animLayer.tick();
    }

    @Unique
    private void playAnim(KeyframeAnimation anim, float speed, int fade, boolean firstPerson) {
        if (currentAnimation == anim || anim == null) return;
        currentAnimation = anim;
        ModifierLayer<IAnimation> layer = animLayer;
        var builder = anim.mutableCopy();
        builder.leftArm.setEnabled(armAnimationsEnabled);
        builder.rightArm.setEnabled(armAnimationsEnabled);
        builder.leftLeg.setEnabled(legAnimationsEnabled);
        builder.rightLeg.setEnabled(legAnimationsEnabled);
        builder.torso.setEnabled(otherAnimationsEnabled);
        builder.head.setEnabled(otherAnimationsEnabled);
        builder.body.setEnabled(otherAnimationsEnabled);
        anim = builder.build();
        if (modified) {
            layer.removeModifier(0);
        }
        modified = true;
        layer.addModifierBefore(new SpeedModifier(speed));

        if (firstPerson) {
            layer.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(fade, Ease.INOUTSINE), // AbstractFadeModifier.functionalFadeIn(20, (modelName, type, value) -> value)
                    new KeyframeAnimationPlayer(anim)
                            .setFirstPersonMode(FirstPersonMode.THIRD_PERSON_MODEL)
                            .setFirstPersonConfiguration(new FirstPersonConfiguration().setShowLeftArm(true).setShowRightArm(true).setShowLeftItem(true))
            );
        } else {
            layer.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(fade, Ease.INOUTSINE), new KeyframeAnimationPlayer(anim));
        }

        layer.setupAnim(1.0F / 20.0F);
    }
}
