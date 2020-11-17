package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.grzegorz2047.thewalls.*;

/**
 * Created by grzegorz2047 on 23.04.2016
 */
public class PlayerJoin implements Listener {

    private final GameData gameData;
    private BossBarExtension bossBarExtension;


    public PlayerJoin(GameData gameData, BossBarExtension bossBarExtension) {
        this.gameData = gameData;

        this.bossBarExtension = bossBarExtension;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (e.getPlayer() == null) {
            return;
        }
        e.setJoinMessage(null);
        Player p = e.getPlayer();
        bossBarExtension.addToBossBar(p);
        gameData.addPlayerToArena(p);
    }


}
