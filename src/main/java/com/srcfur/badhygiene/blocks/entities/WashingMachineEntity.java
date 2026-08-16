package com.srcfur.badhygiene.blocks.entities;

import com.srcfur.badhygiene.BadHygiene;
import com.srcfur.badhygiene.blocks.ModBlockEntities;
import com.srcfur.badhygiene.data.HygieneDataTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

import java.util.ArrayList;
import java.util.List;

public class WashingMachineEntity extends BlockEntity implements IItemHandlerModifiable {
    public WashingMachineEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.WASHING_MACHINE_ENTITY.get(), pos, blockState);
        for (int i = 0; i < getSlots(); i++) {
            INVENTORY.add(ItemStack.EMPTY);
        }
    }

    //0 is used for fuel :3
    public List<ItemStack> INVENTORY = new ArrayList<ItemStack>();
    public int WASH_TIME = 0;

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return (i < 0 || i >= getSlots()) ? ItemStack.EMPTY : INVENTORY.get(i);
    }

    @Override
    public ItemStack insertItem(int i, ItemStack itemStack, boolean b) {
        if(!isItemValid(i, itemStack)) return itemStack;
        if(!b) INVENTORY.set(i, itemStack);
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack extractItem(int i, int i1, boolean b) {
        if(i < 0 || i >= getSlots()) return ItemStack.EMPTY;
        ItemStack give = new ItemStack(INVENTORY.get(i).getItem(), Math.min(i1, INVENTORY.get(i).getCount()));
        if(!b) INVENTORY.get(i).setCount(INVENTORY.get(i).getCount() - give.getCount());
        return give;
    }

    @Override
    public int getSlotLimit(int i) {
        return i == 0 ? INVENTORY.getFirst().getMaxStackSize() : 1;
    }

    @Override
    public boolean isItemValid(int i, ItemStack itemStack) {
        return !(i < 0 || i >= getSlots())
                || (!itemStack.is(ItemTags.COALS) && i == 0)
                || (!itemStack.is(ItemTags.create(ResourceLocation.fromNamespaceAndPath(BadHygiene.MODID, "washable"))) && i > 0)
                || (INVENTORY.get(i) != ItemStack.EMPTY);
    }

    @Override
    public void setStackInSlot(int i, ItemStack itemStack) {
        INVENTORY.set(i, itemStack);
    }

    private List<ItemStack> getSoiledStacks(){
        List<ItemStack> returnable = new ArrayList<>();
        for(int i = 1; i < getSlots(); i++){ if(getStackInSlot(i).getOrDefault(HygieneDataTypes.HYGIENE_SOILED_CLOTHING, false)) returnable.add(getStackInSlot(i)); }
        return returnable;
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, WashingMachineEntity washingMachineEntity)
    {
        List<ItemStack> remaining = washingMachineEntity.getSoiledStacks();
        if(remaining.isEmpty()) return;
        if(washingMachineEntity.getStackInSlot(0).getCount() == 0) return;
        washingMachineEntity.WASH_TIME++;
        if(washingMachineEntity.WASH_TIME < 100) return;
        washingMachineEntity.getStackInSlot(0).setCount(washingMachineEntity.getStackInSlot(0).getCount() - 1);
        remaining.getFirst().set(HygieneDataTypes.HYGIENE_SOILED_CLOTHING, false);
        washingMachineEntity.WASH_TIME = 0;
    }
}
