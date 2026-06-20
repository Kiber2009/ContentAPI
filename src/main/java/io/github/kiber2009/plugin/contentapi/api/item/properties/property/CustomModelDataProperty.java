package io.github.kiber2009.plugin.contentapi.api.item.properties.property;

import org.bukkit.Color;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class CustomModelDataProperty implements Property<CustomModelDataComponent> {
    private final List<Float> floats;
    private final List<Boolean> flags;
    private final List<String> strings;
    private final List<Color> colors;

    public CustomModelDataProperty(final @NonNull List<Float> floats, final @NonNull List<Boolean> flags,
                                   final @NonNull List<String> strings, final @NonNull List<Color> colors) {
        this.floats = floats;
        this.flags = flags;
        this.strings = strings;
        this.colors = colors;
    }

    @Override
    @Contract("_ -> param1")
    public @NonNull CustomModelDataComponent build(final @NonNull CustomModelDataComponent component) {
        component.setFloats(floats);
        component.setFlags(flags);
        component.setStrings(strings);
        component.setColors(colors);
        return component;
    }

    public static class Builder {
        private List<Float> floats = List.of();
        private List<Boolean> flags = List.of();
        private List<String> strings = List.of();
        private List<Color> colors = List.of();

        @Contract("_ -> this")
        public @NonNull Builder floats(final @NonNull List<Float> floats) {
            this.floats = floats;
            return this;
        }

        @Contract("_ -> this")
        public @NonNull Builder flags(final @NonNull List<Boolean> flags) {
            this.flags = flags;
            return this;
        }

        @Contract("_ -> this")
        public @NonNull Builder strings(final @NonNull List<String> strings) {
            this.strings = strings;
            return this;
        }

        @Contract("_ -> this")
        public @NonNull Builder colors(final @NonNull List<Color> colors) {
            this.colors = colors;
            return this;
        }

        @Contract(value = "-> new", pure = true)
        private @NonNull CustomModelDataProperty build() {
            return new CustomModelDataProperty(floats, flags, strings, colors);
        }
    }
}
