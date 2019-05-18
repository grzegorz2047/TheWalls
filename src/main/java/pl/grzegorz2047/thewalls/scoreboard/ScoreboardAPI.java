package pl.grzegorz2047.thewalls.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import pl.grzegorz2047.databaseapi.messages.MessageAPI;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameUser;
import pl.grzegorz2047.thewalls.TheWalls;

import java.util.Map;
import java.util.Set;

/**
 * Created by s416045 on 2016-04-19.
 */
public class ScoreboardAPI {

    private final TheWalls plugin;
    private final String team1Label;
    private final String team2Label;
    private final String team3Label;
    private final String team4Label;
    private final MessageAPI messageManager;
    private final GameData gameData;


    public ScoreboardAPI(TheWalls plugin, MessageAPI messageManager, GameData gameData) {
        this.plugin = plugin;
        this.team1Label = messageManager.getMessage("PL", "thewalls.scoreboard.ingame.TEAM1");
        this.messageManager = plugin.getMessageManager();
        this.team2Label = this.messageManager.getMessage("PL", "thewalls.scoreboard.ingame.TEAM2");
        this.team3Label = this.messageManager.getMessage("PL", "thewalls.scoreboard.ingame.TEAM3");
        this.team4Label = this.messageManager.getMessage("PL", "thewalls.scoreboard.ingame.TEAM4");
        this.gameData = gameData;
    }


    public void createWaitingScoreboard(Player p, int money, int kills, int deaths, int wins, int lose, String userLanguage) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("sidebar", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§6§l *** TheWalls ***");

        Objective healthObj = scoreboard.registerNewObjective("showhealth", "health");
        healthObj.setDisplaySlot(DisplaySlot.BELOW_NAME);
        healthObj.setDisplayName(" §c§l❤");
        p.setHealth(p.getHealth());

        Objective tablistobj = scoreboard.registerNewObjective("tablist", "dummy");
        tablistobj.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        //addTabListEntry(scoreboard, p.getName(), " §7"); //color tablist?

        addEntry(scoreboard, objective, "§     ", "", 11);
        addEntry(scoreboard, objective, this.messageManager.getMessage(userLanguage, "thewalls.scoreboard.money"), String.valueOf(money), 10);
        addEntry(scoreboard, objective, "§    ", "", 9);
        addEntry(scoreboard, objective, this.messageManager.getMessage(userLanguage, "thewalls.scoreboard.kills"), String.valueOf(kills), 8);
        addEntry(scoreboard, objective, this.messageManager.getMessage(userLanguage, "thewalls.scoreboard.deaths"), String.valueOf(deaths), 7);
        addEntry(scoreboard, objective, this.messageManager.getMessage(userLanguage, "thewalls.scoreboard.wins"), String.valueOf(wins), 6);
        addEntry(scoreboard, objective, this.messageManager.getMessage(userLanguage, "thewalls.scoreboard.lose"), String.valueOf(lose), 5);
        addEntry(scoreboard, objective, "§   ", "", 4);
        addEntry(scoreboard, objective, "§  ", "", 3);
        addEntry(scoreboard, objective, "§ ", "", 2);
        String websiteInfo = this.messageManager.getMessage(userLanguage, "scoreboard.website.address");
        addEntry(scoreboard, objective, websiteInfo, "", 1);
        Team t1 = scoreboard.registerNewTeam("team1");
        t1.setPrefix("§a");
        Team t2 = scoreboard.registerNewTeam("team2");
        t2.setPrefix("§b");
        Team t3 = scoreboard.registerNewTeam("team3");
        t3.setPrefix("§c");
        Team t4 = scoreboard.registerNewTeam("team4");
        t4.setPrefix("§e");
        p.setScoreboard(scoreboard);
    }

