package io.github.kiber2009.plugin.contentapi.api.recipe.choice;

import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.Nullable;

public interface CustomRecipeChoice {
    boolean test(final @Nullable ItemStack stack);
}
