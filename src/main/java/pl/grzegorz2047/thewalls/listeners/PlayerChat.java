package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameUser;
import pl.grzegorz2047.thewalls.TheWalls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by grzeg on 07.05.2016.
 */
public class PlayerChat implements Listener {


    private final TheWalls plugin;
    private final HashMap<String, String> settings;
    private final GameData gameData;
    private final HashMap<GameData.GameTeam, ArrayList<String>> teams;
    private final HashMap<String, GameUser> gameUsers;

    public PlayerChat(TheWalls plugin) {
        this.plugin = plugin;
        settings = this.plugin.getSettings();
        gameData = this.plugin.getGameData();
        teams = gameData.getTeams();
        gameUsers = gameData.getGameUsers();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Player p = e.getPlayer();
        String playerName = p.getName();
        GameUser user = gameUsers.get(playerName);
        String userRank = user.getRank();
        String displayName = p.getDisplayName();

        String format = settings.get("chat." + userRank.toLowerCase());
        String message = e.getMessage().replace('%', ' ');
        boolean hasStandardRank = userRank.equals("Gracz");
        boolean toGlobalChat = false;
        if (!hasStandardRank) {
            message = ChatColor.translateAlternateColorCodes('&', message);
            toGlobalChat = message.startsWith("!");
            if(toGlobalChat){
                message = message.substring(1);
            }
        }

        String chatFormat = format.replace("{DISPLAYNAME}", displayName).replace("{MESSAGE}", message);
        String chatFormatLang = chatFormat.replace("{LANG}", user.getLanguage());
        e.setFormat(chatFormatLang);
        GameData.GameStatus status = gameData.getStatus();
        if (status.equals(GameData.GameStatus.INGAME)) {
            e.setCancelled(true);
            boolean isObserver = user.getAssignedTeam() == null;
            if (isObserver) {
                boolean isAdminRank = !hasStandardRank && !hasSpecialVipRank(userRank);
                if (isAdminRank) {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.sendMessage(chatFormatLang);
                    }

                }
                return;
            }

            List<String> recipent = teams.get(user.getAssignedTeam());
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (recipent.contains(pl.getName()) || toGlobalChat) {
                    if (toGlobalChat) {
                        String globalFormat = "§7[§bGLOBAL§7] " + chatFormatLang;
                        pl.sendMessage(globalFormat);
                    } else {
                        pl.sendMessage(chatFormatLang);
                    }
                }
            }
        }
    }

    private boolean hasSpecialVipRank(String userRank) {
        return userRank.equals("Vip") || userRank.equals("Youtube") || !userRank.equals("miniYT");
    }

    @EventHandler
    public void onHint(PlayerChatTabCompleteEvent e) {
        e.getTabCompletions().clear();
    }
}