package io.github.kiber2009.plugin.contentapi.api.recipe;

import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Predicate;

public interface CustomCraftingRecipe extends CustomRecipe, Predicate<@Nullable ItemStack @NonNull []> {}
