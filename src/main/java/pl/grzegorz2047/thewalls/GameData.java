package pl.grzegorz2047.thewalls;

import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import pl.grzegorz2047.databaseapi.*;
import pl.grzegorz2047.databaseapi.messages.MessageAPI;
import pl.grzegorz2047.thewalls.api.util.*;
import pl.grzegorz2047.thewalls.permissions.PermissionAttacher;
import pl.grzegorz2047.thewalls.playerclass.ClassManager;
import pl.grzegorz2047.thewalls.scoreboard.ScoreboardAPI;
import pl.grzegorz2047.thewalls.shop.Shop;

import java.util.*;

/**
 * Created by grzeg on 16.05.2016.
 */
public class GameData {
    private final TheWalls plugin;

    private final MessageAPI messageManager;
    private final HashMap<String, String> settings;


    private final Shop shopMenuManager;
    private Counter counter;
    private HashMap<GameTeam, ArrayList<String>> teams = new HashMap<GameTeam, ArrayList<String>>();

    private int minPlayers;
    private int maxTeamSize;
    private WorldManagement worldManagement;
    private int moneyForKill;
    private int moneyForWin;
    private int multiplier;
    private int expForKill;
    private int expForWin;


    private boolean isCrackersAuthme = true;
    private GameUsers gameUsers;


    public GameData(TheWalls plugin, Counter counter, GameUsers gameUsers) {
        this.plugin = plugin;
        this.counter = counter;
        this.gameUsers = gameUsers;
        HashMap<String, String> settings = plugin.getSettings();
        this.minPlayers = Integer.parseInt(settings.get("thewalls.minplayers"));
        maxTeamSize = Integer.parseInt(settings.get("thewalls.maxteamsize"));
        moneyForKill = Integer.parseInt(settings.get("thewalls.moneyforkill"));
        multiplier = Integer.parseInt(settings.get("thewalls.multiplier"));
        moneyForWin = multiplier * Integer.parseInt(settings.get("thewalls.moneyforwin"));
        expForKill = Integer.parseInt(settings.get("thewalls.expforkill"));
        expForWin = Integer.parseInt(settings.get("thewalls.expforwin"));
        initializeArrays();
        this.settings = plugin.getSettings();
        this.worldManagement = new WorldManagement(Integer.parseInt(settings.get("thewalls.numberofmaps")), settings);
        //WorldManagement.loadWorld(getSettings().get("thewalls.map.path"));
        worldManagement.initNewWorld();
        worldManagement.disableSaving();


        messageManager = plugin.getMessageManager();

        shopMenuManager = plugin.getShopMenuManager();


    }

    private GameStatus status = GameStatus.WAITING;

    public boolean isStatus(GameStatus gameStatus) {
        return status.equals(gameStatus);
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


    public ArrayList<String> getTeam(GameTeam team) {
        return teams.get(team);
    }

    public void removeFromTeam(String username, GameTeam assignedTeam) {
        teams.get(assignedTeam).remove(username);
    }

    public void addPlayerToTeam(String username, GameTeam team) {
        teams.get(team).add(username);
    }

    public int getTeamSize(GameTeam team) {
        return teams.get(team).size();
    }


    public String getCurrentStatusLabel() {
        return status.toString();
    }


    public void addPlayerToArena(Player p) {
        String playerName = p.getName();
        GameUser gameUser = gameUsers.addGameUser(playerName);
        assignUserPermission(p, getWorldManagement().getLoadedWorldName(), gameUser);
        Location spawn = new Location(getWorldManagement().getLoadedWorld(), 0, 147, 0);
        p.teleport(spawn);
        ScoreboardAPI scoreboardAPI = plugin.getScoreboardAPI();
        if (!isStatus(GameData.GameStatus.INGAME)) {
            checkToStart();
            Counter.CounterStatus counterStatus = counter.getStatus();
            preparePlayer(p, scoreboardAPI, gameUser);
            updateScoreboardStatus(scoreboardAPI, counterStatus);
        } else {
            prepareSpectator(p, gameUser, scoreboardAPI);
        }
        clearPlayerEffects(p);
    }

    private void updateScoreboardStatus(ScoreboardAPI scoreboardAPI, Counter.CounterStatus counterStatus) {
        if (counterStatus.equals(Counter.CounterStatus.IDLE)) {
            for (Player pl : Bukkit.getOnlinePlayers()) {
                scoreboardAPI.updateDisplayName(0, pl, gameUsers.getNumberOfPlayers());
            }
        }
    }

    private void clearPlayerEffects(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1, 1, true, true), true);
        for (PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }
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

