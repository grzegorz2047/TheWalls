package pl.grzegorz2047.thewalls.threads;

import pl.grzegorz2047.thewalls.BossBarHandler;
import pl.grzegorz2047.thewalls.Counter;
import pl.grzegorz2047.thewalls.GameData;

/**
 * Created by grzeg on 08.05.2016.
 */
public class GeneralTask implements Runnable {

    private final GameData gameData;
    private final Counter counter;
    private final BossBarHandler bossBarHandler;

    public GeneralTask(GameData gameData, Counter counter, BossBarHandler bossBarHandler) {
        this.gameData = gameData;
        this.counter = counter;
        this.bossBarHandler = bossBarHandler;
    }

    @Override
    public void run() {
        counter.count();
        gameData.checkToStart();
        bossBarHandler.updateBossBar();
        //ArenaStatus.setLastPing();
    }
}
