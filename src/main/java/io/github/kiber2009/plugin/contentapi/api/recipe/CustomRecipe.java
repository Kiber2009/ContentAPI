package io.github.kiber2009.plugin.contentapi.api.recipe;

import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;

public interface CustomRecipe {
    @NonNull ItemStack getResult();
}
