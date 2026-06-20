package io.github.kiber2009.plugin.contentapi.api.collections.bukkit;

import org.bukkit.Keyed;

import java.util.Collection;

public record CollectionBukkitCollection<T extends Keyed>(Collection<T> collection) implements BukkitCollection<T> {}
