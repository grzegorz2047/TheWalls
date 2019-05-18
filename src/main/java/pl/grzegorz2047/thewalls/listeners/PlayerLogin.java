package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import pl.grzegorz2047.databaseapi.DatabaseAPI;
import pl.grzegorz2047.databaseapi.SQLUser;
import pl.grzegorz2047.databaseapi.messages.MessageAPI;
import pl.grzegorz2047.thewalls.GameData;

/**
 * Created by grzeg on 16.05.2016.
 */
public class PlayerLogin implements Listener {

    private final DatabaseAPI playerManager;
    private final GameData gameData;
    private final MessageAPI messageManager;

    public PlayerLogin(DatabaseAPI playerManager, GameData gameData, MessageAPI messageManager) {
        this.playerManager = playerManager;
        this.gameData = gameData;
        this.messageManager = messageManager;
    }

    @EventHandler
    void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();

        playerManager.insertPlayer(p.getName(), "127.0.0.1");
        SQLUser user = playerManager.getPlayer(p.getName());
        String userRank = user.getRank();
        if (!userRank.equals("Gracz") && !userRank.equals("Vip") && !userRank.equals("Youtube") && !userRank.equals("miniYT")) {
            e.allow();
            return;
        }
        String userLanguage = user.getLanguage();
        PlayerLoginEvent.Result kickOtherResult = PlayerLoginEvent.Result.KICK_OTHER;
        if (gameData.isStatus(GameData.GameStatus.INGAME)) {
            if (userRank.equals("Gracz")) {
                e.disallow(kickOtherResult, messageManager.getMessage(userLanguage, "thewalls.login.notspectator"));
                return;
            }
        } else if (gameData.isStatus(GameData.GameStatus.RESTARTING)) {
            e.disallow(kickOtherResult, messageManager.getMessage(userLanguage, "thewalls.login.restarting"));
        } else {
            if (userRank.equals("Gracz")) {
                if ((Bukkit.getMaxPlayers() - Bukkit.getOnlinePlayers().size()) < 5) {
                    e.disallow(kickOtherResult, messageManager.getMessage(userLanguage, "thewalls.login.vipslots"));
                    return;
                }
            }
        }
    }

}
