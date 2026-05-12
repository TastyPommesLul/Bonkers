package dev.tastypommeslul.bonkers.datagen;

import dev.tastypommeslul.bonkers.Bonkers;
import dev.tastypommeslul.bonkers.block.ModBlocks;
import dev.tastypommeslul.bonkers.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.data.PackOutput;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output) {
        super(output, Bonkers.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.ASH.get(), ModelTemplates.FLAT_ITEM);


        blockModels.createTrivialBlock(ModBlocks.ASH_BLOCK.get(), TexturedModel.CUBE);
    }
}
