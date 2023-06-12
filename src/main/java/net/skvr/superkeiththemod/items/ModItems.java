package net.skvr.superkeiththemod.items;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.skvr.superkeiththemod.SuperKeithTheMod;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS
            = DeferredRegister.create(ForgeRegistries.ITEMS, SuperKeithTheMod.MOD_ID);

    public static final RegistryObject<Item> BLANK_CIRCUIT_BOARD = ITEMS.register("blank_circuit_board",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SOLDER = ITEMS.register("solder",
            () -> new Item(new Item.Properties().durability(50)));
    public static final RegistryObject<Item> USB_PORT = ITEMS.register("usb_port",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SOLDERING_IRON = ITEMS.register("soldering_iron",
            () -> new Item(new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
