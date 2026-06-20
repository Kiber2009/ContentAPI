package io.github.kiber2009.plugin.contentapi.api.brigadier.arguments;

import com.mojang.brigadier.arguments.ArgumentType;
import io.github.kiber2009.plugin.contentapi.api.item.CustomItem;

public final class ContentApiArgumentTypes {
    public static final ArgumentType<CustomItem> CUSTOM_ITEM = new CustomItemArgumentType();
}
