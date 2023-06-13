package net.skvr.superkeiththemod.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.skvr.superkeiththemod.entity.ModTileEnitity;
import net.skvr.superkeiththemod.items.ModItems;
import net.skvr.superkeiththemod.recipe.SolderingStationRecipe;
import net.skvr.superkeiththemod.screen.SolderingStationMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public class SolderingStationTile extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };



    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public SolderingStationTile(BlockPos pos, BlockState state) {
        super(ModTileEnitity.SOLDERING_STATION.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> SolderingStationTile.this.progress;
                    case 1 -> SolderingStationTile.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> SolderingStationTile.this.progress = value;
                    case 1 -> SolderingStationTile.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }
    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T be) {
        SolderingStationTile tile = (SolderingStationTile) be;

        if(level.isClientSide()) {
            return;
        }

        if(hasRecipe(tile)) {
            tile.progress++;
            setChanged(level, pos, state);

            if(tile.progress >= tile.maxProgress) {
                craftItem(tile);
            }
        } else {
            tile.resetProgress();
            setChanged(level, pos, state);
        }

    }
    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(SolderingStationTile pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        assert level != null;
        Optional<SolderingStationRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(SolderingStationRecipe.Type.INSTANCE, inventory, level);



        if(hasRecipe(pEntity)) {
            if (pEntity.itemHandler.getStackInSlot(2).isEmpty()) {
                return;
            } else {
                if (recipe.get().getIngredients().size() > 0) {
                    if(pEntity.itemHandler.getStackInSlot(1).getItem() == ModItems.USB_PORT.get()){
                        pEntity.itemHandler.extractItem(0, 1, false);
                        pEntity.itemHandler.extractItem(1, 1, false);
                    } else {
                        return;
                    }
                } else {
                    pEntity.itemHandler.extractItem(0, 1, false);
                }
                pEntity.itemHandler.getStackInSlot(2).setDamageValue(pEntity.itemHandler.getStackInSlot(2).getDamageValue() + 1);
                pEntity.itemHandler.setStackInSlot(3, new ItemStack(recipe.get().getResultItem().getItem(),
                        pEntity.itemHandler.getStackInSlot(3).getCount() + 1));
                if(pEntity.itemHandler.getStackInSlot(3).getItem() == ModItems.CIRCUIT_BOARD_STAGE_1.get()){
                    pEntity.itemHandler.getStackInSlot(3).setDamageValue(13);
                } else if (pEntity.itemHandler.getStackInSlot(3).getItem() == ModItems.CIRCUIT_BOARD_STAGE_2.get()){
                    pEntity.itemHandler.getStackInSlot(3).setDamageValue(10);
                } else if (pEntity.itemHandler.getStackInSlot(3).getItem() == ModItems.CIRCUIT_BOARD_STAGE_3.get()) {
                    pEntity.itemHandler.getStackInSlot(3).setDamageValue(5);
                }

                pEntity.resetProgress();
            }
        }
    }

    private static boolean hasRecipe(SolderingStationTile entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        assert level != null;
        Optional<SolderingStationRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(SolderingStationRecipe.Type.INSTANCE, inventory, level);



        return recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, recipe.get().getResultItem());
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("Soldering Station");
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }
    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("soldering_station.progress", this.progress);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("soldering_station.progress");
    }
    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        assert this.level != null;
        Containers.dropContents(Objects.requireNonNull(this.level), this.worldPosition, inventory);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player p_39956_) {
        return new SolderingStationMenu(id, inventory, this, this.data);
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack) {
        return inventory.getItem(3).getItem() == stack.getItem() || inventory.getItem(3).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(3).getMaxStackSize() > inventory.getItem(3).getCount();
    }
}
