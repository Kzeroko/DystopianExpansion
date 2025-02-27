package net.kzeroko.dcmexpansion.item.tacz;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.kzeroko.dcmexpansion.client.curios.CurioModel;
import net.kzeroko.dcmexpansion.client.curios.ICurioRenderable;
import net.kzeroko.dcmexpansion.item.CurioItem;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.List;

public class BPVestLight extends CurioItem implements ICurioRenderable {
    public BPVestLight() {
        super(220);
    }

    @Override
    public void curioBreak(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public <T extends LivingEntity, M extends EntityModel<T>> void render(
            ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent,
            MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks,
            float ageInTicks, float netHeadYaw, float headPitch) {
        CurioModel model = getModel(stack);

        matrixStack.pushPose();
        LivingEntity entity = slotContext.entity();

        model.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTicks);
        model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        ICurioRenderer.followBodyRotations(entity, model);

        VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, RenderType.armorCutoutNoCull(getTexture(stack)),
                false,false);

        model.renderToBuffer(matrixStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        matrixStack.popPose();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public LayerDefinition constructLayerDefinition() {
        MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0.4F), 0.0F);
        PartDefinition partdefinition = mesh.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bpv_light = body.addOrReplaceChild("bpv_light", CubeListBuilder.create().texOffs(0, 12).addBox(-4.0F, -22.0F, -3.0F, 8.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 41).addBox(3.3125F, -17.1875F, -2.9375F, 1.0F, 5.0F, 1.0F, new CubeDeformation(-0.125F))
                .texOffs(15, 41).mirror().addBox(-4.3125F, -17.1875F, -2.9375F, 1.0F, 5.0F, 1.0F, new CubeDeformation(-0.125F)).mirror(false)
                .texOffs(15, 41).mirror().addBox(-4.3125F, -17.1875F, 1.6875F, 1.0F, 5.0F, 1.0F, new CubeDeformation(-0.125F)).mirror(false)
                .texOffs(15, 41).addBox(3.3125F, -17.1875F, 1.6875F, 1.0F, 5.0F, 1.0F, new CubeDeformation(-0.125F))
                .texOffs(0, 0).addBox(-4.0F, -22.0625F, 1.6875F, 8.0F, 10.0F, 1.0F, new CubeDeformation(0.0625F))
                .texOffs(0, 24).addBox(-3.0F, -24.1875F, 1.6875F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0625F))
                .texOffs(19, 12).addBox(-5.0F, -18.0F, -2.625F, 1.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(19, 0).addBox(4.0F, -18.0F, -2.625F, 1.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F)); //, PartPose.ZERO);

