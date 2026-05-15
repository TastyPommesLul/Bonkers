package dev.tastypommeslul.bonkers.item;

import dev.tastypommeslul.bonkers.Bonkers;
import dev.tastypommeslul.bonkers.block.ModBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Bonkers.MODID);

    public static final DeferredItem<Item> ASH = ITEMS.registerItem("ash", Item::new);

    public static final DeferredItem<BlockItem> ASH_BLOCK_ITEM = ITEMS.registerItem("ash_block", props -> new BlockItem(ModBlocks.ASH_BLOCK.get(), props.useBlockDescriptionPrefix()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