    private void prepareSpectator(Player p, GameUser gameUser, ScoreboardAPI scoreboardAPI) {
        String loadedWorldName = worldManagement.getLoadedWorldName();
        makePlayerSpectator(gameUser, p, loadedWorldName);
        makePlayerSpectator(gameUser, p, loadedWorldName);
        scoreboardAPI.createJoinSpectatorScoreboard(p, gameUser, gameUsers);
    }

    private void preparePlayer(Player p, ScoreboardAPI scoreboardAPI, GameUser gameUser) {
        scoreboardAPI.createWaitingScoreboard(p, gameUser);
        String message = messageManager.getMessage(gameUser.getLanguage(), "thewalls.joininfo");
        p.sendMessage(message);
        PlayerInventory inventory = p.getInventory();
        inventory.clear();
        inventory.setArmorContents(new ItemStack[4]);
        p.setGameMode(GameMode.SURVIVAL);
        p.setHealth(20);
        p.setFoodLevel(20);
        p.setLevel(0);
        p.setFlying(false);
        p.setAllowFlight(false);
        inventory.setItem(0, CreateItemUtil.createItem(Material.BOOK, 1, "§7Klasy"));
        inventory.setItem(2, CreateItemUtil.createItem(Material.GREEN_WOOL, 1, "§aZieloni"));
        inventory.setItem(3, CreateItemUtil.createItem(Material.LIGHT_BLUE_WOOL, 1, "§bNiebiescy"));
        inventory.setItem(4, CreateItemUtil.createItem(Material.RED_WOOL, 1, "§cCzerwoni"));
        inventory.setItem(5, CreateItemUtil.createItem(Material.YELLOW_WOOL, 1, "§eŻółci"));
        inventory.setItem(8, CreateItemUtil.createItem(Material.FEATHER, 1, "§cZmień Język/Language"));
    }


    public void handlePlayerQuit(Player p) {
        GameUser user = gameUsers.getGameUser(p.getName());
        //e.setQuitMessage(plugin.getMessageManager().getMessage(user.getLanguage(),"thewalls.msg.quit"));
        makePlayerSpectator(user, p, getWorldManagement().getLoadedWorldName());
        gameUsers.removePlayerFromGame(p);
        if (isStatus(GameData.GameStatus.WAITING)) {
            checkToStart();
            for (Player pl : Bukkit.getOnlinePlayers()) {
                plugin.getScoreboardAPI().updateDisplayName(0, pl, gameUsers.getNumberOfPlayers());
            }
        } else if (isStatus(GameData.GameStatus.INGAME)) {
            if (gameUsers.isArenaEmpty()) {
                restartGame("thewalls.empty");
            }
            checkWinners();
        }
    }