        PartDefinition cube_r1 = bpv_light.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 37).addBox(-2.9201F, -1.0F, 1.0427F, 3.0F, 2.0F, 1.0F, new CubeDeformation(-0.3125F)), PartPose.offsetAndRotation(0.0F, -23.8125F, 3.6309F, 0.9055F, -0.9798F, -0.8137F));

        PartDefinition cube_r2 = bpv_light.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(27, 0).addBox(-2.0F, -1.0F, -0.2403F, 4.0F, 2.0F, 1.0F, new CubeDeformation(-0.3125F)), PartPose.offsetAndRotation(0.0F, -23.8125F, 3.6309F, 0.4538F, 0.0F, 0.0F));

        PartDefinition cube_r3 = bpv_light.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(25, 37).addBox(-0.0799F, -1.0F, 1.0427F, 3.0F, 2.0F, 1.0F, new CubeDeformation(-0.3125F)), PartPose.offsetAndRotation(0.0F, -23.8125F, 3.6309F, 0.9055F, 0.9798F, 0.8137F));

        PartDefinition ammo = bpv_light.addOrReplaceChild("ammo", CubeListBuilder.create().texOffs(9, 28).addBox(2.8828F, -1.125F, -1.4272F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 24).addBox(2.3828F, -4.0F, -1.8022F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.25F))
                .texOffs(0, 34).addBox(2.3828F, -3.5F, -1.3022F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 28).addBox(0.4669F, -1.5F, -1.5056F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offset(-2.8828F, -14.125F, -2.6978F));

        PartDefinition cube_r4 = ammo.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(18, 31).addBox(-1.0F, -1.5F, -1.375F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.125F, 0.0F, 0.2618F, 0.0F));

        PartDefinition cube_r5 = ammo.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(36, 29).addBox(-1.0F, -2.5F, -0.375F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(15, 24).addBox(-1.0F, -3.0F, -0.875F, 2.0F, 4.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(5.5703F, -1.0F, -0.6147F, 0.0F, -0.2269F, 0.0F));

        PartDefinition cube_r6 = ammo.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(19, 0).addBox(-1.0313F, 0.9375F, -0.125F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0459F, -1.0625F, -0.5622F, 0.0F, 0.2269F, 0.0F));

        PartDefinition cube_r7 = ammo.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(19, 12).addBox(0.0313F, -1.0625F, -0.125F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(40, 13).addBox(-0.7188F, -0.0625F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.8116F, -1.0625F, -0.5622F, 0.0F, -0.2269F, 0.0F));

        PartDefinition bone = bpv_light.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offsetAndRotation(3.6727F, -23.6804F, -0.1056F, 0.0F, 0.0F, -0.0175F));

        PartDefinition cube_r8 = bone.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(27, 31).addBox(-1.5F, -2.5F, -0.5F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2303F, 0.556F, -1.9133F, 2.9671F, 0.0F, 0.3927F));

        PartDefinition cube_r9 = bone.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(40, 9).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2669F, -0.6443F, -1.4897F, 1.9722F, 0.0F, 0.3927F));

        PartDefinition cube_r10 = bone.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(7, 37).addBox(-1.5F, -2.0F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2488F, -0.6006F, 1.4433F, 1.2566F, 0.0F, 0.3927F));

        PartDefinition cube_r11 = bone.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(32, 4).addBox(-1.5F, -2.0F, -0.5F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4618F, 1.1149F, 2.1543F, 0.0524F, 0.0F, 0.3927F));

        PartDefinition bone2 = bpv_light.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.6727F, -23.6804F, -0.1056F, 0.0F, 0.0F, 0.0175F));

        PartDefinition cube_r12 = bone2.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(9, 31).addBox(-1.5F, -2.5F, -0.5F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2303F, 0.556F, -1.9133F, 2.9671F, 0.0F, -0.3927F));

        PartDefinition cube_r13 = bone2.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(38, 0).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2669F, -0.6443F, -1.4897F, 1.9722F, 0.0F, -0.3927F));

        PartDefinition cube_r14 = bone2.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(35, 36).addBox(-1.5F, -2.0F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2488F, -0.6006F, 1.4433F, 1.2566F, 0.0F, -0.3927F));

        PartDefinition cube_r15 = bone2.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(31, 11).addBox(-1.5F, -2.0F, -0.5F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4618F, 1.1149F, 2.1543F, 0.0524F, 0.0F, -0.3927F));

        PartDefinition bone3 = bpv_light.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(30, 41).addBox(-0.2579F, -0.4448F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(-2.0546F, -16.0395F, -3.5F, 0.0064F, -0.122F, 3.0016F));

        PartDefinition cube_r16 = bone3.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(25, 41).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3125F)), PartPose.offsetAndRotation(-1.0741F, -0.0358F, 0.0F, 0.0F, 0.0F, -0.0349F));

        PartDefinition cube_r17 = bone3.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(32, 20).addBox(-2.0F, -0.5F, -0.4375F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.3125F)), PartPose.offsetAndRotation(0.4006F, 0.0975F, -0.0625F, 0.0F, 0.0F, 0.2967F));

        PartDefinition cube_r18 = bone3.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(33, 26).addBox(-3.0625F, -0.75F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.3125F)), PartPose.offsetAndRotation(1.523F, -0.0342F, 0.0F, 0.0F, 0.0F, -0.2269F));

        PartDefinition bone4 = bpv_light.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(20, 41).addBox(-0.2579F, -0.4448F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.125F)), PartPose.offsetAndRotation(-2.0546F, -16.852F, -3.375F, 0.0266F, -0.2253F, 2.9114F));

        PartDefinition cube_r19 = bone4.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(41, 4).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3125F)), PartPose.offsetAndRotation(-1.0741F, -0.0358F, 0.0F, 0.0F, 0.0F, -0.0349F));

        PartDefinition cube_r20 = bone4.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(31, 23).addBox(-2.0F, -0.5F, -0.4375F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.3125F)), PartPose.offsetAndRotation(0.4006F, 0.0975F, -0.0625F, 0.0F, 0.0F, 0.2967F));

        PartDefinition cube_r21 = bone4.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(32, 17).addBox(-3.0625F, -0.75F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.3125F)), PartPose.offsetAndRotation(1.523F, -0.0342F, 0.0F, 0.0F, 0.0F, -0.2269F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public List<String> headParts() {
        return Lists.newArrayList("body");
    }


}