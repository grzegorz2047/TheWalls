package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.grzegorz2047.databaseapi.*;
import pl.grzegorz2047.databaseapi.messages.MessageAPI;
import pl.grzegorz2047.databaseapi.shop.ShopAPI;
import pl.grzegorz2047.databaseapi.shop.Transaction;
import pl.grzegorz2047.thewalls.*;
import pl.grzegorz2047.thewalls.api.util.CreateItemUtil;
import pl.grzegorz2047.thewalls.permissions.PermissionAttacher;
import pl.grzegorz2047.thewalls.scoreboard.ScoreboardAPI;

import java.util.HashMap;
import java.util.List;

/**
 * Created by grzegorz2047 on 23.04.2016
 */
public class PlayerJoin implements Listener {

    private final TheWalls plugin;
    private final GameData gameData;
    private final MoneyAPI moneyManager;
    private final StatsAPI statsManager;
    private final ScoreboardAPI scoreboardAPI;
    private final MessageAPI messageManager;
    private final DatabaseAPI playerManager;
    private final ShopAPI shopManager;
    private final World loadedWorld;
    private final Location spawn;

    public PlayerJoin(TheWalls plugin, World loadedWorld) {
        this.plugin = plugin;
        this.gameData = plugin.getGameData();
        this.moneyManager = plugin.getMoneyManager();
        this.statsManager = plugin.getStatsManager();
        this.scoreboardAPI = plugin.getScoreboardAPI();
        this.messageManager = plugin.getMessageManager();
        this.playerManager = plugin.getPlayerManager();
        this.shopManager = plugin.getShopManager();
        this.loadedWorld = loadedWorld;
        spawn = new Location(this.loadedWorld, 0, 147, 0);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (e.getPlayer() == null) {
            return;
        }
        e.setJoinMessage(null);
        Player p = e.getPlayer();
        //Location spawn = WorldManagement.getLoadedWorld().getSpawnLocation();
        String loadedWorldName = loadedWorld.getName();
        p.teleport(spawn);
        // int money = 5;
        //plugin.getPlayerManager().insertPlayer(p);
        String playerName = p.getName();
        GameUser gameUser = getGameUser(playerName);
        assignUserPermission(p, loadedWorldName, gameUser);

        GameData.GameStatus status = gameData.getStatus();
        if (!status.equals(GameData.GameStatus.INGAME)) {
            gameData.checkToStart();
            Counter counter = gameData.getCounter();
            Counter.CounterStatus counterStatus = counter.getStatus();
            preparePlayer(p, gameData, scoreboardAPI, gameUser.getLanguage(), gameUser.getMoney(), gameUser.getKills(), gameUser.getDeaths(), gameUser.getWins(), gameUser.getLose(), counterStatus);
            if (counterStatus.equals(Counter.CounterStatus.IDLE)) {
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    scoreboardAPI.updateDisplayName(0, pl);
                }
            }
        } else {
            prepareSpectator(p, gameData, gameUser, scoreboardAPI);
        }
        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1, 1, true, true), true);
        for (PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }
        //ArenaStatus.setPlayers(Bukkit.getOnlinePlayers().size());

    }


    private GameUser getGameUser(String playerName) {
        moneyManager.insertPlayer(playerName);
        statsManager.insertPlayer(playerName);
        int money = moneyManager.getPlayer(playerName);
        //plugin.getPlayerManager().changePlayerExp(p.getName(), 100);
        SQLUser user = playerManager.getPlayer(playerName);
        StatsUser statsUser = statsManager.getPlayer(playerName);
        List<Transaction> transactions = shopManager.getPlayerItems(playerName);
        GameUser gameUser = new GameUser(user, statsUser, transactions, money);
        HashMap<String, GameUser> gameUsers = gameData.getGameUsers();
        gameUsers.put(playerName, gameUser);
        return gameUser;
    }

    private void assignUserPermission(Player p, String loadedWorldName, GameUser gameUser) {
        String userRank = gameUser.getRank();
        if (userRank.equals("HeadAdmin")
                || userRank.equals("Admin")) {
            PermissionAttacher.attachAdminsPermissions(p, loadedWorldName);
        } else if (userRank.equals("GlobalMod")
                || userRank.equals("Mod")
                || userRank.equals("KidMod")
                || userRank.equals("Helper")) {
            PermissionAttacher.attachModsPermissions(p, loadedWorldName);
        }
        PermissionAttacher.attachPlayersPermissions(p, loadedWorldName);
    }

    private void prepareSpectator(Player p, GameData gameData, GameUser gameUser, ScoreboardAPI scoreboardAPI) {
        gameData.makePlayerSpectator(gameUser, p, loadedWorld.getName());
        gameData.makePlayerSpectator(gameUser, p, loadedWorld.getName());
        scoreboardAPI.createJoinSpectatorScoreboard(p, gameUser);
    }

    private void preparePlayer(Player p, GameData gameData, ScoreboardAPI scoreboardAPI, String userLanguage, int userMoney, int userKills, int userDeaths, int userWins, int userLose, Counter.CounterStatus counterStatus) {
        scoreboardAPI.createWaitingScoreboard(p, userMoney, userKills, userDeaths, userWins, userLose, userLanguage);
        String message = messageManager.getMessage(userLanguage, "thewalls.joininfo");
        p.sendMessage(message);
        p.getInventory().clear();
        p.getInventory().setArmorContents(new ItemStack[4]);
        p.setGameMode(GameMode.SURVIVAL);
        p.setHealth(20);
        p.setFoodLevel(20);
        p.setLevel(0);
        p.setFlying(false);
        p.setAllowFlight(false);
        p.getInventory().setItem(0, CreateItemUtil.createItem(Material.BOOK, 1, "§7Klasy"));
    }

}
