package io.github.kiber2009.plugin.contentapi.api.item.action;

import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class EntityInteractAction implements InteractAction {
    private final Entity clickedEntity;

    public EntityInteractAction(final PlayerInteractAtEntityEvent event) {
        clickedEntity = event.getRightClicked();
    }

    public Entity getClickedEntity() {
        return clickedEntity;
    }
}
