package io.github.kiber2009.plugin.contentapi.api.recipe.crafting;

import io.github.kiber2009.plugin.contentapi.api.recipe.CustomCraftingRecipe;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Predicate;

public class CustomShapedCraftingRecipe implements CustomCraftingRecipe {
    private static final byte[][] OFFSETS = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};

    private final ItemStack result;
    private final Predicate<ItemStack>[] matrix;

    public CustomShapedCraftingRecipe(final @NonNull Predicate<@Nullable ItemStack> @NonNull [] matrix,
                                      final @NonNull ItemStack result) {
        if (matrix.length != 4 && matrix.length != 9)
            throw new IllegalArgumentException("Invalid matrix size");
        this.matrix = matrix;
        this.result = result;
    }

    @Override
    @Contract(pure = true)
    public @NonNull ItemStack getResult() {
        return result.clone();
    }

    @Override
    @Contract(pure = true)
    public boolean test(final @Nullable ItemStack @NonNull [] matrix) {
        if (matrix.length == 4 && this.matrix.length == 9)
            return false;

        if (matrix.length == this.matrix.length) {
            for (int i = 0; i < matrix.length; i++)
                if (!this.matrix[i].test(matrix[i]))
                    return false;
            return true;
        }

        for (final byte[] off : OFFSETS) {
            boolean fits = true;

            for (int i = 0; i < 2 && fits; i++)
                for (int j = 0; j < 2; j++)
                    if (!this.matrix[(off[0] + i) * 3 + off[1] + j].test(matrix[i * 2 + j])) {
                        fits = false;
                        break;
                    }

            if (fits)
                return true;
        }
        return false;
    }
}
