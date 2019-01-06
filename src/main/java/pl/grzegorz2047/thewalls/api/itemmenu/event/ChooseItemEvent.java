package pl.grzegorz2047.thewalls.api.itemmenu.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by grzeg on 08.12.2015.
 */
public class ChooseItemEvent extends Event implements Cancellable {

    private final int slot;
    private Player player;
    private ItemStack clicked;

    public ChooseItemEvent(String title, int size, Inventory inventory, ItemStack clicked, Player p, int slot){
        this.title = title;
        this.size = size;
        this.inventory = inventory;
        this.clicked = clicked;
        this.player = p;
        this.slot = slot;
    }

    private static final HandlerList handlers = new HandlerList();

    private int size;
    private String title;
    private boolean cancelled = false;
    Inventory inventory;

    public Player getPlayer() {
        return player;
    }

    public int getSize() {
        return size;
    }

    public String getTitle() {
        return title;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public ItemStack getClicked() {
        return clicked;
    }

    public int getSlot() {
        return slot;
    }
}

