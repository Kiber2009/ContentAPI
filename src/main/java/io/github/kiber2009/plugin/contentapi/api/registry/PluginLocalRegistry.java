package io.github.kiber2009.plugin.contentapi.api.registry;

import io.github.kiber2009.plugin.contentapi.api.exceptions.MustImplementException;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

public class PluginLocalRegistry<T> extends LocalRegistry<T> {
    private final Plugin plugin;

    public PluginLocalRegistry(final @NonNull Plugin plugin) {
        this.plugin = plugin;
    }

    @Contract("_, _ -> param2")
    public <V extends T> @NonNull V register(final @NonNull String key, final @NonNull V value) {
        return register(new NamespacedKey(plugin, key), value);
    }

    public <V extends T> @NonNull V register(final @NonNull String key,
                                             final @NonNull Function<@NonNull NamespacedKey, @NonNull V> value) {
        return register(new NamespacedKey(plugin, key), value);
    }

    @Contract("_, _ -> param2")
    public <V extends T> @NonNull V registerListener(final @NonNull NamespacedKey key, final @NonNull V value) {
        if (!(value instanceof final Listener listener))
            throw new MustImplementException(Listener.class, value.getClass());

        final V register = register(key, value);
        Bukkit.getPluginManager().registerEvents(listener, plugin);
        return register;
    }

    @Contract("_, _ -> param2")
    public <V extends T> @NonNull V registerListener(final @NonNull String key, final @NonNull V value) {
        return registerListener(new NamespacedKey(plugin, key), value);
    }

    public <V extends T> @NonNull V registerListener(final @NonNull NamespacedKey key,
                                                     final @NonNull Function<@NonNull NamespacedKey, @NonNull V> value) {
        return registerListener(key, value.apply(key));
    }

    public <V extends T> @NonNull V registerListener(final @NonNull String key,
                                                     final @NonNull Function<@NonNull NamespacedKey, @NonNull V> value) {
        return registerListener(new NamespacedKey(plugin, key), value);
    }

    public <V extends T> @NonNull V registerListener(final @NonNull V value) {
        if (!(value instanceof final Listener listener))
            throw new MustImplementException(Listener.class, value.getClass());

        final V register = register(value);
        Bukkit.getPluginManager().registerEvents(listener, plugin);
        return register;
    }

    public <V extends T> @NonNull V registerListener(final @NonNull Supplier<@NonNull V> value) {
        return registerListener(value.get());
    }

    @Contract(pure = true)
    public @Nullable T get(final @NonNull String key) {
        return get(new NamespacedKey(plugin, key));
    }
}
