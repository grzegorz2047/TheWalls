package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import pl.grzegorz2047.thewalls.TheWalls;
import pl.grzegorz2047.thewalls.api.itemmenu.event.ChooseItemEvent;

/**
 * Created by grzeg on 21.05.2016.
 */
public class InventoryClick implements Listener {


    private final TheWalls plugin;

    public InventoryClick(TheWalls plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    void clickEkwipunek(InventoryClickEvent e) {
        Inventory inventory = e.getInventory();
        ChooseItemEvent event = new ChooseItemEvent(inventory.getTitle(), inventory.getSize(), inventory, e.getCurrentItem(), (Player) e.getWhoClicked(), e.getSlot());
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            e.setCancelled(true);
        }


    }
}
