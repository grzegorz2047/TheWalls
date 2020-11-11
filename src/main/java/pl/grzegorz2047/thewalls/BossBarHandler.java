package pl.grzegorz2047.thewalls;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.Random;

public class BossBarHandler {
    int bossbarTime = 0;
    String[] titles = {"Zapraszamy na ts.mc-walls.pl", "Wesprzyj nas na mc-walls.pl"};
    BarColor[] bossColors = {BarColor.BLUE, BarColor.GREEN};
    Random r = new Random();
    private BossBar bossBar;

    public BossBarHandler() {
        bossBar = Bukkit.createBossBar("Zapraszamy na ts.mc-walls.pl", BarColor.BLUE, BarStyle.SOLID);
    }

    public void updateBossBar() {
        bossbarTime = bossbarTime + 1;
        if (bossbarTime % 60 == 0) {
            bossbarTime = 0;
            int index = r.nextInt(2);
            this.bossBar.setTitle(titles[index]);
            this.bossBar.setColor(bossColors[index]);
        }
    }

    public void addToBossBar(Player p) {
        this.bossBar.addPlayer(p);
    }

    public void removeFromBossBar(Player p) {
        this.bossBar.removePlayer(p);
    }
}