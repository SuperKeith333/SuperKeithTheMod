package net.skvr.superkeiththemod.integration;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.skvr.superkeiththemod.SuperKeithTheMod;
import net.skvr.superkeiththemod.recipe.SolderingStationRecipe;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEISuperKeithTheModPlugin implements IModPlugin {

    public static RecipeType<SolderingStationRecipe> SOLDERING_TYPE =
            new RecipeType<>(SolderingStationRecipeCategory.UID, SolderingStationRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(SuperKeithTheMod.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new
                SolderingStationRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<SolderingStationRecipe> recipesInfusing = rm.getAllRecipesFor(SolderingStationRecipe.Type.INSTANCE);
        registration.addRecipes(SOLDERING_TYPE, recipesInfusing);
    }
}
