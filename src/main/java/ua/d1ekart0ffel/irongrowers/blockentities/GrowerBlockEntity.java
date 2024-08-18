package ua.d1ekart0ffel.irongrowers.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import org.openjdk.nashorn.internal.objects.annotations.Getter;
import ua.d1ekart0ffel.irongrowers.registry.BlockEntityRegistry;
import ua.d1ekart0ffel.irongrowers.screen.GrowerBlockMenu;

import java.util.HashMap;
import java.util.Map;

public class GrowerBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {
    private int progress = -1;
    private int maxProgress;
    private static final int[] FUEL_SLOTS = new int[]{0};
    private static final int INVENTORY_SIZE = 1;

    private final ItemStackHandler inventory;
    private final Component customName = Component.literal("Grower Block");

    @Nullable
    protected final ContainerData growerBlockData;

    public GrowerBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.GROWER_BLOCK_ENTITY.get(), pos, blockState);
        this.growerBlockData = createIntArray();
        this.inventory = createHandler();
    }

    public static final Map<Item, Integer> GROWER_FUEL = new HashMap<>() {{
        put(Items.BONE_MEAL, 50);
        put(Items.BONE_BLOCK, 450);
    }};

    private int tickCount;

    public static void serverTick(ServerLevel level, BlockPos pos, BlockState state, GrowerBlockEntity blockEntity, double growthInterval, int radius, int growthChance) {
        growthInterval = growthInterval * 20;
        blockEntity.tickCount++;
        if (blockEntity.tickCount % growthInterval != 0) {
            return;
        }

        boolean mayContinue = false;

         if (blockEntity.progress < 0) {
            ItemStack itemstack = blockEntity.inventory.getStackInSlot(0);
            if (GROWER_FUEL.containsKey(itemstack.getItem())) {
                blockEntity.maxProgress = GROWER_FUEL.get(itemstack.getItem());
                itemstack.shrink(1);
                level.playSound(null, blockEntity.getBlockPos(), SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                blockEntity.progress = blockEntity.maxProgress;
                blockEntity.inventoryChanged();
                blockEntity.setChanged();
            }  else return;
        }

        for (BlockPos blockScanPos : BlockPos.betweenClosed(pos.offset(-radius, 0, -radius), pos.offset(radius, 0, radius))) {
            if (!(blockScanPos != null && level.getRandom().nextInt(100) <= growthChance)) continue;
            BlockState cropstate = level.getBlockState(blockScanPos);

            if (!(cropstate.getBlock() instanceof IGrowerCrop growerCrop)) continue;

            if (growerCrop.ironGrowers$growCrop(level, blockScanPos, cropstate)) {
                level.sendParticles(ParticleTypes.HAPPY_VILLAGER, blockScanPos.getX() + 0.5, blockScanPos.getY() + 0.5, blockScanPos.getZ() + 0.5, 5, 0.25, 0.25, 0.25, 0.1);
                mayContinue = true;
            }
        }
        if (mayContinue) {
            blockEntity.progress--;
            blockEntity.setChanged();
        }
    }

    public NonNullList<ItemStack> getDroppableInventory() {
        NonNullList<ItemStack> drops = NonNullList.create();
        for (int i = 0; i < INVENTORY_SIZE; ++i) {
            drops.add(inventory.getStackInSlot(i));
        }
        return drops;
    }

    @Override
    public Component getDisplayName() {
        return customName;
    }

    @Override
    protected Component getDefaultName() {
        return customName;
    }

    @Getter
    public ItemStackHandler getInventory() {
        return inventory;
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory player) {
        return new GrowerBlockMenu(id, player, this, this.growerBlockData);
    }

    //Data sync for GrowerBlockEntity
    protected void inventoryChanged() {
        super.setChanged();
        if (level != null)
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), Block.UPDATE_ALL);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(INVENTORY_SIZE) {
            @Override
            protected void onContentsChanged(int slot) {
                inventoryChanged();
            }
        };
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("Inventory"));
        progress = tag.getInt("Progress");
        maxProgress = tag.getInt("MaxProgress");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("Progress", progress);
        tag.putInt("MaxProgress", maxProgress);
        tag.put("Inventory", inventory.serializeNBT(registries));
    }

    @Override
    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        this.saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        super.handleUpdateTag(tag, lookupProvider);
    }

    private ContainerData createIntArray() {
        return new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> GrowerBlockEntity.this.progress;
                    case 1 -> GrowerBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> GrowerBlockEntity.this.progress = value;
                    case 1 -> GrowerBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    //Getters & setters
    @Override
    public int getContainerSize() {
        return this.inventory.getSlots();
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        NonNullList<ItemStack> list = NonNullList.create();
        for (int i = 0; i < this.inventory.getSlots(); i++) {
            list.add(this.inventory.getStackInSlot(i));
        }
        return list;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        for (int i = 0; i < items.size(); i++) {
            this.inventory.setStackInSlot(i, items.get(i));
        }
    }

    public void setItem(int index, ItemStack stack) {
        this.inventory.setStackInSlot(index, stack);
        this.setChanged();
    }

    //Place and take grower fuels from different sides
    @Override
    public int[] getSlotsForFace(Direction direction) {
        return FUEL_SLOTS;
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack itemStack) {
        return GROWER_FUEL.containsKey(itemStack.getItem());
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @Nullable Direction direction) {
        return canPlaceItem(i, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
        return direction == Direction.DOWN;
    }
}

