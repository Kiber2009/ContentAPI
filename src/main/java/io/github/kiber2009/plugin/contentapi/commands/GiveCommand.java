package io.github.kiber2009.plugin.contentapi.commands;

import io.github.kiber2009.plugin.contentapi.api.brigadier.arguments.ContentApiArgumentTypes;
import io.github.kiber2009.plugin.contentapi.api.item.CustomItem;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import org.bukkit.entity.Player;

import java.util.List;

public final class GiveCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return Commands.literal("give")
                .then(Commands.argument("targets", ArgumentTypes.players())
                        .then(Commands.argument("item", ContentApiArgumentTypes.CUSTOM_ITEM)
                                .then(Commands.argument("count", IntegerArgumentType.integer(1))
                                        .executes(ctx -> give(
                                                ctx.getArgument("targets", PlayerSelectorArgumentResolver.class)
                                                        .resolve(ctx.getSource()),
                                                ctx.getArgument("item", CustomItem.class),
                                                IntegerArgumentType.getInteger(ctx, "count"))))
                                .executes(ctx -> give(
                                        ctx.getArgument("targets", PlayerSelectorArgumentResolver.class)
                                                .resolve(ctx.getSource()),
                                        ctx.getArgument("item", CustomItem.class), 1))));
    }

    private static int give(final List<Player> targets, final CustomItem item, final int count) {
        for (final Player target : targets)
            target.getInventory().addItem(item.getItemStack(count));

        return Command.SINGLE_SUCCESS;
    }
}
