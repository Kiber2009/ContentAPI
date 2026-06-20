package io.github.kiber2009.plugin.contentapi.api.brigadier.arguments;

import io.github.kiber2009.plugin.contentapi.api.item.CustomItem;
import io.github.kiber2009.plugin.contentapi.registry.GlobalRegistry;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.papermc.paper.command.brigadier.MessageComponentSerializer;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.CustomArgumentType;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CustomItemArgumentType implements CustomArgumentType<CustomItem, NamespacedKey> {
    private static final DynamicCommandExceptionType ERROR_UNKNOWN_ITEM = new DynamicCommandExceptionType(arg ->
            MessageComponentSerializer.message().serialize(Component.translatable("argument.item.id.invalid",
                    List.of(Component.text(arg.toString())))));

    @Override
    public @NonNull CustomItem parse(final @NonNull StringReader reader) throws CommandSyntaxException {
        final NamespacedKey parse = ArgumentTypes.namespacedKey().parse(reader);
        final CustomItem item = GlobalRegistry.ITEMS.get(parse);

        if (item == null)
            throw ERROR_UNKNOWN_ITEM.createWithContext(reader, parse);

        return item;
    }

    @Override
    @Contract(pure = true)
    public @NonNull ArgumentType<NamespacedKey> getNativeType() {
        return ArgumentTypes.namespacedKey();
    }

    @Override
    public <S> @NonNull CompletableFuture<Suggestions> listSuggestions(final @NonNull CommandContext<S> context,
                                                                       final @NonNull SuggestionsBuilder builder) {
        for (final NamespacedKey key : GlobalRegistry.ITEMS.keySet())
            builder.suggest(key.toString());
        return builder.buildFuture();
    }
}
