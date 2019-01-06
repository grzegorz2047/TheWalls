package pl.grzegorz2047.thewalls;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.grzegorz2047.databaseapi.SQLUser;
import pl.grzegorz2047.databaseapi.shop.Item;
import pl.grzegorz2047.databaseapi.shop.Transaction;
import pl.grzegorz2047.serversmanagement.ArenaStatus;
import pl.grzegorz2047.thewalls.api.exception.IncorrectDataStringException;
import pl.grzegorz2047.thewalls.api.util.*;
import pl.grzegorz2047.thewalls.permissions.PermissionAttacher;
import pl.grzegorz2047.thewalls.playerclass.ClassManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by grzeg on 16.05.2016.
 */
public class GameData {
    private final TheWalls plugin;
    private Counter counter;
    private HashMap<GameTeam, ArrayList<String>> teams = new HashMap<GameTeam, ArrayList<String>>();
    private HashMap<Location, String> protectedFurnace = new HashMap<Location, String>();
    private ClassManager classManager;
    private int minPlayers;
    private int maxTeamSize;
    private WorldManagement worldManagement;
    private HashMap<String, GameUser> gameUsers = new HashMap<String, GameUser>();
    private int moneyForKill;
    private int moneyForWin;
    private int multiplier;
    private int expForKill;
    private int expForWin;
    private StartGameLocationLoader startGameLocationLoader;

    public GameData(TheWalls plugin) {
        this.plugin = plugin;
        this.counter = new Counter(plugin);
        HashMap<String, String> settings = plugin.getSettings();
        this.minPlayers = Integer.parseInt(settings.get("thewalls.minplayers"));
        maxTeamSize = Integer.parseInt(settings.get("thewalls.maxteamsize"));
        moneyForKill = Integer.parseInt(settings.get("thewalls.moneyforkill"));
        multiplier = Integer.parseInt(settings.get("thewalls.multiplier"));
        moneyForWin = multiplier * Integer.parseInt(settings.get("thewalls.moneyforwin"));
        expForKill = Integer.parseInt(settings.get("thewalls.expforkill"));
        expForWin = Integer.parseInt(settings.get("thewalls.expforwin"));
        initializeArrays();
        this.worldManagement = new WorldManagement();
        //WorldManagement.loadWorld(getSettings().get("thewalls.map.path"));
        int numberOfMaps = Integer.parseInt(settings.get("thewalls.numberofmaps"));
        worldManagement.loadWorld(numberOfMaps);
        worldManagement.disableSaving();
        classManager = new ClassManager(plugin);
        startGameLocationLoader = new StartGameLocationLoader(plugin).invoke(worldManagement);

    }

    private GameStatus status = GameStatus.WAITING;

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public HashMap<GameTeam, ArrayList<String>> getTeams() {
        return teams;
    }

    public Counter getCounter() {
        return counter;
    }

    public int getMaxTeamSize() {
        return maxTeamSize;
    }

    public int getMoneyForKill() {
        return moneyForKill;
    }

    public int getMoneyForWin() {
        return moneyForWin;
    }

    public int getExpForKill() {
        return expForKill;
    }

    public int getExpForWin() {
        return expForWin;
    }

    public HashMap<Location, String> getProtectedFurnace() {
        return protectedFurnace;
    }

    public enum GameTeam {
        TEAM1,
        TEAM2,
        TEAM3,
        TEAM4
    }

    public enum GameStatus {
        WAITING,
        INGAME,
        STARTING,
        RESTARTING
    }

