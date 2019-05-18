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
    private String motd = "§7§l> Czas: §a%TIME%                         §6§l1.8 - 1.14.1\n§7§l> Status: §a%STATUS%";

    public ServerMotd(GameData gameData) {
        this.gameData = gameData;
        counter = gameData.getCounter();
    }

    @EventHandler
    public void onServerPing(ServerListPingEvent serverListPingEvent) {
        String time = TimeUtil.formatHHMMSS(counter.getTime());
        String status = gameData.getStatus().toString();
        motd = motd.replaceAll("%TIME%", time).replaceAll("%STATUS%", status);
        serverListPingEvent.setMotd(motd);
    }
}
