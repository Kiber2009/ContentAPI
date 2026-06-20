package io.github.kiber2009.plugin.contentapi.api.item.properties.property;

import org.jspecify.annotations.NonNull;

public interface Property<T> {
    @NonNull T build(final @NonNull T component);
}
