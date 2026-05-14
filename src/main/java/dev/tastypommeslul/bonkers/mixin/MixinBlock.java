package dev.tastypommeslul.bonkers.mixin;

import dev.tastypommeslul.bonkers.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.extensions.IBlockExtension;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(IBlockExtension.class)
public interface MixinBlock {

    /**
     * @author
     * @reason
     */
    @Overwrite
    default boolean onCaughtFire(BlockState state, Level level, BlockPos pos, @Nullable Direction direction, @Nullable LivingEntity igniter) {
        level.setBlock(pos, ModBlocks.ASH_BLOCK.get().defaultBlockState(), Block.UPDATE_ALL);
        return true;
    }
}
