package com.srcfur.badhygiene.blocks.custom;

import com.mojang.serialization.MapCodec;
import com.srcfur.badhygiene.api.HygieneAPI;
import com.srcfur.badhygiene.blocks.RegisteredPotty;
import com.srcfur.badhygiene.blocks.entities.AbstractToiletBlockEntity;
import com.srcfur.badhygiene.blocks.entities.ToiletBlockEntity;
import com.srcfur.badhygiene.fluids.ModFluids;
import com.srcfur.badhygiene.fluids.UrineFluid;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractToiletBlock extends BaseEntityBlock {
    public RegisteredPotty<AbstractToiletBlock, AbstractToiletBlockEntity> registration = null;
    private static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    protected AbstractToiletBlock(Properties properties) {
        super(properties);
    }

    public AbstractToiletBlock(Properties properties, RegisteredPotty<AbstractToiletBlock, AbstractToiletBlockEntity> potty){
        super(properties);
        registration = potty;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new ToiletBlockEntity(registration.ENTITY.get(), blockPos, blockState);
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull BlockHitResult blockHitResult) {
        if(HygieneAPI.getBladderLevel(player) > 0 || player.isCreative()){
            AbstractToiletBlockEntity entity = (AbstractToiletBlockEntity) level.getBlockEntity(blockPos);
            if(entity != null){
                if(!level.isClientSide()) {
                    if (entity.fill(new FluidStack(ModFluids.URINE_STILL, HygieneAPI.getBladderToFluidUnits(1)), IFluidHandler.FluidAction.SIMULATE) > 0) {
                        entity.fill(new FluidStack(ModFluids.URINE_STILL, HygieneAPI.getBladderToFluidUnits(1)), IFluidHandler.FluidAction.EXECUTE);
                        HygieneAPI.setBladderLevel(player, Math.clamp(HygieneAPI.getBladderLevel(player) - 1, 0, HygieneAPI.getContinence(player)));
                    } else {
                        //Add thingy to make it visible a toilet is backed up
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.useWithoutItem(blockState, level, blockPos, player, blockHitResult);
    }
}
