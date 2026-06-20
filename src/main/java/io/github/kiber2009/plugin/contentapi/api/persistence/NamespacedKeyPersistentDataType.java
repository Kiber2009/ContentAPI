package io.github.kiber2009.plugin.contentapi.api.persistence;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

public class NamespacedKeyPersistentDataType implements PersistentDataType<String, NamespacedKey> {
    NamespacedKeyPersistentDataType() {
    }

    @Override
    @Contract(pure = true)
    public @NotNull Class<String> getPrimitiveType() {
        return String.class;
    }

    @Override
    @Contract(pure = true)
    public @NotNull Class<NamespacedKey> getComplexType() {
        return NamespacedKey.class;
    }

    @Override
    @Contract(pure = true)
    public @NonNull String toPrimitive(final @NonNull NamespacedKey complex,
                                       final @NotNull PersistentDataAdapterContext context) {
        return complex.toString();
    }

    @Override
    @Contract(pure = true)
    public @NonNull NamespacedKey fromPrimitive(final @NonNull String primitive,
                                                final @NotNull PersistentDataAdapterContext context) {
        return Objects.requireNonNull(NamespacedKey.fromString(primitive));
    }
}
