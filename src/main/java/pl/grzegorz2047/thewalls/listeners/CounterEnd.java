package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.grzegorz2047.databaseapi.messages.MessageAPI;
import pl.grzegorz2047.thewalls.*;
import pl.grzegorz2047.thewalls.playerclass.ClassManager;
import pl.grzegorz2047.thewalls.scoreboard.ScoreboardAPI;

/**
 * Created by grzeg on 17.05.2016.
 */
public class CounterEnd implements Listener {

    private final TheWalls plugin;
    private final ScoreboardAPI scoreboardAPI;
    private final GameData gameData;
    private final MessageAPI messageManager;
    private final ClassManager classManager;

    public CounterEnd(TheWalls plugin, ClassManager classManager) {
        this.plugin = plugin;
        scoreboardAPI = plugin.getScoreboardAPI();
        gameData = plugin.getGameData();
        messageManager = plugin.getMessageManager();
        this.classManager = classManager;

    }

    @EventHandler
    public void onCounterEnd(CounterEndEvent e) {
        Counter.CounterStatus status = e.getStatus();
        if (e.getStatus().equals(Counter.CounterStatus.VOTED_COUNTING_TO_START)) {
            gameData.forceStartGame(scoreboardAPI, classManager);
            return;
        }if (e.getStatus().equals(Counter.CounterStatus.COUNTINGTOSTART)) {
            gameData.startGame(scoreboardAPI, classManager);
            return;
        }
        if (status.equals(Counter.CounterStatus.COUNTINGTODROPWALLS)) {
            gameData.startFight();
            return;
        }
        if (status.equals(Counter.CounterStatus.COUNTINGTODM)) {
            gameData.startDeathMatch();
            return;
        }
        if (status.equals(Counter.CounterStatus.DEATHMATCH)) {
            gameData.restartGame("thewalls.nowinners");
            return;
        }
    }



}
