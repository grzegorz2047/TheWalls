package pl.grzegorz2047.thewalls.threads;

import pl.grzegorz2047.thewalls.Counter;
import pl.grzegorz2047.thewalls.GameData;

/**
 * Created by grzeg on 08.05.2016.
 */
public class GeneralTask implements Runnable {

    private final GameData gameData;
    private final Counter counter;

    public GeneralTask(GameData gameData, Counter counter) {
        this.gameData = gameData;
        this.counter = counter;
    }

    @Override
    public void run() {
        counter.count();
        gameData.checkToStart();
        //ArenaStatus.setLastPing();
    }
}
