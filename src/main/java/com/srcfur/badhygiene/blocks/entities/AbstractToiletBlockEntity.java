package com.srcfur.badhygiene.blocks.entities;

import com.srcfur.badhygiene.blocks.ModBlockEntities;
import com.srcfur.badhygiene.fluids.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.WaterFluid;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractToiletBlockEntity extends BlockEntity implements IFluidHandler {
    FluidStack fluidInToilet;
    public AbstractToiletBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        fluidInToilet = new FluidStack(ModFluids.URINE_STILL.get(), 0);
    }


    public @NotNull FluidStack getFluidInTank(int side){
        return fluidInToilet;
    }

    /**
     * @param resource FluidStack attempting to fill the tank.
     * @param action   If SIMULATE, the fill will only be simulated.
     * @return Amount of fluid that was accepted (or would be, if simulated) by the tank.
     */
    public int fill(FluidStack resource, FluidAction action){
        int pull = Math.max(Math.min(getTankCapacity(0) - getFluidInTank(0).getAmount(), resource.getAmount()), 0);
        if(action.execute()) {
            getFluidInTank(0).setAmount(getFluidInTank(0).getAmount() + pull);
        }
        return pull;
    }
    public @NotNull FluidStack drain(int maxDrain, FluidAction action){
        int todrain = Math.min(maxDrain, getFluidInTank(0).getAmount());
        if(action.execute()){
            getFluidInTank(0).setAmount(getFluidInTank(0).getAmount() - todrain);
        }
        return new FluidStack(ModFluids.URINE_STILL.get(), todrain);
    }

    public @NotNull FluidStack drain(FluidStack resource, @NotNull FluidAction action){
        return drain(resource.getAmount(), action);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("urine", fluidInToilet.getAmount());
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        fluidInToilet = new FluidStack(ModFluids.URINE_STILL.get(), tag.getInt("urine"));
    }

    public boolean canBeScoopedOut(ItemStack item){
        return false;
    }

    @Override
    public boolean isFluidValid(int i, FluidStack fluidStack) {
        return fluidStack.getFluid() == ModFluids.URINE_STILL.get();
    }
}
