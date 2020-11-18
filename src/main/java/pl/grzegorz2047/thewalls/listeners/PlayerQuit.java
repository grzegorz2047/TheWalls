package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.grzegorz2047.thewalls.BossBarExtension;
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
    private BossBarExtension bossBarExtension;
    private Voter voter;

    public PlayerQuit(TheWalls plugin, BossBarExtension bossBarExtension, Voter voter) {
        this.plugin = plugin;
        this.bossBarExtension = bossBarExtension;
        this.voter = voter;
        gameData = this.plugin.getGameData();
        scoreboardAPI = plugin.getScoreboardAPI();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        bossBarExtension.removeFromBossBar(p);
        gameData.handlePlayerQuit(p);
        voter.handlePlayerQuit(p.getName());
        //ArenaStatus.setPlayers(Bukkit.getOnlinePlayers().size() - 1);
        e.setQuitMessage("");
    }




}
