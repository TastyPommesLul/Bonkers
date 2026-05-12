package dev.tastypommeslul.bonkers.block;

import dev.tastypommeslul.bonkers.Bonkers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Bonkers.MODID);




    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
