package dev.tastypommeslul.bonkers.item;

import dev.tastypommeslul.bonkers.Bonkers;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Bonkers.MODID);

    public static final DeferredItem<Item> TEST_ITEM = ITEMS.registerItem("test_item", Item::new);


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
