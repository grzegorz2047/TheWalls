package pl.grzegorz2047.thewalls;

import org.bukkit.Bukkit;

/**
 * Created by grzeg on 16.05.2016.
 */
public class Counter {
    private final TheWalls plugin;

    public int getTime() {
        return time;
    }

    private int time;
    private boolean running = false;

    public Counter(TheWalls plugin) {
        this.plugin = plugin;
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
        this.running = false;
    }

    public void start(CounterStatus status) {
        this.running = true;

        if (status.equals(CounterStatus.COUNTINGTOSTART)) {
            time = Integer.parseInt(plugin.getSettings().get("thewalls.countingtostarttime"));
        }
        if (status.equals(CounterStatus.COUNTINGTODROPWALLS)) {
            time = Integer.parseInt(plugin.getSettings().get("thewalls.countingtodropwalls"));
        }
        if (status.equals(CounterStatus.COUNTINGTODM)) {
            time = Integer.parseInt(plugin.getSettings().get("thewalls.countingtodm"));
        }
        if (status.equals(CounterStatus.DEATHMATCH)) {
            time = Integer.parseInt(plugin.getSettings().get("thewalls.countingtoend"));
        }
        this.setStatus(status);
    }

    public boolean isRunning() {
        return running;
    }

    public void count() {
        if (this.running) {
            if (time > 0) {
                time--;
                Bukkit.getPluginManager().callEvent(new CountingEvent(this.getStatus(), this));
            } else {
                Bukkit.getPluginManager().callEvent(new CounterEndEvent(this.getStatus(), this));
            }
        }
    }
}
