package io.github.kiber2009.plugin.contentapi;

import io.github.kiber2009.plugin.contentapi.api.recipe.CustomCraftingRecipe;
import io.github.kiber2009.plugin.contentapi.registry.recipe.RecipeGlobalRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

final class RecipesManager implements Listener {
    @EventHandler
    private static void onPrepareItemCraft(final PrepareItemCraftEvent event) {
        if (event.isRepair())
            return;

        final CustomCraftingRecipe recipe = RecipeGlobalRegistry.CRAFTING.get(event.getInventory().getMatrix());
        if (recipe != null)
            event.getInventory().setResult(recipe.getResult());
    }
}