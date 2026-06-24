package io.github.kiber2009.plugin.contentapi.registry;

import io.github.kiber2009.plugin.contentapi.api.registry.LocalRegistry;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class GlobalRegistry<T> {
    @SuppressWarnings("StaticInitializerReferencesSubClass")
    public static final ItemGlobalRegistry ITEMS = new ItemGlobalRegistry();

    protected final Map<NamespacedKey, T> map = new HashMap<>();

    protected void registerItem(final @NonNull NamespacedKey key, final @NonNull T value) {
        if (map.containsKey(key))
            throw new IllegalStateException("Duplicate key " + key);
        map.put(key, value);
    }

    public void register(final @NonNull LocalRegistry<? extends T> registry) {
        for (final NamespacedKey key : registry.keySet())
            registerItem(key, Objects.requireNonNull(registry.get(key)));
    }

    @Contract(pure = true)
    public @Nullable T get(final @NonNull NamespacedKey key) {
        return map.get(key);
    }

    @Contract(pure = true)
    public @NonNull Set<NamespacedKey> keySet() {
        return map.keySet();
    }
}
