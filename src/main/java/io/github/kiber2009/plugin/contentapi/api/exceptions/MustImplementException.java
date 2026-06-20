package io.github.kiber2009.plugin.contentapi.api.exceptions;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class MustImplementException extends IllegalArgumentException {
    public MustImplementException(final @NonNull Class<?> expected, final @NonNull Class<?> actual) {
        super(message(expected, actual));
    }

    public MustImplementException(final @NonNull Class<?> expected, final @NonNull Class<?> actual,
                                  final @Nullable Throwable cause) {
        super(message(expected, actual), cause);
    }

    private static String message(final @NonNull Class<?> expected, final @NonNull Class<?> actual) {
        return "Value must implement " + expected.getName() + "; got " + actual.getName();
    }
}
