package io.github.kiber2009.plugin.contentapi.api.item.action;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockInteractAction implements InteractAction {
    private final Block blockClicked;
    private final BlockFace blockFace;
    private final Location interactionPoint;

    public BlockInteractAction(final PlayerInteractEvent event) {
        blockClicked = event.getClickedBlock();
        blockFace = event.getBlockFace();
        interactionPoint = event.getInteractionPoint();
    }

    public Block getBlockClicked() {
        return blockClicked;
    }

    public BlockFace getBlockFace() {
        return blockFace;
    }

    public Location getInteractionPoint() {
        return interactionPoint;
    }
}
