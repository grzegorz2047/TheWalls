package pl.grzegorz2047.thewalls;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.grzegorz2047.databaseapi.DatabaseAPI;
import pl.grzegorz2047.databaseapi.MoneyAPI;
import pl.grzegorz2047.databaseapi.StatsAPI;
import pl.grzegorz2047.databaseapi.messages.MessageAPI;
import pl.grzegorz2047.databaseapi.shop.ShopAPI;
import pl.grzegorz2047.thewalls.commands.surface.SurfaceCommand;
import pl.grzegorz2047.thewalls.commands.team.TeamCommand;
import pl.grzegorz2047.thewalls.commands.tep.TepCommand;
import pl.grzegorz2047.thewalls.commands.walls.WallsCommand;
import pl.grzegorz2047.thewalls.listeners.*;
import pl.grzegorz2047.thewalls.scoreboard.ScoreboardAPI;
import pl.grzegorz2047.thewalls.shop.Shop;
import pl.grzegorz2047.thewalls.threads.GeneralTask;

import java.util.HashMap;

/**
 * Created by grzegorz2047 on 23.04.2016
 */
public class TheWalls extends JavaPlugin {

    private MoneyAPI moneyManager;
    private DatabaseAPI playerManager;
    private MessageAPI messageManager;
    private ScoreboardAPI scoreboardAPI;
    private StatsAPI statsAPI;
    private HashMap<String, String> settings = new HashMap<String, String>();
    private GameData gameData;
    private ShopAPI shopManager;
    private Shop shopMenuManager;


    public ShopAPI getShopManager() {
        return shopManager;
    }

    @Override
    public void onEnable() {
        moneyManager = new MoneyAPI("remotemysql.com", 3306, "2JhSk0sNGM", "2JhSk0sNGM", "Q7rlTVBx55");
        statsAPI = new StatsAPI("remotemysql.com", 3306, "2JhSk0sNGM", "2JhSk0sNGM", "Q7rlTVBx55", "TheWallsStats");
        playerManager = new DatabaseAPI("remotemysql.com", 3306, "2JhSk0sNGM", "2JhSk0sNGM", "Q7rlTVBx55");
        shopManager = new ShopAPI("remotemysql.com", 3306,"2JhSk0sNGM", "2JhSk0sNGM", "Q7rlTVBx55", "TheWallsShopItems", "TheWallsShopItemsPurchased");
        shopMenuManager = new Shop(shopManager);
        messageManager = new MessageAPI("remotemysql.com", 3306, "2JhSk0sNGM", "2JhSk0sNGM", "Q7rlTVBx55", "TheWalls");
        moneyManager.setMoneyTable("TheWallsMoney");
        setSettings(playerManager.getSettings());
        Bukkit.getScheduler().runTaskTimer(this, new GeneralTask(this), 0, 20l);
        scoreboardAPI = new ScoreboardAPI(this);
        gameData = new GameData(this);
        registerListeners(gameData);
        this.getCommand("team").setExecutor(new TeamCommand("team", new String[]{"team", "druzyna", "t", "d"}, this));
        this.getCommand("wyjdz").setExecutor(new SurfaceCommand("wyjdz", new String[]{"wyjdz", "surface"}, this));
        this.getCommand("walls").setExecutor(new WallsCommand("walls", new String[]{"walls", "thewalls"}, this));
        this.getCommand("tep").setExecutor(new TepCommand(this));
        //ArenaStatus.initStatus(Bukkit.getMaxPlayers());
        //ArenaStatus.setStatus(//ArenaStatus.Status.WAITING);
        //ArenaStatus.setPlayers(0);
        //ArenaStatus.setLore("§7§l> §a1.7 - 1.10");
        System.out.println("thewalls zostaly wlaczone!");


    }

    private void registerListeners(GameData gameData) {
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLogin(this), this);
        Bukkit.getPluginManager().registerEvents(new EntityExplode(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChat(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDead(this), this);
        Bukkit.getPluginManager().registerEvents(new CounterEnd(this), this);
        Bukkit.getPluginManager().registerEvents(new GeneralBlocking(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlace(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayersDamaging(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreak(this), this);
        Bukkit.getPluginManager().registerEvents(new Counting(this), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClick(this), this);
        Bukkit.getPluginManager().registerEvents(new ChooseItem(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteract(this), this);
        Bukkit.getPluginManager().registerEvents(new ItemDrop(this), this);
        Bukkit.getPluginManager().registerEvents(new ServerMotd(gameData), this);

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    @Override
    public void onDisable() {
        System.out.println("thewalls is off!");
    }


    public MoneyAPI getMoneyManager() {
        return moneyManager;
    }

    public DatabaseAPI getPlayerManager() {
        return playerManager;
    }

    public MessageAPI getMessageManager() {
        return messageManager;
    }


    public HashMap<String, String> getSettings() {
        return settings;
    }

    public void setSettings(HashMap<String, String> settings) {
        this.settings = settings;
    }

    public ScoreboardAPI getScoreboardAPI() {
        return scoreboardAPI;
    }

    public GameData getGameData() {
        return gameData;
    }


    public StatsAPI getStatsManager() {
        return statsAPI;
    }

    public Shop getShopMenuManager() {
        return shopMenuManager;
    }


}
