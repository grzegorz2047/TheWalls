package pl.grzegorz2047.thewalls;

import org.bukkit.boss.BarColor;

import java.util.Random;

public class BossBarData {
    private final int resetLimitTime;
    private final BarColor[] barColors;
    private final String[] titles;
    private final Random r = new Random();

    public BossBarData(String[] titles, BarColor[] barColors, int resetLimitTime) {
        this.titles = titles;
        this.barColors = barColors;
        this.resetLimitTime = resetLimitTime;
    }

    public int getResetLimitTime() {
        return resetLimitTime;
    }

    public String getRandomTitle() {
        return getRandomValue(this.titles);
     }

    public BarColor getRandomColor() {
        return getRandomValue(this.barColors);
    }
    private String getRandomValue(String[] source) {
        int titles = r.nextInt(source.length - 1);
        return source[titles];
    }

    private BarColor getRandomValue(BarColor[] source) {
        int titles = r.nextInt(source.length - 1);
        return source[titles];
    }
}