    public void createIngameScoreboard(Player p, GameUser user) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("sidebar", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§6§l *** TheWalls ***");

        Objective healthObj = scoreboard.registerNewObjective("showhealth", "health");
        healthObj.setDisplaySlot(DisplaySlot.BELOW_NAME);
        healthObj.setDisplayName(" §c§l❤");
        p.setHealth(p.getHealth());

        Objective tablistobj = scoreboard.registerNewObjective("tablist", "dummy");
        tablistobj.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        //addTabListEntry(scoreboard, p.getName(), " §7"); //color tablist?

        addEntry(scoreboard, objective, "§§", "", 9);
        addEntry(scoreboard, objective, team1Label, "0", 8);
        addEntry(scoreboard, objective, team2Label, "0", 7);
        addEntry(scoreboard, objective, team3Label, "0", 6);
        addEntry(scoreboard, objective, team4Label, "0", 5);
        addEntry(scoreboard, objective, "§   ", "", 4);
        addEntry(scoreboard, objective, this.messageManager.getMessage(user.getLanguage(), "thewalls.scoreboard.kills"), String.valueOf(0), 3);
        addEntry(scoreboard, objective, this.messageManager.getMessage(user.getLanguage(), "thewalls.scoreboard.money"), String.valueOf(user.getMoney()), 2);
        //addEntry(scoreboard, objective, "§bTEAM2§6", "0", 3);
        //addEntry(scoreboard, objective, "§cTEAM3§6", "0", 2);
        //addEntry(scoreboard, objective, "§eTEAM4§6", "0", 2);
        addEntry(scoreboard, objective, "§    ", "", 1);
        String websiteInfo = this.messageManager.getMessage(user.getLanguage(), "scoreboard.website.address");
        addEntry(scoreboard, objective, websiteInfo, "", 0);
        Team t1 = scoreboard.registerNewTeam("team1");
        t1.setPrefix("§a");
        Team t2 = scoreboard.registerNewTeam("team2");
        t2.setPrefix("§b");
        Team t3 = scoreboard.registerNewTeam("team3");
        t3.setPrefix("§c");
        Team t4 = scoreboard.registerNewTeam("team4");
        t4.setPrefix("§e");
        p.setScoreboard(scoreboard);
    }

    public void createJoinSpectatorScoreboard(Player p, GameUser gameUser) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("sidebar", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§6§l *** TheWalls ***");

        addEntry(scoreboard, objective, "§§", "", 9);
        addEntry(scoreboard, objective, team1Label, "0", 8);
        addEntry(scoreboard, objective, team2Label, "0", 7);
        addEntry(scoreboard, objective, team3Label, "0", 6);
        addEntry(scoreboard, objective, team4Label, "0", 5);
        addEntry(scoreboard, objective, "§   ", "", 4);
        addEntry(scoreboard, objective, "§    ", "", 1);

        String websiteInfo = this.messageManager.getMessage(gameUser.getLanguage(), "scoreboard.website.address");
        addEntry(scoreboard, objective, websiteInfo, "", 0);

        Objective tablistobj = scoreboard.registerNewObjective("tablist", "dummy");
        tablistobj.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        Team t1 = scoreboard.registerNewTeam("team1");
        t1.setPrefix("§a");
        Team t2 = scoreboard.registerNewTeam("team2");
        t2.setPrefix("§b");
        Team t3 = scoreboard.registerNewTeam("team3");
        t3.setPrefix("§c");
        Team t4 = scoreboard.registerNewTeam("team4");
        t4.setPrefix("§e");
        p.setScoreboard(scoreboard);
        System.out.print(
                "DDDD spect"
        );
        plugin.getScoreboardAPI().refreshTags(p);
    }

    public void refreshTags(Player p) {
        Scoreboard sc = p.getScoreboard();
        Team t1 = sc.getTeam("team1");
        t1.setPrefix("§a");
        Team t2 = sc.getTeam("team2");
        t2.setPrefix("§b");
        Team t3 = sc.getTeam("team3");
        t3.setPrefix("§c");
        Team t4 = sc.getTeam("team4");
        t4.setPrefix("§e");
        Set<Map.Entry<String, GameUser>> arenaUsers = gameData.getArenaUsers();
        for (Map.Entry<String, GameUser> entry : arenaUsers) {
            GameUser user = entry.getValue();
            if (user.isSpectator()) {
                continue;
            }
            String key = entry.getKey();
            GameData.GameTeam assignedTeam = user.getAssignedTeam();
            if (assignedTeam.equals(GameData.GameTeam.TEAM1)) {
                t1.addEntry(key);
            } else if (assignedTeam.equals(GameData.GameTeam.TEAM2)) {
                t2.addEntry(key);
            } else if (assignedTeam.equals(GameData.GameTeam.TEAM3)) {
                t3.addEntry(key);
            } else if (assignedTeam.equals(GameData.GameTeam.TEAM4)) {
                t4.addEntry(key);
            }
        }

    }

