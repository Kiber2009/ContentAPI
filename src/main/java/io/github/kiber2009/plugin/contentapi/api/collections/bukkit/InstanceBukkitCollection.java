package io.github.kiber2009.plugin.contentapi.api.collections.bukkit;

import org.bukkit.Keyed;

public record InstanceBukkitCollection<T extends Keyed>(T instance) implements BukkitCollection<T> {}
