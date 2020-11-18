package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameUser;
import pl.grzegorz2047.thewalls.GameUsers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by grzeg on 07.05.2016.
 */
public class PlayerChat implements Listener {


    private final HashMap<String, String> settings;
    private final GameData gameData;
    private GameUsers gameUsers;


    public PlayerChat(HashMap<String, String> settings, GameData gameData, GameUsers gameUsers) {
        this.settings = settings;
        this.gameData = gameData;
        this.gameUsers = gameUsers;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if(CommandSanitizer.isDisallowedCommand(e.getMessage())) {
            e.setCancelled(true);
        }
    }



    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Player p = e.getPlayer();
        String playerName = p.getName();
        GameUser user = gameUsers.getGameUser(playerName);
        String userRank = user.getRank();
        String displayName = p.getDisplayName();

        String format = settings.get("chat." + userRank.toLowerCase());
        String message = e.getMessage().replace('%', ' ');
        boolean hasStandardRank = userRank.equals("Gracz");
        boolean toGlobalChat = false;
        if (!hasStandardRank) {
            message = ChatColor.translateAlternateColorCodes('&', message);
            toGlobalChat = message.startsWith("!");
            if (toGlobalChat) {
                message = message.substring(1);
            }
        }

        String chatFormat = format.replace("{DISPLAYNAME}", displayName).replace("{MESSAGE}", message);
        String chatFormatLang = chatFormat.replace("{LANG}", user.getLanguage());
        e.setFormat(chatFormatLang);
        if (gameData.isStatus(GameData.GameStatus.INGAME)) {
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

            List<String> recipent = gameData.getTeam(user.getAssignedTeam());
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