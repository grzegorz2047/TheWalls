package pl.grzegorz2047.thewalls.api.itemmenu;

import org.bukkit.event.Listener;

/**
 * Created by grzeg on 08.12.2015.
 */
public class Menu implements Listener {


    public Menu(){
    }
/*
    @EventHandler
    void clickEkwipunek(InventoryClickEvent e) {

        ChooseItemEvent event = new ChooseItemEvent(e.getInventory().getTitle(), e.getInventory().getSize(), e.getInventory());
        Bukkit.getServer().getPluginManager().callEvent(event);
        if(event.isCancelled()){
            e.setCancelled(true);
        }


    }*/


}
