package io.github.kiber2009.plugin.contentapi.api.recipe;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;

public interface CustomRecipe {
    @Contract(pure = true)
    @NonNull ItemStack getResult();
}
