package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.TheWalls;
import pl.grzegorz2047.thewalls.scoreboard.ScoreboardAPI;

/**
 * Created by grzeg on 11.05.2016.
 */
public class PlayerQuit implements Listener {

    private final TheWalls plugin;
    private final GameData gameData;
    private final ScoreboardAPI scoreboardAPI;

    public PlayerQuit(TheWalls plugin) {
        this.plugin = plugin;
        gameData = this.plugin.getGameData();
        scoreboardAPI = plugin.getScoreboardAPI();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        gameData.handlePlayerQuit(p);
        //ArenaStatus.setPlayers(Bukkit.getOnlinePlayers().size() - 1);
        e.setQuitMessage("");
    }




}
