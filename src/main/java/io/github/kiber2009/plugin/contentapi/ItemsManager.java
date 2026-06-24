package io.github.kiber2009.plugin.contentapi;

import io.github.kiber2009.plugin.contentapi.api.item.CustomItem;
import io.github.kiber2009.plugin.contentapi.api.item.action.BlockInteractAction;
import io.github.kiber2009.plugin.contentapi.api.item.action.EntityInteractAction;
import io.github.kiber2009.plugin.contentapi.registry.GlobalRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

final class ItemsManager implements Listener {
    @EventHandler
    private static void onPlayerInteract(final PlayerInteractEvent event) {
        if (!event.getAction().isRightClick())
            return;

        final ItemStack stack = event.getItem();
        if (stack == null)
            return;

        final CustomItem item = GlobalRegistry.ITEMS.get(stack);
        if (item == null)
            return;

        item.onInteract(stack, event.getPlayer(), event.getHand(),
                event.getAction() == Action.RIGHT_CLICK_AIR ? null : new BlockInteractAction(event));
    }

    @EventHandler
    private static void onPlayerInteractEntity(final PlayerInteractAtEntityEvent event) {
        final ItemStack stack = event.getPlayer().getInventory().getItem(event.getHand());

        final CustomItem item = GlobalRegistry.ITEMS.get(stack);
        if (item == null)
            return;

        item.onInteract(stack, event.getPlayer(), event.getHand(), new EntityInteractAction(event));
    }
}
