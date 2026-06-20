package io.github.kiber2009.plugin.contentapi.api.item.properties.property;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.components.UseCooldownComponent;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class UseCooldownProperty implements Property<UseCooldownComponent> {
    private final float cooldown;
    private final NamespacedKey group;

    public UseCooldownProperty(final float seconds, final @Nullable NamespacedKey group) {
        cooldown = seconds;
        this.group = group;
    }

    @Override
    @Contract("_ -> param1")
    public @NonNull UseCooldownComponent build(final @NonNull UseCooldownComponent component) {
        component.setCooldownSeconds(cooldown);
        component.setCooldownGroup(group);
        return component;
    }
}
