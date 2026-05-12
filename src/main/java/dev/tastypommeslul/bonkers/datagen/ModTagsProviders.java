package dev.tastypommeslul.bonkers.datagen;

import dev.tastypommeslul.bonkers.Bonkers;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public interface ModTagsProviders {
    class Items extends ItemTagsProvider {
        public Items(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider, Bonkers.MODID);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {

        }
    }

    class Blocks extends BlockTagsProvider {
        public Blocks(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider, Bonkers.MODID);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {

        }
    }
}
