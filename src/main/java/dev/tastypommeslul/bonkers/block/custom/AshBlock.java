package dev.tastypommeslul.bonkers.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

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
        QUARTER("quarter", 1, 3),
        HALF("half", 2, 5),
        FOURTH("fourth", 4, 8),
        FULL("full", 8, 16);

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
