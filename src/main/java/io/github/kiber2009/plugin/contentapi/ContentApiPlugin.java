package io.github.kiber2009.plugin.contentapi;

import io.github.kiber2009.plugin.contentapi.commands.GiveCommand;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;

public final class ContentApiPlugin extends JavaPlugin {

    private static ContentApiPlugin instance;

    public static @NonNull ContentApiPlugin getInstance() {
        if (instance == null)
            throw new IllegalStateException("ContentApiPlugin instance is not initialized yet");
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        getServer().getPluginManager().registerEvents(new ItemsManager(), this);
        getServer().getPluginManager().registerEvents(new RecipesManager(), this);

        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands ->
                commands.registrar().register(Commands.literal("contentapi")
                        .then(GiveCommand.build())
                        .build()));
    }
}