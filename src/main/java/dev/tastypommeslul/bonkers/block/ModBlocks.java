package dev.tastypommeslul.bonkers.block;

import dev.tastypommeslul.bonkers.Bonkers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jspecify.annotations.NonNull;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Bonkers.MODID);


    public static final DeferredBlock<Block> ASH_BLOCK = BLOCKS.registerBlock("ash_block", props -> new Block(props.sound(SoundType.GRAVEL)) {
        @Override
        protected @NonNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            return Block.column(16, 0, 6);
        }
    });



    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
