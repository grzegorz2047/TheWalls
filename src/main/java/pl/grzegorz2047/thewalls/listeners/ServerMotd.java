package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import pl.grzegorz2047.thewalls.Counter;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.api.util.TimeUtil;

public class ServerMotd implements Listener {

    private final GameData gameData;
    private Counter counter;

    public ServerMotd(GameData gameData) {
        this.gameData = gameData;
        counter = gameData.getCounter();
        System.out.println("Register motd");
    }

    @EventHandler
    public void onServerPing(ServerListPingEvent serverListPingEvent) {
        serverListPingEvent.setMotd(
                        "§7§l> Time: §a" + TimeUtil.formatHHMMSS(counter.getTime()) +
                        "                         §6§l1.8 - 1.13.2" +
                        "\n§7§l> Status: §a" + gameData.getStatus().toString());
    }
}
