package io.github.kiber2009.plugin.contentapi.registry.recipe;

import io.github.kiber2009.plugin.contentapi.api.recipe.CustomCraftingRecipe;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public final class CraftingRecipeGlobalRegistry extends RecipeGlobalRegistry<CustomCraftingRecipe> {
    CraftingRecipeGlobalRegistry() {
    }

    @Contract(pure = true)
    public @Nullable CustomCraftingRecipe get(final @Nullable ItemStack @NonNull [] matrix) {
        for (final CustomCraftingRecipe recipe : map.values())
            if (recipe.test(matrix))
                return recipe;
        return null;
    }
}
