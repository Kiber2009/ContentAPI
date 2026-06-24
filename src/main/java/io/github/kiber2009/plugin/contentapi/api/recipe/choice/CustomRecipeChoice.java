package io.github.kiber2009.plugin.contentapi.api.recipe.choice;

import org.bukkit.inventory.ItemStack;

public interface CustomRecipeChoice {
    boolean test(final ItemStack stack);
}
