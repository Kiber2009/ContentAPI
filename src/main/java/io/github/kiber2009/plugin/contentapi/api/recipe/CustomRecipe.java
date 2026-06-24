package io.github.kiber2009.plugin.contentapi.api.recipe;

import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public interface CustomRecipe {
    @NonNull ItemStack getResult();

    boolean match(final @Nullable ItemStack @NonNull [] matrix);
}
