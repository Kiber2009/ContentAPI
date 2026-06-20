package io.github.kiber2009.plugin.contentapi.api.collections.bukkit;

import org.bukkit.Keyed;
import org.bukkit.Tag;

public record TagBukkitCollection<T extends Keyed>(Tag<T> tag) implements BukkitCollection<T> {}