    public String handleKilledPerson(Player killed) {
        World loadedWorld = worldManagement.getLoadedWorld();
        final String killedPlayerName = killed.getName();
        GameUser killedUser = gameUsers.getGameUser(killedPlayerName);
        killedUser.addDeath();
        killedUser.addLostGame();

        killed.setHealth(20);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            killed.setHealth(20);
            ColoringUtil.colorPlayerTab(killed, "§7");
            makePlayerSpectator(killedUser, killed, loadedWorld.getName());
            checkWinners();
        }, 1l);
        return killedPlayerName;
    }

    public void handleKiller(Player killer) {
        ScoreboardAPI scoreboardAPI = plugin.getScoreboardAPI();
        String killerName = killer.getName();
        GameUser killerUser = gameUsers.getGameUser(killerName);
        String killerLanguage = killerUser.getLanguage();
        addKillForTeamScoreboard(killerUser, killer, scoreboardAPI);
        addKillerDiamond(killer, killerLanguage);
        killerUser.increaseIngameKills(1);


        givePlayerMoneyForKill(killer, killerUser, killerLanguage, scoreboardAPI);
        giveKillerExp(killerUser);

    }

    private void giveKillerExp(GameUser killerUser) {
        int expForKill = getExpForKill();
        killerUser.addExp(expForKill);

    }

    private void givePlayerMoneyForKill(Player killer, GameUser killerUser, String killerLanguage, ScoreboardAPI scoreboardAPI) {
        String killerRank = killerUser.getRank();
        int moneyForKill = getMoneyForKill();
        Scoreboard killerScoreboard = killer.getScoreboard();
        if (!killerRank.equals("Gracz")) {
            killerUser.changeMoney(moneyForKill * 2);
            killer.sendMessage(messageManager.getMessage(killerLanguage, "thewalls.msg.moneyforkill").replace("{MONEY}", String.valueOf(moneyForKill * 2)));

            scoreboardAPI.updateEntry(killerScoreboard, messageManager.getMessage(killerLanguage, "thewalls.scoreboard.money"), moneyForKill * 2);

        } else {
            killerUser.changeMoney(moneyForKill);
            killer.sendMessage(messageManager.getMessage(killerLanguage, "thewalls.msg.moneyforkill").replace("{MONEY}", String.valueOf(moneyForKill)));
            scoreboardAPI.updateEntry(killerScoreboard, messageManager.getMessage(killerLanguage, "thewalls.scoreboard.money"), moneyForKill);

        }
    }

    private void addKillForTeamScoreboard(GameUser killerUser, Player killer, ScoreboardAPI scoreboardAPI) {
        try {
            GameTeam killerTeam = killerUser.getAssignedTeam();
            scoreboardAPI.addKillForTeam(killerTeam);
        } catch (NullPointerException ex) {
            System.out.print(ex.getMessage());
        }
        Scoreboard killerScoreboard = killer.getScoreboard();
        scoreboardAPI.updateEntry(killerScoreboard, messageManager.getMessage(killerUser.getLanguage(), "thewalls.scoreboard.kills"), killerUser.getIngameKills());

    }

    private void addKillerDiamond(Player killer, String killerLanguage) {
        PlayerInventory inventory = killer.getInventory();
        inventory.addItem(new ItemStack(Material.DIAMOND, 1));
        killer.sendMessage(messageManager.getMessage(killerLanguage, "thewalls.msg.givediamondforkill"));
    }

    public void handleWeirdDeath(Player killed) {
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                if (killed != null) {
                    killed.setHealth(20);
                    World loadedWorld = worldManagement.getLoadedWorld();
                    Location spawn = new Location(loadedWorld, 0, 147, 0);
                    killed.teleport(spawn);
                }
            }
        }, 1l);
    }

    public enum GameTeam {
        TEAM1(1, "§a"),
        TEAM2(2, "§b"),
        TEAM3(3, "§c"),
        TEAM4(4, "§e");

        private String color = "§7";
        private int number;

        GameTeam(int number, String teamColor) {
            this.number = number;
            this.color = teamColor;
        }

        public static GameTeam fromNumber(int teamNumber) {
            for (GameTeam team : GameTeam.values()) {
                if (team.getNumber() == teamNumber) {
                    return team;
                }
            }
            throw new RuntimeException("wrong number");
        }

        private int getNumber() {
            return this.number;
        }

        public String getColor() {
            return color;
        }
    }

    public enum GameStatus {
        WAITING,
        INGAME,
        STARTING,
        RESTARTING
    }

    public void restartGame(String endMessage) {
        if (status.equals(GameStatus.RESTARTING)) {
            plugin.getLogger().info("bug?");
            return;
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            //nikt nie wygral
             p.sendMessage(messageManager.getMessage(gameUsers.getGameUser(p.getName()).getLanguage(),endMessage));
        }
        status = GameStatus.RESTARTING;
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                //nikt nie wygral
                BungeeUtil.changeServer(plugin, p, "Lobby1");
                p.kickPlayer("Arena przygotowuje sie do nowej gry!");
            }
        }, 20l * 5);


        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            worldManagement.initNewWorld();
            initializeArrays();
            status = GameStatus.WAITING;
            this.counter.cancel();
        }, 20l * 10);
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
        int numberOfPlayers = Bukkit.getOnlinePlayers().size();
        if (this.isStatus(GameStatus.WAITING)) {
            Calendar cal = Calendar.getInstance();
            int hour = cal.get(Calendar.HOUR_OF_DAY);
//            System.out.println("Godzina jest " + hour);
            if (hour < 13 || hour > 21) {
                this.minPlayers = Integer.parseInt(settings.get("thewalls.minplayersearly"));
            } else {
                this.minPlayers = Integer.parseInt(settings.get("thewalls.minplayers"));
            }
            if (isCrackersAuthme) {
                int authedPlayers = 0;
                for (Player player : Bukkit.getOnlinePlayers()) {
                    boolean authenticated = AuthMeApi.getInstance().isAuthenticated(player);
                    if (authenticated) {
                        authedPlayers++;
                    }

                }
                if (authedPlayers >= this.minPlayers) {
                    startCountingDown();
                }
            } else {
                if (numberOfPlayers >= this.minPlayers) {
                    startCountingDown();
                    //ArenaStatus.setStatus(//ArenaStatus.Status.STARTING);
                }
            }
        } else if (this.isStatus(GameStatus.STARTING)) {
            if (numberOfPlayers < this.minPlayers) {
                this.setStatus(GameStatus.WAITING);
                broadcastToAllPlayers("thewalls.countingcancelled");
                this.counter.cancel();
            }
        }
    }

    private void startCountingDown() {
        getCounter().start(Counter.CounterStatus.COUNTINGTOSTART);
        this.setStatus(GameStatus.STARTING);
        broadcastToAllPlayers("thewalls.countingstarted");
    }

    private void broadcastToAllPlayers(String path) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            broadcastMessageToPlayer(p, path);
        }
    }

    private void broadcastMessageToPlayer(Player p, String path) {
        SQLUser user = gameUsers.getGameUser(p.getName());
        String message = messageManager.getMessage(user.getLanguage(), path);
        p.sendMessage(message);
    }


    public void startGame(ScoreboardAPI scoreboardAPI, ClassManager classManager) {
        //ArenaStatus.setStatus(//ArenaStatus.Status.INGAME);
        this.getWorldManagement().setProtected(true);

        String expGiveMsgPL = messageManager.getMessage("PL", "thewalls.exp.giveinfo");
        String expGiveMsgEN = messageManager.getMessage("EN", "thewalls.exp.giveinfo");

        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerInventory userInventory = p.getInventory();
            userInventory.clear();
            String username = p.getName();
            GameUser user = gameUsers.getGameUser(username);
            String userRank = user.getRank();

            assignUnassigedToTeams(p, user);

            GameTeam assignedTeam = user.getAssignedTeam();
            Location startLocation = worldManagement.getStartLocation(assignedTeam);
            System.out.println(startLocation.getWorld().toString());
            p.teleport(startLocation);
            if (!userRank.equals("Gracz")) {
                p.setLevel(5);
                String userLanguage = user.getLanguage();
                if (userLanguage.equals("PL")) {
                    p.sendMessage(expGiveMsgPL);
                } else if (userLanguage.equals("EN")) {
                    p.sendMessage(expGiveMsgEN);
                } else {
                    String message = messageManager.getMessage(userLanguage, "thewalls.exp.giveinfo");
                    p.sendMessage(message);
                }
            }
            userInventory.addItem(new ItemStack(Material.LAPIS_ORE, 1));
            ItemStack netherStar = CreateItemUtil.createItem(Material.NETHER_STAR, "§6Sklep");
            userInventory.setItem(8, netherStar);
            scoreboardAPI.createIngameScoreboard(p, user);
            this.setStatus(GameStatus.INGAME);
            //ArenaStatus.setStatus(//ArenaStatus.Status.INGAME);
            shopMenuManager.givePermItems(p, user);
            broadcastMessageToPlayer(p, "shop.givenpermitems");
            classManager.givePlayerClass(p, user);
        }
        refreshScoreboardToAll(scoreboardAPI);
        this.counter.start(Counter.CounterStatus.COUNTINGTODROPWALLS);
    }

    private void refreshScoreboardToAll(ScoreboardAPI scoreboardAPI) {
        for (Player pl : Bukkit.getOnlinePlayers()) {
            scoreboardAPI.refreshTags(pl, gameUsers);
        }
    }


    private void assignUnassigedToTeams(Player p, GameUser user) {
        GameTeam assignedTeam = user.getAssignedTeam();
        if (assignedTeam == null) {
            boolean found = false;
            String playerName = p.getName();
            if (Bukkit.getOnlinePlayers().size() >= 20) {
                for (Map.Entry<GameTeam, ArrayList<String>> entry : getTeams().entrySet()) {
                    if (entry.getValue().size() < this.maxTeamSize) {
                        user.setAssignedTeam(entry.getKey());
                        entry.getValue().add(playerName);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    getTeams().get(GameTeam.TEAM4).add(playerName);
                    user.setAssignedTeam(GameTeam.TEAM4);
                }
            } else {
                if (teams.get(GameTeam.TEAM1).size() < this.maxTeamSize) {
                    teams.get(GameTeam.TEAM1).add(playerName);
                    user.setAssignedTeam(GameTeam.TEAM1);
                } else {
                    teams.get(GameTeam.TEAM2).add(playerName);
                    user.setAssignedTeam(GameTeam.TEAM2);
                }
            }
            assignedTeam = user.getAssignedTeam();
            ColoringUtil.colorPlayerTab(p, assignedTeam.getColor());
        }
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
        getWorldManagement().teleportPlayersOnDeathMatch(gameUsers);
        this.setStatus(GameStatus.INGAME);
        //ArenaStatus.setStatus(//ArenaStatus.Status.INGAME);
        this.counter.start(Counter.CounterStatus.DEATHMATCH);
    }


    public void makePlayerSpectator(GameUser gameUser, Player p, String worldName) {
        gameUser.setSpectator(true);
        p.setGameMode(GameMode.SPECTATOR);
        p.setAllowFlight(true);
        p.setFlying(true);
        PermissionAttacher.attachSpectatorPermissions(p, worldName);
        if (gameUser.getAssignedTeam() != null) {
            this.getTeams().get(gameUser.getAssignedTeam()).remove(p.getName());
            gameUser.setAssignedTeam(null);
        }
    }


    public WorldManagement getWorldManagement() {
        return worldManagement;
    }

    public void checkWinners() {
        if (!status.equals(GameStatus.INGAME)) {
            return;
        }
        GameTeam team = null;
        int alive = 0;
        Set<Map.Entry<GameTeam, ArrayList<String>>> teams = this.getTeams().entrySet();
        for (Map.Entry<GameTeam, ArrayList<String>> iteratedTeamData : teams) {

            ArrayList<String> teamList = iteratedTeamData.getValue();
            if (teamList.size() > 0) {
                alive++;
                team = iteratedTeamData.getKey();
            }
        }
        if (alive == 0) {
            restartGame("thewalls.nowinners");
        } else if (alive == 1) {
            String teamName = team.name().toLowerCase();
            for (Map.Entry<String, GameUser> user : gameUsers.getArenaUsers()) {
                GameUser gameUser = user.getValue();
                giveMoneyToWinners(gameUser);
            }
            restartGame("thewalls.game.win." + teamName);
        }
    }


    private void giveMoneyToWinners(GameUser user) {
        if (user.getAssignedTeam() != null) {
            user.addWonGame();
            int moneForWin = Integer.parseInt(settings.get("thewalls.moneyforwin"));
            if (user.getRank().equals("Gracz")) {
                user.changeMoney(moneForWin);
            } else {
                user.changeMoney(moneForWin * 2);
            }
            user.addExp(getExpForWin());
        }
    }
}