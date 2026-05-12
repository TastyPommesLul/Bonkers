package dev.tastypommeslul.bonkers;

import dev.tastypommeslul.bonkers.block.ModBlocks;
import dev.tastypommeslul.bonkers.datagen.ModBlockLootTableProvider;
import dev.tastypommeslul.bonkers.datagen.ModModelProvider;
import dev.tastypommeslul.bonkers.datagen.ModRecipeProvider;
import dev.tastypommeslul.bonkers.datagen.ModTagsProviders;
import dev.tastypommeslul.bonkers.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.List;
import java.util.Set;

@Mod(Bonkers.MODID)
public class Bonkers {
    public static final String MODID = "bonkers";
    public static final Logger LOGGER = LogUtils.getLogger();
    public Bonkers(IEventBus eventBus, ModContainer modContainer) {
        eventBus.addListener(this::commonSetup);

//        NeoForge.EVENT_BUS.register(this);

        eventBus.addListener(this::addCreative);
        eventBus.addListener(this::gatherClientData);
        eventBus.addListener(this::gatherServerData);

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }


    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("if u see this, why are u looking here?");
        LOGGER.info("if something went wrong, im sowwy :3");
    }
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.INGREDIENTS)) {
            event.accept(ModItems.TEST_ITEM);
        }
    }

    private void gatherServerData(GatherDataEvent.Server event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        var lookupProvider = event.getLookupProvider();
        generator.addProvider(true, new ModRecipeProvider.Runner(output, lookupProvider));
        generator.addProvider(true, new ModTagsProviders.Blocks(output, lookupProvider));
        generator.addProvider(true, new ModTagsProviders.Items(output, lookupProvider));

        generator.addProvider(true, new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)
        ), lookupProvider));
    }

    public void gatherClientData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        var lookupProvider = event.getLookupProvider();

        generator.addProvider(true, new ModModelProvider(output));
    }
}
