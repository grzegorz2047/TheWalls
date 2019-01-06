package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.grzegorz2047.databaseapi.SQLUser;
import pl.grzegorz2047.serversmanagement.ArenaStatus;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameUser;
import pl.grzegorz2047.thewalls.TheWalls;
import pl.grzegorz2047.thewalls.permissions.PermissionAttacher;

import java.util.HashMap;

/**
 * Created by grzeg on 11.05.2016.
 */
public class PlayerQuit implements Listener {

    private final TheWalls plugin;

    public PlayerQuit(TheWalls plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        GameUser user = plugin.getGameData().getGameUsers().get(p.getName());
        //e.setQuitMessage(plugin.getMessageManager().getMessage(user.getLanguage(),"thewalls.msg.quit"));
        plugin.getGameData().makePlayerSpectator(user, p, plugin.getGameData().getWorldManagement().getLoadedWorld().getName());
        plugin.getGameData().getGameUsers().remove(p.getName());
        if (plugin.getGameData().getStatus().equals(GameData.GameStatus.WAITING)) {
            plugin.getGameData().checkToStart();
            for (Player pl : Bukkit.getOnlinePlayers()) {
                plugin.getScoreboardAPI().updateDisplayName(0, pl);
            }
        } else if (plugin.getGameData().getStatus().equals(GameData.GameStatus.INGAME)) {
            if (plugin.getGameData().getGameUsers().size() == 0) {
                plugin.getGameData().restartGame();
            }
            plugin.getGameData().checkWinners();
        }
        //ArenaStatus.setPlayers(Bukkit.getOnlinePlayers().size() - 1);
        e.setQuitMessage("");
    }


}
