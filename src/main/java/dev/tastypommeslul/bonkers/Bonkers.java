package dev.tastypommeslul.bonkers;

import dev.tastypommeslul.bonkers.block.ModBlocks;
import dev.tastypommeslul.bonkers.datagen.ModBlockLootTableProvider;
import dev.tastypommeslul.bonkers.datagen.ModModelProvider;
import dev.tastypommeslul.bonkers.datagen.ModRecipeProvider;
import dev.tastypommeslul.bonkers.datagen.ModTagsProviders;
import dev.tastypommeslul.bonkers.item.ModItems;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Set;

@Mod(Bonkers.MODID)
public class Bonkers {
    public static final String MODID = "bonkers";
    public static final Logger LOGGER = LogUtils.getLogger();
    public Bonkers(IEventBus eventBus, ModContainer modContainer) {
        eventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);

        eventBus.addListener(this::addCreative);

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }


    @SubscribeEvent
    public void gatherData(GatherDataEvent event) {
        event.createProvider(ModModelProvider::new);
        event.createProvider(ModRecipeProvider.Runner::new);
        event.createProvider(ModTagsProviders.Blocks::new);
        event.createProvider(ModTagsProviders.Items::new);

        event.createProvider((output, lookupProvider) -> new LootTableProvider(
                output, Set.of(), List.of(
                        new LootTableProvider.SubProviderEntry(
                                ModBlockLootTableProvider::new,
                                LootContextParamSets.BLOCK
                        )
                ), lookupProvider
        ));
    }
}
