package com.srcfur.badhygiene.blocks.entities;

import com.srcfur.badhygiene.blocks.ModBlockEntities;
import com.srcfur.badhygiene.fluids.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;

public class ToiletBlockEntity extends AbstractToiletBlockEntity  {

    public ToiletBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }


    @Override
    public int getTanks() {
        return 1;
    }

    @Override
    public int getTankCapacity(int i) {
        return 650;
    }
}
