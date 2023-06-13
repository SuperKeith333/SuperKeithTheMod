package net.skvr.superkeiththemod.integration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.common.Mod;
import net.skvr.superkeiththemod.SuperKeithTheMod;
import net.skvr.superkeiththemod.block.ModBlocks;
import net.skvr.superkeiththemod.items.ModItems;
import net.skvr.superkeiththemod.recipe.SolderingStationRecipe;

import java.util.Optional;

public class SolderingStationRecipeCategory implements IRecipeCategory<SolderingStationRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(SuperKeithTheMod.MOD_ID, "soldering");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(SuperKeithTheMod.MOD_ID, "textures/gui/soldering_station.png");

    private final IDrawable background;
    private final IDrawable icon;
    public SolderingStationRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.SOLDERING_STATION.get()));
    }
    @Override
    public RecipeType<SolderingStationRecipe> getRecipeType() {
        return JEISuperKeithTheModPlugin.SOLDERING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Soldering Station");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }



    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SolderingStationRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 7, 35).addIngredients(recipe.getIngredients().get(0));
        if(recipe.getResultItem().getItem() == ModItems.CIRCUIT_BOARD_STAGE_1.get() ||
                recipe.getResultItem().getItem() == ModItems.CIRCUIT_BOARD_STAGE_2.get() ||
                recipe.getResultItem().getItem() == ModItems.CIRCUIT_BOARD_STAGE_3.get()){
            builder.addSlot(RecipeIngredientRole.INPUT, 33, 34).addItemStack(ModItems.USB_PORT.get().getDefaultInstance());
        }
        builder.addSlot(RecipeIngredientRole.INPUT, 56, 35).addItemStack(ModItems.SOLDER.get().getDefaultInstance());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 116, 35).addItemStack(recipe.getResultItem());
    }
}
