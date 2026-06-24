package io.github.kiber2009.plugin.contentapi.registry.recipe;

import io.github.kiber2009.plugin.contentapi.api.recipe.CustomRecipe;
import io.github.kiber2009.plugin.contentapi.registry.GlobalRegistry;

public sealed class RecipeGlobalRegistry<T extends CustomRecipe> extends GlobalRegistry<T>
        permits CraftingRecipeGlobalRegistry {
    @SuppressWarnings("StaticInitializerReferencesSubClass")
    public static final CraftingRecipeGlobalRegistry CRAFTING = new CraftingRecipeGlobalRegistry();

    RecipeGlobalRegistry() {
    }
}
