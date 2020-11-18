package pl.grzegorz2047.thewalls;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BossBarExtension {
    private final BossBarData bossBarData;
    private int bossbarTime = 0;
    private final BossBar bossBar;

    public BossBarExtension(BossBar bossBar, BossBarData bossBarData) {
        this.bossBar = bossBar;
        this.bossBarData = bossBarData;
    }

    public void updateBossBar() {
        if (bossbarTime % bossBarData.getResetLimitTime() == 0) {
            bossbarTime = 0;
            this.bossBar.setTitle(bossBarData.getRandomTitle());
            this.bossBar.setColor(bossBarData.getRandomColor());

        }
        bossbarTime = bossbarTime + 1;
    }

    public void addToBossBar(Player p) {
        this.bossBar.addPlayer(p);
    }

    public void removeFromBossBar(Player p) {
        this.bossBar.removePlayer(p);
    }
}