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
        GameUser user = plugin.getGameData().getGameUsers().get(p.getName());
        String format = plugin.getSettings().get("chat." + user.getRank().toLowerCase());
        String message = e.getMessage();
        message = message.replace('%', ' ');
        if (!user.getRank().equals("Gracz")) {
            message = ChatColor.translateAlternateColorCodes('&', message);
        }
        e.setFormat(format.replace("{DISPLAYNAME}", p.getDisplayName()).replace("{MESSAGE}", message).replace("{LANG}", user.getLanguage()));
        if (plugin.getGameData().getStatus().equals(GameData.GameStatus.INGAME)) {
            e.setCancelled(true);
            if (user.getAssignedTeam() == null) {
                if ((!user.getRank().equals("Gracz") && !user.getRank().equals("Vip") && !user.getRank().equals("Youtube") && !user.getRank().equals("miniYT"))) {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.sendMessage(format.replace("{DISPLAYNAME}",
                                p.getDisplayName()).replace("{MESSAGE}",
                                message).replace("{LANG}",
                                user.getLanguage()));
                    }

                }
                return;
            }
            boolean toAll = false;
            if (!user.getRank().equals("Gracz")) {
                toAll = message.startsWith("!");
            }

            List<String> recipent = plugin.getGameData().getTeams().get(user.getAssignedTeam());
            String formatted = format.replace("{DISPLAYNAME}",
                    p.getDisplayName()).replace("{MESSAGE}",
                    message).replace("{LANG}",
                    user.getLanguage());
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (recipent.contains(pl.getName()) || toAll) {
                    if (toAll) {
                        pl.sendMessage("§7[§bGLOBAL§7] " + formatted.substring(1));
                    } else {
                        pl.sendMessage(formatted);
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