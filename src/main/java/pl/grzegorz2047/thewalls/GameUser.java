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

    private final GameUsers gameUsers;
    private int money;
    private boolean spectator;
    private GameTeam assignedTeam;
    private boolean usedSurface = false;
    private int ingameKills = 0;
    private StatsUser statsUser;
    private List<Transaction> transactions;
    private List<Material> boughtTempItems = new ArrayList<Material>();
    private int protectedFurnaces = 0;

    public GameUser(int userid, String username, String language, String lastip, int exp, boolean pets, boolean effects, boolean disguise, String rank, long rankto, GameUsers gameUsers) {
        super(userid, username, language, lastip, exp, pets, effects, disguise, rank, rankto);
        this.gameUsers = gameUsers;
    }

    public GameUser(SQLUser user, StatsUser statsUser, List<Transaction> transactions, int money, GameUsers gameUsers) {
        super(user.getUserid(), user.getUsername(), user.getLanguage(),
                user.getLastip(), user.getExp(), user.hasPets(), user.hasEffects(), user.hasDisguise(), user.getRank(), user.getRankto());
        this.money = money;
        this.statsUser = statsUser;
        this.transactions = transactions;
        this.gameUsers = gameUsers;
    }

    public int getMoney() {
        return money;
    }

    public void changeMoney(int change) {
        this.money += change;
        gameUsers.changePlayerMoney(getUsername(), change);
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

    public void increaseIngameKills(int ingameKills) {
        this.ingameKills += ingameKills;
        gameUsers.increaseValueBy(getUsername(), "kills", 1);
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

    public void incrementProtectedFurnaces() {
        setProtectedFurnaces(getProtectedFurnaces() + 1);
    }

    public void decrementProtectedFurnaces() {
        setProtectedFurnaces(getProtectedFurnaces() - 1);
    }

    public int getLose() {
        return this.statsUser.getLose();
    }

    public int getWins() {
        return this.statsUser.getWins();
    }

    public int getDeaths() {
        return this.statsUser.getDeaths();
    }

    public int getKills() {
        return this.statsUser.getKills();
    }

    public void addExp(int expForKill) {
        gameUsers.changePlayerExp(getUsername(), expForKill);
    }

    public void addDeath() {
        gameUsers.increaseValueBy(getUsername(), "deaths", 1);
    }

    public void addLostGame() {
        gameUsers.increaseValueBy(getUsername(), "lose", 1);
    }

    public void addWonGame() {
        gameUsers.increaseValueBy(getUsername(), "wins", 1);
    }
}
