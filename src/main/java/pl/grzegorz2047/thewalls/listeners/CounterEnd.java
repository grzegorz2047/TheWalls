package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import pl.grzegorz2047.databaseapi.shop.Item;
import pl.grzegorz2047.databaseapi.shop.Transaction;
import pl.grzegorz2047.serversmanagement.ArenaStatus;
import pl.grzegorz2047.thewalls.Counter;
import pl.grzegorz2047.thewalls.CounterEndEvent;
import pl.grzegorz2047.thewalls.GameUser;
import pl.grzegorz2047.thewalls.TheWalls;
import pl.grzegorz2047.thewalls.api.util.BungeeUtil;
import pl.grzegorz2047.thewalls.playerclass.ClassManager;

/**
 * Created by grzeg on 17.05.2016.
 */
public class CounterEnd implements Listener {

    private final TheWalls plugin;

    public CounterEnd(TheWalls plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCounterEnd(CounterEndEvent e) {
        Counter.CounterStatus status = e.getStatus();
        if (e.getStatus().equals(Counter.CounterStatus.COUNTINGTOSTART)) {
            plugin.getGameData().startGame();
            return;
        }
        if (status.equals(Counter.CounterStatus.COUNTINGTODROPWALLS)) {
            plugin.getGameData().startFight();
            return;
        }
        if (status.equals(Counter.CounterStatus.COUNTINGTODM)) {
            plugin.getGameData().startDeathMatch();
            return;
        }
        if (status.equals(Counter.CounterStatus.DEATHMATCH)) {
            setNobodyWin();
            return;
        }
    }

    private void setNobodyWin() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            GameUser user = plugin.getGameData().getGameUsers().get(p.getName());
            p.sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.nowinners"));
        }
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    //nikt nie wygral
                    BungeeUtil.changeServer(plugin, p, "Lobby1");
                }
                plugin.getGameData().restartGame();
            }
        }, 20 * 4);
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                plugin.getGameData().restartGame();
            }
        }, 20 * 8);
        return;
    }

}
