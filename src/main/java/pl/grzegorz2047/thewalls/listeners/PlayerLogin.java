package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import pl.grzegorz2047.databaseapi.SQLUser;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.TheWalls;

import java.net.InetAddress;

/**
 * Created by grzeg on 16.05.2016.
 */
public class PlayerLogin implements Listener {

    private final TheWalls plugin;

    public PlayerLogin(TheWalls plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        plugin.getPlayerManager().insertPlayer(p.getName(), "127.0.0.1");
        SQLUser user = plugin.getPlayerManager().getPlayer(p.getName());
        if (!user.getRank().equals("Gracz") && !user.getRank().equals("Vip") && !user.getRank().equals("Youtube") && !user.getRank().equals("miniYT")) {
            e.allow();
            return;
        }
        if (plugin.getGameData().getStatus().equals(GameData.GameStatus.INGAME)) {
            if (user.getRank().equals("Gracz")) {
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.login.notspectator"));
                return;
            }
        } else if (plugin.getGameData().getStatus().equals(GameData.GameStatus.RESTARTING)) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.login.restarting"));
        } else {
            if (user.getRank().equals("Gracz")) {
                if ((Bukkit.getMaxPlayers() - Bukkit.getOnlinePlayers().size()) < 5) {
                    e.disallow(PlayerLoginEvent.Result.KICK_OTHER, plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.login.vipslots"));
                    return;
                }
            }
        }
    }

}
