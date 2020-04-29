package pl.grzegorz2047.thewalls;

import org.bukkit.entity.Player;
import pl.grzegorz2047.databaseapi.*;
import pl.grzegorz2047.databaseapi.shop.ShopAPI;
import pl.grzegorz2047.databaseapi.shop.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameUsers {

    private final MoneyAPI moneyManager;
    private final DatabaseAPI playerManager;
    private final StatsAPI statsManager;
    private final ShopAPI shopManager;

    private final HashMap<String, GameUser> gameUsers = new HashMap<>();

    public GameUsers(MoneyAPI moneyManager, DatabaseAPI playerManager, StatsAPI statsManager, ShopAPI shopManager) {
        this.moneyManager = moneyManager;
        this.playerManager = playerManager;
        this.statsManager = statsManager;

        this.shopManager = shopManager;
    }

    public GameUser addGameUser(String playerName) {
        moneyManager.insertPlayer(playerName);
        statsManager.insertPlayer(playerName);
        int money = moneyManager.getPlayer(playerName);
        //plugin.getPlayerManager().changePlayerExp(p.getName(), 100);
        SQLUser user = playerManager.getPlayer(playerName);
        StatsUser statsUser = statsManager.getPlayer(playerName);
        List<Transaction> transactions = shopManager.getPlayerItems(playerName);
        GameUser gameUser = new GameUser(user, statsUser, transactions, money, this);
        gameUsers.put(playerName, gameUser);
        return gameUser;
    }

    public GameUser getGameUser(String username) {
        return gameUsers.get(username);
    }

    public boolean isArenaEmpty() {
        return gameUsers.size() == 0;
    }


    public GameUser removePlayerFromGame(Player p) {
        return gameUsers.remove(p.getName());
    }


    public Set<Map.Entry<String, GameUser>> getArenaUsers() {
        return gameUsers.entrySet();
    }

    public int getNumberOfPlayers() {
        return gameUsers.size();
    }

    public void changePlayerMoney(String username, int change) {
        moneyManager.changePlayerMoney(username, change);
    }

    public void changePlayerExp(String username, int expForKill) {
        playerManager.changePlayerExp(username, expForKill);
    }

    public void increaseValueBy(String username, String column, int value) {
        statsManager.increaseValueBy(username, column, value);
    }
}