    public void restartGame() {
        HashMap<String, String> settings = plugin.getSettings();
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.kickPlayer("Restart!");
        }
        this.getWorldManagement().reloadLoadedWorld(settings.get("thewalls.numberofmaps"));
        startGameLocationLoader = new StartGameLocationLoader(plugin).invoke(worldManagement);
        initializeArrays();
        status = GameStatus.WAITING;
        counter = new Counter(plugin);
        /*ArenaStatus.setStatus(ArenaStatus.Status.WAITING);
        ArenaStatus.setLore(
                "\n§7§l> §a1.7 - 1.10"
        );*/

    }

    private void initializeArrays() {
        for (GameTeam team : GameTeam.values()) {
            teams.put(team, new ArrayList<String>());
        }
    }

    public void checkToStart() {
        if (this.getStatus().equals(GameStatus.WAITING)) {
            Calendar cal = Calendar.getInstance();
            int godzina = cal.get(Calendar.HOUR_OF_DAY);
            System.out.println("Godzina jest " + godzina);
            if (godzina < 13 || godzina > 21) {
                this.minPlayers = Integer.parseInt(plugin.getSettings().get("thewalls.minplayersearly"));
            } else {
                this.minPlayers = Integer.parseInt(plugin.getSettings().get("thewalls.minplayers"));
            }
            if (Bukkit.getOnlinePlayers().size() >= this.minPlayers) {
                plugin.getGameData().getCounter().start(Counter.CounterStatus.COUNTINGTOSTART);
                this.setStatus(GameStatus.STARTING);
                broadcastToAllPlayers("thewalls.countingstarted");
                //ArenaStatus.setStatus(//ArenaStatus.Status.STARTING);
            }
        } else if (this.getStatus().equals(GameStatus.STARTING)) {
            if (Bukkit.getOnlinePlayers().size() < this.minPlayers) {
                this.setStatus(GameStatus.WAITING);
                broadcastToAllPlayers("thewalls.countingcancelled");
                this.counter.cancel();
            }
        }
    }

    private void broadcastToAllPlayers(String path) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            broadcastMessageToPlayer(p, path);
        }
    }

    private void broadcastMessageToPlayer(Player p, String path) {
        SQLUser user = this.getGameUsers().get(p.getName());
        String message = plugin.getMessageManager().getMessage(user.getLanguage(), path);
        p.sendMessage(message);
    }

    public static String TeamtoColor(GameTeam t) {
        if (t.equals(GameTeam.TEAM1)) {
            return "§a";
        }
        if (t.equals(GameTeam.TEAM2)) {
            return "§b";
        }
        if (t.equals(GameTeam.TEAM3)) {
            return "§c";
        }
        if (t.equals(GameTeam.TEAM4)) {
            return "§e";
        }
        return "§7";
    }

    public void startGame() {
        //ArenaStatus.setStatus(//ArenaStatus.Status.INGAME);
        this.getWorldManagement().setProtected(true);
        Location locteam1 = startGameLocationLoader.getLocteam1();
        Location locteam2 = startGameLocationLoader.getLocteam2();
        Location locteam3 = startGameLocationLoader.getLocteam3();
        Location locteam4 = startGameLocationLoader.getLocteam4();

        String expGiveMsgPL = plugin.getMessageManager().getMessage("PL", "thewalls.exp.giveinfo");
        String expGiveMsgEN = plugin.getMessageManager().getMessage("EN", "thewalls.exp.giveinfo");

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.getInventory().clear();
            GameUser user = this.getGameUsers().get(p.getName());

            assignUnassiged(p, user);

            teleportToArenaTeamSpawns(locteam1, locteam2, locteam3, locteam4, p, user);
            if (!user.getRank().equals("Gracz")) {
                p.setLevel(5);
                if (user.getLanguage().equals("PL")) {
                    p.sendMessage(expGiveMsgPL);
                } else if (user.getLanguage().equals("EN")) {
                    p.sendMessage(expGiveMsgEN);
                } else {
                    p.sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.exp.giveinfo"));
                }
            }
            p.getInventory().addItem(new ItemStack(Material.LAPIS_ORE, 1));
            ItemStack netherStar = CreateItemUtil.createItem(Material.NETHER_STAR, "§6Sklep");
            p.getInventory().setItem(8, netherStar);
            plugin.getScoreboardAPI().createIngameScoreboard(p, user);
            this.setStatus(GameStatus.INGAME);
            //ArenaStatus.setStatus(//ArenaStatus.Status.INGAME);
            givePermItems(p, user);
            givePlayerClass(p, user);
        }
        refreshScoreboardToAll();
        this.counter.start(Counter.CounterStatus.COUNTINGTODROPWALLS);
    }

    private void refreshScoreboardToAll() {
        for (Player pl : Bukkit.getOnlinePlayers()) {
            plugin.getScoreboardAPI().refreshTags(pl);
        }
    }

    private void teleportToArenaTeamSpawns(Location locteam1, Location locteam2, Location locteam3, Location locteam4, Player p, GameUser user) {
        if (user.getAssignedTeam().equals(GameTeam.TEAM1)) {
            p.teleport(locteam1);
        } else if (user.getAssignedTeam().equals(GameTeam.TEAM2)) {
            p.teleport(locteam2);
        } else if (user.getAssignedTeam().equals(GameTeam.TEAM3)) {
            p.teleport(locteam3);
        } else if (user.getAssignedTeam().equals(GameTeam.TEAM4)) {
            p.teleport(locteam4);
        }
    }

    private void assignUnassiged(Player p, GameUser user) {
        if (user.getAssignedTeam() == null) {
            boolean found = false;
            if (Bukkit.getOnlinePlayers().size() >= 20) {
                for (Map.Entry<GameTeam, ArrayList<String>> entry : getTeams().entrySet()) {
                    if (entry.getValue().size() < this.maxTeamSize) {
                        user.setAssignedTeam(entry.getKey());
                        entry.getValue().add(p.getName());
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    getTeams().get(GameTeam.TEAM4).add(p.getName());
                    user.setAssignedTeam(GameTeam.TEAM4);
                }
            } else {
                if (teams.get(GameTeam.TEAM1).size() < this.maxTeamSize) {
                    teams.get(GameTeam.TEAM1).add(p.getName());
                    user.setAssignedTeam(GameTeam.TEAM1);
                } else {
                    teams.get(GameTeam.TEAM2).add(p.getName());
                    user.setAssignedTeam(GameTeam.TEAM2);
                }
            }
            ColoringUtil.colorPlayerTab(p, GameData.TeamtoColor(user.getAssignedTeam()));
        }
    }

    private void givePlayerClass(Player p, GameUser user) {
        ClassManager.CLASS playerClass = plugin.getGameData().getClassManager().getPlayerClasses().get(p.getName());
        if (playerClass == null) {
            playerClass = ClassManager.CLASS.GORNIK;
        }
        String kittype = "Gracz";
        if (!user.getRank().equals("Gracz")) {
            kittype = "Vip";
        }
        for (ItemStack it : plugin.getGameData().getClassManager().getClassInventory().get(playerClass).get(kittype).getInventory()) {
            if (it != null) {
                p.getInventory().addItem(it);
            }
        }
        p.sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.msg.classgiven").replace("{CLASS}", playerClass.name()));
    }

    private void givePermItems(Player p, GameUser user) {
        for (Transaction t : user.getTransactions()) {
            Item perm = plugin.getShopMenuManager().getNormalItems().get(t.getItemid());
            if (perm != null) {
                p.getInventory().addItem(perm.toItemStack());
            }
        }
        broadcastMessageToPlayer(p, "shop.givenpermitems");
    }

    public void startFight() {

        this.getWorldManagement().removeWalls();
        this.counter.start(Counter.CounterStatus.COUNTINGTODM);
        fixInvisiblePlayers();
    }

    private void fixInvisiblePlayers() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            for (Player pl : Bukkit.getOnlinePlayers()) {
                p.hidePlayer(pl);
                p.showPlayer(pl);
                pl.hidePlayer(p);
                pl.showPlayer(p);
            }
        }
    }

    public void startDeathMatch() {
        try {
            Location dmloc1 = LocationUtil.entityStringToLocation(
                    this.getWorldManagement().getLoadedWorld().getName(),
                    plugin.getSettings().get("thewalls.spawns.dm.team." + 1));
            Location dmloc2 = LocationUtil.entityStringToLocation(
                    this.getWorldManagement().getLoadedWorld().getName(),
                    plugin.getSettings().get("thewalls.spawns.dm.team." + 2));
            Location dmloc3 = LocationUtil.entityStringToLocation(
                    this.getWorldManagement().getLoadedWorld().getName(),
                    plugin.getSettings().get("thewalls.spawns.dm.team." + 3));
            Location dmloc4 = LocationUtil.entityStringToLocation(
                    this.getWorldManagement().getLoadedWorld().getName(),
                    plugin.getSettings().get("thewalls.spawns.dm.team." + 4));

            for (Player p : Bukkit.getOnlinePlayers()) {
                GameUser user = this.
                        getGameUsers().
                        get(p.getName());
                if (user.getAssignedTeam() == null) {
                    p.teleport(dmloc1);
                    continue;
                }
                teleportToDeathMatch(dmloc1, dmloc2, dmloc3, dmloc4, p, user);
            }
            this.setStatus(GameStatus.INGAME);
            //ArenaStatus.setStatus(//ArenaStatus.Status.INGAME);
        } catch (IncorrectDataStringException e) {
            e.printStackTrace();
        }
        this.counter.start(Counter.CounterStatus.DEATHMATCH);
    }

    private void teleportToDeathMatch(Location dmloc1, Location dmloc2, Location dmloc3, Location dmloc4, Player p, GameUser user) {
        if (user.getAssignedTeam().equals(GameTeam.TEAM1)) {
            p.teleport(dmloc1);
        } else if (user.getAssignedTeam().equals(GameTeam.TEAM2)) {
            p.teleport(dmloc2);
        } else if (user.getAssignedTeam().equals(GameTeam.TEAM3)) {
            p.teleport(dmloc3);
        } else if (user.getAssignedTeam().equals(GameTeam.TEAM4)) {
            p.teleport(dmloc4);
        }
    }

    public void makePlayerSpectator(GameUser gameUser, Player p, String worldName) {
        gameUser.setSpectator(true);
        p.setGameMode(GameMode.SPECTATOR);
        p.setAllowFlight(true);
        p.setFlying(true);
        PermissionAttacher.attachSpectatorPermissions(plugin, p, worldName);
        if (gameUser.getAssignedTeam() != null) {
            this.getTeams().get(gameUser.getAssignedTeam()).remove(p.getName());
            gameUser.setAssignedTeam(null);
        }
    }

    public HashMap<String, GameUser> getGameUsers() {
        return gameUsers;
    }


    public WorldManagement getWorldManagement() {
        return worldManagement;
    }

    public void checkWinners() {
        int i = 0;
        GameTeam team = null;
        for (Map.Entry<GameTeam, ArrayList<String>> entry : this.getTeams().entrySet()) {

            if (entry.getValue().size() > 0) {
                i++;
                team = entry.getKey();
            }
        }
        if (i == 0) {
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        //nikt nie wygral
                        BungeeUtil.changeServer(plugin, p, "Lobby1");
                    }
                    status = GameStatus.RESTARTING;
                }
            }, 20 * 3);

            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    restartGame();
                }
            }, 20 * 6);

        } else if (i == 1) {
            final GameTeam finalTeam = team;
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    for (Map.Entry<String, GameUser> user : GameData.this.getGameUsers().entrySet()) {
                        Player p = Bukkit.getPlayer(user.getKey());
                        p.sendMessage(
                                plugin.getMessageManager().getMessage(
                                        user.getValue().getLanguage(),
                                        "thewalls.game.win." + finalTeam.name().toLowerCase()));
                        giveMoneyToWinners(user);
                        BungeeUtil.changeServer(plugin, p, "Lobby1");
                    }
                    status = GameStatus.RESTARTING;
                }
            }, 20 * 3);


            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    plugin.getGameData().restartGame();
                }
            }, 20 * 6);

        }
    }

    private void giveMoneyToWinners(Map.Entry<String, GameUser> user) {
        if (user.getValue().getAssignedTeam() != null) {
            plugin.getStatsManager().increaseValueBy(user.getKey(), "wins", 1);
            if (user.getValue().getRank().equals("Gracz")) {
                plugin.getMoneyManager().changePlayerMoney(user.getKey(), Integer.parseInt(plugin.getSettings().get("thewalls.moneyforwin")));//TODO

            } else {
                plugin.getMoneyManager().changePlayerMoney(user.getKey(), 2 * Integer.parseInt(plugin.getSettings().get("thewalls.moneyforwin")));//TODO

            }
            plugin.getPlayerManager().changePlayerExp(user.getKey(), plugin.getGameData().getExpForWin());

        }
    }

    public ClassManager getClassManager() {
        return classManager;
    }


}