    public Team addEntry(Scoreboard scoreboard, Objective objective, String name, String value, int position) {
        Team t = scoreboard.registerNewTeam(name);
        t.setPrefix(" §4❤  ");
        //t.setPrefix(" §0∙ ");
        t.addEntry(name);
        t.setSuffix(value);
        Score score = objective.getScore(name);
        score.setScore(position);
        return t;
    }

    public Team addTabListEntry(Scoreboard scoreboard, String name, String value) {
        Team t = scoreboard.registerNewTeam(name);
        t.setPrefix(value);
        //t.setPrefix(" §0∙ ");
        t.addEntry(name);
        t.setSuffix(" ");
        return t;
    }

    public void updateTabListEntry(Scoreboard scoreboard, String name, String value) {
        Team t = scoreboard.getTeam(name);
        t.setPrefix(value);
    }

    public void updateEntry(Scoreboard scoreboard, String name, int value) {
        Team t = scoreboard.getTeam(name);
        t.setSuffix(" " + value);
    }

    public void updateIncreaseEntry(Scoreboard scoreboard, String name, int value) {
        Team t = scoreboard.getTeam(name);
        int val = Integer.parseInt(t.getSuffix());
        t.setSuffix(String.valueOf(val + value));
    }

    public void refreshIngameScoreboard(int team1, int team2, int team3, int team4) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Scoreboard scoreboard = p.getScoreboard();
            updateIncreaseEntry(scoreboard, this.team1Label, team1);
            updateIncreaseEntry(scoreboard, this.team2Label, team2);
            updateIncreaseEntry(scoreboard, this.team3Label, team3);
            updateIncreaseEntry(scoreboard, this.team4Label, team4);
        }
    }

    private void updateEntry(Scoreboard scoreboard, String name, String value) {
        Team t = scoreboard.getTeam(name);
        t.setSuffix(" " + value);
    }

    public void removeEntry(Scoreboard scoreboard, String name) {
        Team t = scoreboard.getTeam(name);
        t.unregister();
        scoreboard.resetScores(name);
    }

    public void colorTabListPlayer(Scoreboard scoreboard, String name, String color) {
        updateTabListEntry(scoreboard, name, color);
    }

    public void updateDisplayName(int time, Player p) {
        Scoreboard scoreboard = p.getScoreboard();
        Objective sidebar = scoreboard.getObjective(DisplaySlot.SIDEBAR);
        String formattedTime = formatIntoHHMMSS(time);
        int arenaNumber = Bukkit.getPort() % 10;
        int numberOfPlayers = this.gameData.getNumberOfPlayers();
        int maxPlayers = Bukkit.getMaxPlayers();
        String displayName = "§a" + formattedTime + " §6TheWalls #" + arenaNumber + " §a" + numberOfPlayers + "/" + maxPlayers;
        sidebar.setDisplayName(displayName);
    }

    static String formatIntoHHMMSS(int secsIn) {

        int remainder = secsIn % 3600,
                minutes = remainder / 60,
                seconds = remainder % 60;

        return ((minutes < 10 ? "0" : "") + minutes
                + ":" + (seconds < 10 ? "0" : "") + seconds);

    }

    public void addKillForTeam(GameData.GameTeam killerTeam) {
        System.out.print("killer user ma " + killerTeam);
        if (killerTeam.equals(GameData.GameTeam.TEAM1)) {
            refreshIngameScoreboard(1, 0, 0, 0);
        } else if (killerTeam.equals(GameData.GameTeam.TEAM2)) {
            refreshIngameScoreboard(0, 1, 0, 0);
        } else if (killerTeam.equals(GameData.GameTeam.TEAM3)) {
            refreshIngameScoreboard(0, 0, 1, 0);
        } else if (killerTeam.equals(GameData.GameTeam.TEAM4)) {
            refreshIngameScoreboard(0, 0, 0, 1);
        }
    }
}
