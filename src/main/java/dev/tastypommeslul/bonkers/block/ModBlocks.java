package dev.tastypommeslul.bonkers.block;

import dev.tastypommeslul.bonkers.Bonkers;
import dev.tastypommeslul.bonkers.block.custom.AshBlock;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Bonkers.MODID);


    public static final DeferredBlock<Block> ASH_BLOCK = BLOCKS.registerBlock("ash_block",
            props -> new AshBlock(props.sound(SoundType.GRAVEL).mapColor(DyeColor.GRAY).instabreak().noCollision()));



    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
