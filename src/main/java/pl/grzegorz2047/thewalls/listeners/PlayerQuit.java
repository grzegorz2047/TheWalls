package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.grzegorz2047.thewalls.BossBarHandler;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.TheWalls;
import pl.grzegorz2047.thewalls.commands.vote.Voter;
import pl.grzegorz2047.thewalls.scoreboard.ScoreboardAPI;

/**
 * Created by grzeg on 11.05.2016.
 */
public class PlayerQuit implements Listener {

    private final TheWalls plugin;
    private final GameData gameData;
    private final ScoreboardAPI scoreboardAPI;
    private BossBarHandler bossBarHandler;
    private Voter voter;

    public PlayerQuit(TheWalls plugin, BossBarHandler bossBarHandler, Voter voter) {
        this.plugin = plugin;
        this.bossBarHandler = bossBarHandler;
        this.voter = voter;
        gameData = this.plugin.getGameData();
        scoreboardAPI = plugin.getScoreboardAPI();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        bossBarHandler.removeFromBossBar(p);
        gameData.handlePlayerQuit(p);
        voter.handlerPlayerQuit(p);
        //ArenaStatus.setPlayers(Bukkit.getOnlinePlayers().size() - 1);
        e.setQuitMessage("");
    }




}
