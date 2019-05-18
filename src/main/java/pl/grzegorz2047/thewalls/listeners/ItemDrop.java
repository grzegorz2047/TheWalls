package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import pl.grzegorz2047.thewalls.GameData;

/**
 * Created by grzeg on 22.05.2016.
 */
public class ItemDrop implements Listener {


    private final GameData gameData;

    public ItemDrop(GameData gameData) {
        this.gameData = gameData;
    }

    @EventHandler
    private void onDrop(PlayerDropItemEvent e){
        if(!gameData.isStatus(GameData.GameStatus.INGAME)){
            e.setCancelled(true);
        }
    }

}
