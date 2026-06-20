package io.github.kiber2009.plugin.contentapi.api.collections.bukkit;

import org.bukkit.Keyed;
import org.bukkit.Tag;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

public sealed interface BukkitCollection<T extends Keyed> permits CollectionBukkitCollection, InstanceBukkitCollection,
        TagBukkitCollection {
    @Contract("_ -> new")
    static <T extends Keyed> @NonNull BukkitCollection<T> ofInstance(final T instance) {
        return new InstanceBukkitCollection<>(instance);
    }

    @Contract("_ -> new")
    static <T extends Keyed> @NonNull BukkitCollection<T> ofCollection(final Collection<T> collection) {
        return new CollectionBukkitCollection<>(collection);
    }

    @Contract("_ -> new")
    static <T extends Keyed> @NonNull BukkitCollection<T> ofTag(final Tag<T> tag) {
        return new TagBukkitCollection<>(tag);
    }

    default void accept(final @NonNull Consumer<Collection<T>> collection, final @NonNull Consumer<Tag<T>> tag,
                       final @NonNull Consumer<T> instance) {
        switch (this) {
            case InstanceBukkitCollection<T>(final T i) -> instance.accept(i);
            case CollectionBukkitCollection<T>(final Collection<T> c) -> collection.accept(c);
            case TagBukkitCollection<T>(final Tag<T> t) -> tag.accept(t);
            default -> throw new UnsupportedOperationException();
        }
    }

    default <R> R apply(final @NonNull Function<Collection<T>, R> collection, final @NonNull Function<Tag<T>, R> tag,
                        final @NonNull Function<T, R> instance) {
        return switch (this) {
            case InstanceBukkitCollection<T>(final T i) -> instance.apply(i);
            case CollectionBukkitCollection<T>(final Collection<T> c) -> collection.apply(c);
            case TagBukkitCollection<T>(final Tag<T> t) -> tag.apply(t);
        };
    }
}
