package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import pl.grzegorz2047.thewalls.Counter;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.api.util.TimeUtil;

public class ServerMotd implements Listener {

    private final GameData gameData;
    private final Counter counter;
    private final String motd;

    public ServerMotd(GameData gameData, String motd) {
        this.gameData = gameData;
        this.counter = gameData.getCounter();
        this.motd = motd;
    }

    @EventHandler
    public void onServerPing(ServerListPingEvent serverListPingEvent) {
        String time = TimeUtil.formatHHMMSS(counter.getTime());
        serverListPingEvent.setMotd(motd.replace("%TIME%", time).replace("%STATUS%", gameData.getCurrentStatusLabel()));
    }
}
