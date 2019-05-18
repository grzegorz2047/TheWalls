package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.TheWalls;

/**
 * Created by grzeg on 22.05.2016.
 */
public class ItemDrop implements Listener {


    private final TheWalls plugin;
    private final GameData gameData;

    public ItemDrop(TheWalls plugin) {
        this.plugin = plugin;
        gameData = plugin.getGameData();
    }

    @EventHandler
    private void onDrop(PlayerDropItemEvent e){
        if(!gameData.isStatus(GameData.GameStatus.INGAME)){
            e.setCancelled(true);
        }
    }

}
