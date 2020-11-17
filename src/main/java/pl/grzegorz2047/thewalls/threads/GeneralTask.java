package pl.grzegorz2047.thewalls.threads;

import pl.grzegorz2047.thewalls.BossBarExtension;
import pl.grzegorz2047.thewalls.Counter;
import pl.grzegorz2047.thewalls.GameData;

/**
 * Created by grzeg on 08.05.2016.
 */
public class GeneralTask implements Runnable {

    private final GameData gameData;
    private final Counter counter;
    private final BossBarExtension bossBarExtension;

    public GeneralTask(GameData gameData, Counter counter, BossBarExtension bossBarExtension) {
        this.gameData = gameData;
        this.counter = counter;
        this.bossBarExtension = bossBarExtension;
    }

    @Override
    public void run() {
        counter.count();
        gameData.checkToStart();
        bossBarExtension.updateBossBar();
        //ArenaStatus.setLastPing();
    }
}
