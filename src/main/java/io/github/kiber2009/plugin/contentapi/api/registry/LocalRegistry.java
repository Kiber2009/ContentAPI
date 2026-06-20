package io.github.kiber2009.plugin.contentapi.api.registry;

import io.github.kiber2009.plugin.contentapi.api.exceptions.MustImplementException;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public class LocalRegistry<T> {
    private final HashMap<NamespacedKey, T> map = new HashMap<>();

    @Contract("_, _ -> param2")
    public <V extends T> @NonNull V register(final @NonNull NamespacedKey key, final @NonNull V value) {
        if (map.containsKey(key))
            throw new IllegalArgumentException(key.toString());
        map.put(Objects.requireNonNull(key), Objects.requireNonNull(value));
        return value;
    }

    public <V extends T> @NonNull V register(final @NonNull NamespacedKey key,
                                             final @NonNull Function<@NonNull NamespacedKey, @NonNull V> value) {
        return register(key, value.apply(key));
    }

    public <V extends T> @NonNull V register(final @NonNull V value) {
        if (!(value instanceof final Keyed keyed))
            throw new MustImplementException(Keyed.class, value.getClass());
        return register(keyed.getKey(), value);
    }

    public <V extends T> @NonNull V register(final @NonNull Supplier<@NonNull V> value) {
        return register(value.get());
    }

    @Contract(pure = true)
    public @Nullable T get(final @NonNull NamespacedKey key) {
        return map.get(Objects.requireNonNull(key));
    }

    @Contract(pure = true)
    public @NonNull Set<NamespacedKey> keySet() {
        return map.keySet();
    }
}
