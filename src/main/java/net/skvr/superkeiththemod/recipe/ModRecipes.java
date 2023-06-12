package net.skvr.superkeiththemod.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.skvr.superkeiththemod.SuperKeithTheMod;

public class ModRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, SuperKeithTheMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<SolderingStationRecipe>> SOLDERING_SERIALIZER =
            SERIALIZERS.register("soldering", () -> SolderingStationRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
