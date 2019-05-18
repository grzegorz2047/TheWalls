package pl.grzegorz2047.thewalls;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.HashMap;

/**
 * Created by grzeg on 16.05.2016.
 */
public class Counter {
     private final HashMap<String, String> settings;

    public int getTime() {
        return time;
    }

    private int time;
    private boolean running = false;

    public Counter(HashMap<String, String> settings) {
        this.settings = settings;
    }

    private CounterStatus status = CounterStatus.IDLE;

    public enum CounterStatus {
        IDLE,
        COUNTINGTOSTART,
        COUNTINGTODROPWALLS,
        COUNTINGTODM,
        RESTARTINGARENA,
        DEATHMATCH
    }

    public CounterStatus getStatus() {
        return status;
    }

    public void setStatus(CounterStatus status) {
        this.status = status;
    }

    public void cancel() {
        this.setStatus(CounterStatus.IDLE);
        this.time = 0;
        this.running = false;
    }

    public void start(CounterStatus status) {
        this.running = true;

        if (status.equals(CounterStatus.COUNTINGTOSTART)) {
            time = Integer.parseInt(settings.get("thewalls.countingtostarttime"));
        }
        if (status.equals(CounterStatus.COUNTINGTODROPWALLS)) {
            time = Integer.parseInt(settings.get("thewalls.countingtodropwalls"));
        }
        if (status.equals(CounterStatus.COUNTINGTODM)) {
            time = Integer.parseInt(settings.get("thewalls.countingtodm"));
        }
        if (status.equals(CounterStatus.DEATHMATCH)) {
            time = Integer.parseInt(settings.get("thewalls.countingtoend"));
        }
        this.setStatus(status);
    }

    public boolean isRunning() {
        return running;
    }

    public void count() {
        if (this.running) {
            PluginManager pluginManager = Bukkit.getPluginManager();
            if (time > 0) {
                time--;
                pluginManager.callEvent(new CountingEvent(this.getStatus(), this));
            } else {
                pluginManager.callEvent(new CounterEndEvent(this.getStatus(), this));
            }
        }
    }
}
