package pl.grzegorz2047.thewalls;

import org.bukkit.Material;
import pl.grzegorz2047.databaseapi.SQLUser;
import pl.grzegorz2047.databaseapi.StatsUser;
import pl.grzegorz2047.databaseapi.shop.Transaction;
import pl.grzegorz2047.thewalls.GameData.GameTeam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grzeg on 16.05.2016.
 */
public class GameUser extends SQLUser {

    private int money;
    private boolean spectator;
    private GameTeam assignedTeam;
    private boolean usedSurface = false;
    private int ingameKills = 0;
    private StatsUser statsUser;
    private List<Transaction> transactions;
    private List<Material> boughtTempItems = new ArrayList<Material>();
    private int protectedFurnaces = 0;

    public GameUser(int userid, String username, String language, String lastip, int exp, boolean pets, boolean effects, boolean disguise, String rank, long rankto) {
        super(userid, username, language, lastip, exp, pets, effects, disguise, rank, rankto);
    }

    public GameUser(SQLUser user, StatsUser statsUser, List<Transaction> transactions, int money) {
        super(user.getUserid(), user.getUsername(), user.getLanguage(),
                user.getLastip(), user.getExp(), user.hasPets(), user.hasEffects(),user.hasDisguise(), user.getRank(), user.getRankto());
        this.money = money;
        this.statsUser = statsUser;
        this.transactions = transactions;
    }

    public int getMoney() {
        return money;
    }

    public void changeMoney(int change) {
        this.money += change;
    }

    public boolean isSpectator() {
        return spectator;
    }

    public void setSpectator(boolean spectator) {
        this.spectator = spectator;
    }

    public GameData.GameTeam getAssignedTeam() {
        return assignedTeam;
    }

    public void setAssignedTeam(GameTeam assignedTeam) {
        this.assignedTeam = assignedTeam;
    }

    public boolean hasUsedSurface() {
        return usedSurface;
    }

    public void setUsedSurface(boolean usedSurface) {
        this.usedSurface = usedSurface;
    }

    public int getIngameKills() {
        return ingameKills;
    }

    public void setIngameKills(int ingameKills) {
        this.ingameKills = ingameKills;
    }

    public void increaseIngameKills(int ingameKills) {
        this.ingameKills += ingameKills;
    }

    public StatsUser getStatsUser() {
        return statsUser;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public List<Material> getBoughtTempItems() {
        return boughtTempItems;
    }

    public int getProtectedFurnaces() {
        return protectedFurnaces;
    }

    public void setProtectedFurnaces(int protectedFurnaces) {
        this.protectedFurnaces = protectedFurnaces;
    }
}
