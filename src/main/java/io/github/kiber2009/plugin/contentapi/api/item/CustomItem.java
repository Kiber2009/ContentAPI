package io.github.kiber2009.plugin.contentapi.api.item;

import io.github.kiber2009.plugin.contentapi.ContentApiPlugin;
import io.github.kiber2009.plugin.contentapi.api.item.action.InteractAction;
import io.github.kiber2009.plugin.contentapi.api.item.properties.Properties;
import io.github.kiber2009.plugin.contentapi.api.persistence.ContentApiPersistentDataType;
import io.papermc.paper.persistence.PersistentDataContainerView;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public abstract class CustomItem {
    public static final NamespacedKey CUSTOM_ITEM_TAG =
            new NamespacedKey(ContentApiPlugin.getInstance(), "custom_item");

    private final NamespacedKey id;
    private final Properties properties;

    protected CustomItem(final NamespacedKey id, final Properties properties) {
        this.id = id;
        this.properties = properties;
    }

   @Contract(value = "_ -> new", pure = true)
    public @NonNull ItemStack getItemStack(final int amount) {
        return properties.getItemStack(id, amount);
    }

    @Contract(value = "-> new", pure = true)
    public @NonNull ItemStack getItemStack() {
        return getItemStack(1);
    }

    @Contract(value = "null -> false", pure = true)
    public boolean match(final @Nullable ItemStack stack) {
        if (stack == null)
            return false;

        final PersistentDataContainerView container = stack.getPersistentDataContainer();

        if (!container.has(CUSTOM_ITEM_TAG, PersistentDataType.STRING))
            return false;

        return Objects.equals(container.get(CUSTOM_ITEM_TAG, ContentApiPersistentDataType.NAMESPACED_KEY), id);
    }

    public void onInteract(final ItemStack stack, final Player player, final EquipmentSlot hand,
                           final @Nullable InteractAction action) {
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isCustomItem(final @Nullable ItemStack stack) {
        if (stack == null)
            return false;
        return stack.getPersistentDataContainer().has(CUSTOM_ITEM_TAG, ContentApiPersistentDataType.NAMESPACED_KEY);
    }
}
