package com.srcfur.badhygiene.client;

import com.srcfur.badhygiene.blocks.entities.WashingMachineEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public class WashingMachineMenu extends AbstractContainerMenu {
    public WashingMachineMenu(int containerId, Inventory playerInventory, IItemHandler entity) {
        super(HygieneScreens.WASHING_MACHINE_MENU.get(), containerId);
        for(int i = 0; i < 9; i++){
            this.addSlot(new Slot(playerInventory, i, 8 + 18 * (i % 9), 141));
        }
        for(int i = 0; i < 27; i++){
            this.addSlot(new Slot(playerInventory, i + 9, 8 + 18 * (i % 9), 84 + 18 * (i / 9)));
        }
        this.addSlot(new SlotItemHandler(entity,0, 62, 35));
        for(int i = 0; i < 8; i++){
            this.addSlot(new SlotItemHandler(entity,i + 1, 8 + (18 * (i / 4)), 8 + 18 * (i % 4)));
        }
    }
    public WashingMachineMenu(int id, Inventory plr){
        this(id, plr, new ItemStackHandler(9));
    }

    @Override
    protected boolean moveItemStackTo(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        return super.moveItemStackTo(stack, startIndex, endIndex, reverseDirection);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
