package dev.tastypommeslul.bonkers.datagen;

import dev.tastypommeslul.bonkers.block.ModBlocks;
import dev.tastypommeslul.bonkers.block.custom.AshBlock;
import dev.tastypommeslul.bonkers.item.ModItems;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jspecify.annotations.NonNull;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {

    public ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        this.add(ModBlocks.ASH_BLOCK.get(), block ->
                LootTable.lootTable().withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.ASH.get())
                                        // QUARTER
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(AshBlock.AshLevel.QUARTER.getMin(), AshBlock.AshLevel.QUARTER.getMax()))
                                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                                .hasProperty(AshBlock.LEVEL, AshBlock.AshLevel.QUARTER))))
                                        // HALF
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(AshBlock.AshLevel.HALF.getMin(), AshBlock.AshLevel.HALF.getMax()))
                                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                                .hasProperty(AshBlock.LEVEL, AshBlock.AshLevel.HALF))))
                                        // FOURTH
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(AshBlock.AshLevel.FOURTH.getMin(), AshBlock.AshLevel.FOURTH.getMax()))
                                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                                .hasProperty(AshBlock.LEVEL, AshBlock.AshLevel.FOURTH))))
                                        // FULL
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(AshBlock.AshLevel.FULL.getMin(), AshBlock.AshLevel.FULL.getMax()))
                                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                                .hasProperty(AshBlock.LEVEL, AshBlock.AshLevel.FULL))))
                                )
                )
        );
    }

    protected LootTable.Builder createMultipleDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }
    @Override
    protected @NonNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
