package dev.tastypommeslul.bonkers.block.custom;

import dev.tastypommeslul.bonkers.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

public class AshBlock extends Block {
    public static final EnumProperty<AshLevel> LEVEL = EnumProperty.create("level", AshLevel.class);
    public static final VoxelShape QUARTER_SHAPE = Block.column(16, 0, 4);
    public static final VoxelShape HALF_SHAPE = Block.column(16, 0, 8);
    public static final VoxelShape FOURTH_SHAPE = Block.column(16, 0, 12);
    public static final VoxelShape FULL_SHAPE = Block.column(16, 0, 16);

    public AshBlock(Properties properties) {
        super(properties);
    }


    @Override
    protected @NonNull InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (state.getValue(LEVEL) == AshLevel.FULL) return InteractionResult.FAIL;
        if (itemStack.getItem().equals(ModBlocks.ASH_BLOCK.get().asItem())) {
            level.setBlock(pos, state.setValue(LEVEL, state.getValue(LEVEL).getNextAshLevel()), Block.UPDATE_ALL);
            level.playSound(null, pos, this.soundType.getPlaceSound(), SoundSource.BLOCKS);
            itemStack.consume(1, player);
            return InteractionResult.SUCCESS;
        }
        return super.useItemOn(itemStack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(LEVEL)) {
            case QUARTER -> QUARTER_SHAPE;
            case HALF -> HALF_SHAPE;
            case FOURTH -> FOURTH_SHAPE;
            case FULL -> FULL_SHAPE;
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    public enum AshLevel implements StringRepresentable {
        QUARTER("quarter", 2, 4),
        HALF("half", 6, 8),
        FOURTH("fourth", 10, 12),
        FULL("full", 14, 16);

        public AshBlock.AshLevel getNextAshLevel() {
            return switch (this) {
                case QUARTER -> AshBlock.AshLevel.HALF;
                case HALF -> AshBlock.AshLevel.FOURTH;
                case FOURTH -> AshBlock.AshLevel.FULL;
                default -> AshBlock.AshLevel.FULL;
            };
        }
        private final String name;
        private final int min;
        private final int max;

        public int getMin() {
            return this.min;
        }
        public int getMax() {
            return this.max;
        }


        AshLevel(String name, int min, int max) {
            this.name = name;
            this.min = min;
            this.max = max;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
