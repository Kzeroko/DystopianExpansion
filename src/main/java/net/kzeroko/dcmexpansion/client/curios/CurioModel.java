package net.kzeroko.dcmexpansion.client.curios;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

public class CurioModel extends HumanoidModel<LivingEntity> {
    private final Item item;
    private final ModelPart root;

    private CurioModel(Item item, ModelPart root) {
        super(root);
        this.item = item;
        this.root = root;
    }

    public CurioModel(Item item) {
        this(item, constructRoot(item));
    }

    public static ModelLayerLocation getLayerLocation(Item item) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
        assert id != null;
        return new ModelLayerLocation(new ResourceLocation(id.getNamespace(), id.getPath()), id.getPath());
    }

    public static ModelPart constructRoot(Item item) {
        return Minecraft.getInstance().getEntityModels().bakeLayer(getLayerLocation(item));
    }

    public ModelPart getById(String id) {
        return switch (id) {
            case "head" -> this.head;
            case "hat" -> this.hat;
            case "body" -> this.body;
            case "right_arm" -> this.rightArm;
            case "left_arm" -> this.leftArm;
            case "right_leg" -> this.rightLeg;
            case "left_leg" -> this.leftLeg;
            default -> root.getChild(id);
        };
    }

    @Nonnull
    @Override
    protected Iterable<ModelPart> headParts() {
        return item instanceof ICurioRenderable renderable ? renderable.headParts().stream().map(this::getById).toList() : ImmutableList.of();
    }

    @Nonnull
    @Override
    protected Iterable<ModelPart> bodyParts() {
        return item instanceof ICurioRenderable renderable ? renderable.bodyParts().stream().map(this::getById).toList() : ImmutableList.of();
    }
}