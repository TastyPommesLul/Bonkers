package dev.tastypommeslul.bonkers;

import com.mojang.logging.LogUtils;
import dev.tastypommeslul.bonkers.block.ModBlocks;
import dev.tastypommeslul.bonkers.block.custom.AshBlock;
import dev.tastypommeslul.bonkers.datagen.*;
import dev.tastypommeslul.bonkers.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;

@Mod(Bonkers.MODID)
public class Bonkers {
    public static final String MODID = "bonkers";
    public static final Logger LOGGER = LogUtils.getLogger();
    public Bonkers(IEventBus eventBus, ModContainer modContainer) {
        eventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);

        eventBus.addListener(this::addCreative);
        eventBus.addListener(this::gatherClientData);

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
            event.accept(ModItems.ASH.get());
        }
        if (event.getTabKey().equals(CreativeModeTabs.FUNCTIONAL_BLOCKS)) {
            event.accept(ModItems.ASH_BLOCK_ITEM.get());
        }
    }

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getHand().equals(InteractionHand.MAIN_HAND)) return;
        if (event.getLevel().isClientSide()) return;

        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockPos posBelow = pos.below();
        BlockState state = level.getBlockState(pos);
        BlockState below = level.getBlockState(posBelow);

        if (!state.is(BlockTags.LOGS_THAT_BURN) || !event.getItemStack().getItem().equals(Items.FLINT_AND_STEEL)) return;

        if (below.is(ModBlocks.ASH_BLOCK.get())) {
            AshBlock.AshLevel ashLevel = below.getValue(AshBlock.LEVEL);

            if (ashLevel != AshBlock.AshLevel.FULL) {
                level.setBlock(posBelow, below.setValue(AshBlock.LEVEL, ashLevel.getNextAshLevel()), Block.UPDATE_ALL);
                level.removeBlock(pos, false);
            } else {
                level.setBlock(pos, ModBlocks.ASH_BLOCK.get().defaultBlockState().setValue(AshBlock.LEVEL, AshBlock.AshLevel.QUARTER), Block.UPDATE_ALL);
            }
        } else {
            level.setBlock(pos, ModBlocks.ASH_BLOCK.get().defaultBlockState().setValue(AshBlock.LEVEL, AshBlock.AshLevel.QUARTER), Block.UPDATE_ALL);
        }
    }


    public void gatherClientData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        var lookupProvider = event.getLookupProvider();

        generator.addProvider(true, new ModModelProvider(output));
        generator.addProvider(true, new ModRecipeProvider.Runner(output, lookupProvider));
        generator.addProvider(true, new ModTagsProviders.Blocks(output, lookupProvider));
        generator.addProvider(true, new ModTagsProviders.Items(output, lookupProvider));
        generator.addProvider(true, new ModDataMapProvider(output, lookupProvider));

        generator.addProvider(true, new LootTableProvider(output, Collections.emptySet(), List.of(
                new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK)
        ), lookupProvider));
    }
}
