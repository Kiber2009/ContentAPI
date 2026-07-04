package io.github.kiber2009.plugin.contentapi.api.recipe.choice;

import io.github.kiber2009.plugin.contentapi.api.item.CustomItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Predicate;

@Deprecated(forRemoval = true, since = "1.2.0")
public class CustomItemRecipeChoice implements Predicate<@Nullable ItemStack> {
    private final CustomItem item;

    public CustomItemRecipeChoice(final @NonNull CustomItem item) {
        this.item = item;
    }

    @Override
    @Contract(value = "null -> false", pure = true)
    public boolean test(final @Nullable ItemStack stack) {
        return item.test(stack);
    }
}
