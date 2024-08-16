package ua.d1ekart0ffel.irongrowers.screen;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import ua.d1ekart0ffel.irongrowers.registry.MenuTypesRegistry;

public class GrowerBlockMenu extends AbstractContainerMenu {

    private final Container growerBlockContainer;
    private final ContainerData growerBlockData;

    public GrowerBlockMenu(int containerId, Inventory playerInventory, FriendlyByteBuf data) {
        this(containerId, playerInventory, new SimpleContainer(1), new SimpleContainerData(2));
    }

    public GrowerBlockMenu(int containerId, Inventory playerInventory, Container growerBlockContainer, ContainerData growerBlockData) {
        super(MenuTypesRegistry.GROWER_BLOCK_MENU.get(), containerId);
        checkContainerSize(growerBlockContainer, 1);
        checkContainerDataCount(growerBlockData, 2);
        this.growerBlockData = growerBlockData;
        this.growerBlockContainer = growerBlockContainer;

        this.addSlot(new FuelSlot(growerBlockContainer, 0, 80, 48));
        int k;
        for(k = 0; k < 3; ++k) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + k * 9 + 9, 8 + j * 18, 84 + k * 18));
            }
        }

        for(k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

        this.addDataSlots(growerBlockData);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < this.growerBlockContainer.getContainerSize()) {
                if (!this.moveItemStackTo(itemstack1, this.growerBlockContainer.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.growerBlockContainer.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.growerBlockContainer.stillValid(player);
    }

    public int getProgress() {
        return this.growerBlockData.get(0);
    }

    public int getMaxProgress() {
        return this.growerBlockData.get(1);
    }

    static class FuelSlot extends Slot {
        public FuelSlot(Container container, int slot, int x, int y) {
            super(container, slot, x, y);
        }


        public boolean mayPlace(ItemStack stack) {
            return mayPlaceItem(stack);
        }

        public static boolean mayPlaceItem(ItemStack itemStack) {
            return itemStack.is(Items.BONE_MEAL) || itemStack.is(Items.BONE_BLOCK);
        }
    }
}
