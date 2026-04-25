package com.srcfur.badhygiene.fluids;

import com.srcfur.badhygiene.blocks.ModBlocks;
import com.srcfur.badhygiene.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;

public class UrineFluid extends FlowingFluid {

    @Override
    public @NotNull Fluid getFlowing() {
        return ModFluids.URINE_FLOWING.get();
    }

    @Override
    public @NotNull Fluid getSource() {
        return ModFluids.URINE_STILL.get();
    }

    @Override
    protected boolean canConvertToSource(@NotNull Level level) {
        return false;
    }

    @Override
    protected void beforeDestroyingBlock(@NotNull LevelAccessor levelAccessor, @NotNull BlockPos blockPos, @NotNull BlockState blockState) {

    }

    @Override
    protected int getSlopeFindDistance(@NotNull LevelReader levelReader) {
        return 4;
    }

    @Override
    protected int getDropOff(@NotNull LevelReader levelReader) {
        return 4;
    }

    @Override
    public @NotNull Item getBucket() {
        return ModItems.URINE_BUCKET.get();
    }

    @Override
    protected boolean canBeReplacedWith(@NotNull FluidState fluidState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull Fluid fluid, @NotNull Direction direction) {
        return false;
    }

    @Override
    public @NotNull FluidType getFluidType() {
        return ModFluids.URINE_TYPE.get();
    }

    @Override
    public int getTickDelay(@NotNull LevelReader levelReader) {
        return 5;
    }

    @Override
    protected float getExplosionResistance() {
        return 0;
    }

    @Override
    protected @NotNull BlockState createLegacyBlock(@NotNull FluidState fluidState) {
        return ModBlocks.URINE.get().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(fluidState));
    }

    @Override
    public boolean isSource(@NotNull FluidState fluidState) {
        return false;
    }

    @Override
    public int getAmount(@NotNull FluidState fluidState) {
        return 0;
    }

    @Override
    public boolean isSame(@NotNull Fluid fluid) {
        return fluid == ModFluids.URINE_STILL.get() || fluid == ModFluids.URINE_FLOWING.get();
    }

    public static class Flowing extends UrineFluid {
        @Override
        protected void createFluidStateDefinition(StateDefinition.@NotNull Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(@NotNull FluidState fluidState) {
            return (Integer)fluidState.getValue(LEVEL);
        }

    }

    public static class Source extends UrineFluid {
        @Override
        public int getAmount(@NotNull FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isSource(@NotNull FluidState fluidState) {
            return true;
        }
    }
}