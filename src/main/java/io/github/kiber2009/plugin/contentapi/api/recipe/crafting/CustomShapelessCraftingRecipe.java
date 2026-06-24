package io.github.kiber2009.plugin.contentapi.api.recipe.crafting;

import io.github.kiber2009.plugin.contentapi.api.recipe.CustomCraftingRecipe;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Predicate;

public class CustomShapelessCraftingRecipe implements CustomCraftingRecipe {
    private final ItemStack result;
    private final Predicate<ItemStack>[] ingredients;

    @SafeVarargs
    public CustomShapelessCraftingRecipe(final @NonNull ItemStack result,
                                         final @NonNull Predicate<@NonNull ItemStack> @NonNull ... ingredients) {
        if (ingredients.length > 9)
            throw new IllegalArgumentException("Too many ingredients");
        this.result = result;
        this.ingredients = ingredients;
    }

    @Override
    @Contract(pure = true)
    public @NonNull ItemStack getResult() {
        return result.clone();
    }

    @Override
    @Contract(pure = true)
    public boolean test(final @Nullable ItemStack @NonNull [] matrix) {
        if (matrix.length < ingredients.length)
            return false;

        final boolean[] used = new boolean[ingredients.length];
        int usedCount = 0;

        for (final ItemStack elem : matrix) {
            if (elem == null)
                continue;

            boolean found = false;
            for (int i = 0; i < ingredients.length; i++)
                if (!used[i] && ingredients[i].test(elem)) {
                    used[i] = true;
                    usedCount++;
                    found = true;
                    break;
                }

            if (!found)
                return false;
        }

        return usedCount == ingredients.length;
    }
}
