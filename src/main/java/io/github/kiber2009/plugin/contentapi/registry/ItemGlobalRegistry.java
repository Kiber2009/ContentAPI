package io.github.kiber2009.plugin.contentapi.registry;

import io.github.kiber2009.plugin.contentapi.api.item.CustomItem;
import io.github.kiber2009.plugin.contentapi.api.persistence.ContentApiPersistentDataType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public final class ItemGlobalRegistry extends GlobalRegistry<CustomItem> {
    ItemGlobalRegistry() {
    }

    @Contract(value = "null -> null", pure = true)
    public @Nullable CustomItem get(final @Nullable ItemStack stack) {
        if (!CustomItem.isCustomItem(stack))
            return null;

        return get(Objects.requireNonNull(stack.getPersistentDataContainer().get(CustomItem.CUSTOM_ITEM_TAG,
                ContentApiPersistentDataType.NAMESPACED_KEY)));
    }
}
