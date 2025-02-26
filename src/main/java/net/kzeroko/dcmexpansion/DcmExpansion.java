package net.kzeroko.dcmexpansion;

import com.mojang.logging.LogUtils;
import net.kzeroko.dcmexpansion.config.DcmExpansionConfig;
import net.kzeroko.dcmexpansion.internal.DcmDamageSources;
import net.kzeroko.dcmexpansion.internal.DcmTags;
import net.kzeroko.dcmexpansion.internal.event.GracetimeMessage;
import net.kzeroko.dcmexpansion.network.DcmNetworkHandler;
import net.kzeroko.dcmexpansion.registry.*;
import net.kzeroko.dcmexpansion.registry.modintegration.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;

@SuppressWarnings("all")
@Mod(DcmExpansion.MOD_ID)
public class DcmExpansion {

    public static final String MOD_ID = "dcmexpansion";
    public static final Logger LOGGER = LogUtils.getLogger();

    //public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    //public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
    //public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("example_block", () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties()));

    // Event class
    public static final GracetimeMessage GRACETIME_MESSAGE = new GracetimeMessage();

    // Curios slot icon
    private static final ResourceLocation CURIOS_ICON_UNIT = new ResourceLocation("curios:slot/unit");

    public DcmExpansion() {
        MinecraftForge.EVENT_BUS.register(this);

        final ModLoadingContext modLoadingContext = ModLoadingContext.get();
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::enqueueIMC);
        eventBus.addListener(this::processIMC);
        eventBus.addListener(this::addCreative);

        DcmCreativeTabs.CREATIVE_MODE_TABS.register(eventBus);
        DcmEffects.EFFECTS.register(eventBus);
        DcmSounds.SOUNDS.register(eventBus);

        ColdSweatItems.ITEMS.register(eventBus);
        ImmersiveAircraftItems.ITEMS.register(eventBus);
        PneumaticCraftItems.ITEMS.register(eventBus);
        TaczItems.ITEMS.register(eventBus);
        WeatherItems.ITEMS.register(eventBus);

        DcmFirstaidItems.ITEMS.register(eventBus);
        DcmHealingItems.ITEMS.register(eventBus);
        DcmIntegrationItems.ITEMS.register(eventBus);
        DcmMiscItems.ITEMS.register(eventBus);
        DcmWeapons.ITEMS.register(eventBus);

        modLoadingContext.registerConfig(ModConfig.Type.SERVER, DcmExpansionConfig.serverSpec);
        modLoadingContext.registerConfig(ModConfig.Type.CLIENT, DcmExpansionConfig.clientSpec);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        DcmDamageSources.init();
        DcmTags.init();
        DcmNetworkHandler.register();
        MinecraftForge.EVENT_BUS.register(GRACETIME_MESSAGE);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        //if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            //event.accept(EXAMPLE_BLOCK_ITEM);
    }

    // IMC

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // Init curios slots
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () ->
                new SlotTypeMessage.Builder("unit").priority(400).icon(CURIOS_ICON_UNIT).build());
    }

    private void processIMC(final InterModProcessEvent event) {
        // WIP
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}
