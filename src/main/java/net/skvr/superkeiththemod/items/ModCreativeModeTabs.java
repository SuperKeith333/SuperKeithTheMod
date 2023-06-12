package net.skvr.superkeiththemod.items;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.skvr.superkeiththemod.SuperKeithTheMod;

@Mod.EventBusSubscriber(modid = SuperKeithTheMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTabs {
    public static CreativeModeTab SUPERKEITH_TAB;

    @SubscribeEvent
    public static void registerCreativeModeTab(CreativeModeTabEvent.Register event){
        SUPERKEITH_TAB = event.registerCreativeModeTab(new ResourceLocation(SuperKeithTheMod.MOD_ID, "superkeiththemod_tab"),
                builder -> builder.icon(() -> new ItemStack(ModItems.BLANK_CIRCUIT_BOARD.get()))
                        .title(Component.translatable("creativemodetab.superkeiththemodtab")));
    }

}
