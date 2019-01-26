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
import java.util.Map;

/**
 * Created by grzeg on 07.05.2016.
 */
public class PlayerChat implements Listener {


    private final TheWalls plugin;

    public PlayerChat(TheWalls plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (e.isCancelled()) {
            return;
        }
        Player p = e.getPlayer();
        GameData gameData = plugin.getGameData();
        String playerName = p.getName();
        GameUser user = gameData.getGameUsers().get(playerName);
        String userRank = user.getRank();
        String displayName = p.getDisplayName();

        HashMap<String, String> settings = plugin.getSettings();
        String format = settings.get("chat." + userRank.toLowerCase());
        String message = e.getMessage();
        message = message.replace('%', ' ');
        if (!userRank.equals("Gracz")) {
            message = ChatColor.translateAlternateColorCodes('&', message);
        }
        String chatFormat = format.replace("{DISPLAYNAME}", displayName).replace("{MESSAGE}", message);
        String chatFormatLang = chatFormat.replace("{LANG}",
                user.getLanguage());
        e.setFormat(chatFormatLang);
        if (gameData.getStatus().equals(GameData.GameStatus.INGAME)) {
            e.setCancelled(true);
            if (user.getAssignedTeam() == null) {
                if ((!userRank.equals("Gracz") && !userRank.equals("Vip") && !userRank.equals("Youtube") && !userRank.equals("miniYT"))) {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.sendMessage(chatFormatLang);
                    }

                }
                return;
            }
            boolean toAll = false;
            if (!userRank.equals("Gracz")) {
                toAll = message.startsWith("!");
            }

            List<String> recipent = gameData.getTeams().get(user.getAssignedTeam());
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (recipent.contains(pl.getName()) || toAll) {
                    if (toAll) {
                        String globalFormat = "§7[§bGLOBAL§7] " + chatFormatLang.substring(1);
                        pl.sendMessage(globalFormat);
                    } else {
                        pl.sendMessage(chatFormatLang);
                    }
                }
            }


        }
    }

    @EventHandler
    public void onHint(PlayerChatTabCompleteEvent e) {
        e.getTabCompletions().clear();
    }
}