package dev.tastypommeslul.bonkers.datagen;

import dev.tastypommeslul.bonkers.Bonkers;
import dev.tastypommeslul.bonkers.block.ModBlocks;
import dev.tastypommeslul.bonkers.block.custom.AshBlock;
import dev.tastypommeslul.bonkers.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, Bonkers.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.ASH.get(), ModelTemplates.FLAT_ITEM);



        Identifier modelLoc = TexturedModel.CUBE.create(ModBlocks.ASH_BLOCK.get(), blockModels.modelOutput);



        Variant variant = new Variant(modelLoc);



        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(
                        ModBlocks.ASH_BLOCK.get(),
                        BlockModelGenerators.variant(variant)
                ).with(
                        PropertyDispatch.modify(AshBlock.LEVEL)
                                .select(AshBlock.AshLevel.QUARTER, v -> v.withModel(modelLoc.withSuffix("_quarter")))
                                .select(AshBlock.AshLevel.HALF, v -> v.withModel(modelLoc.withSuffix("_half")))
                                .select(AshBlock.AshLevel.FOURTH, v -> v.withModel(modelLoc.withSuffix("_fourth")))
                                .select(AshBlock.AshLevel.FULL, v -> v.withModel(modelLoc.withSuffix("_full")))
                )
        );
    }
}
