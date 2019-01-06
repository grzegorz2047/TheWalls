package pl.grzegorz2047.thewalls.threads;

import pl.grzegorz2047.serversmanagement.ArenaStatus;
import pl.grzegorz2047.thewalls.TheWalls;

/**
 * Created by grzeg on 08.05.2016.
 */
public class GeneralTask implements Runnable {
    private final TheWalls plugin;

    public GeneralTask(TheWalls plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.getGameData().getCounter().count();
        //ArenaStatus.setLastPing();
    }
}
