package pl.grzegorz2047.thewalls.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import pl.grzegorz2047.databaseapi.messages.MessageAPI;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameUser;
import pl.grzegorz2047.thewalls.GameUsers;

import java.util.Map;

/**
 * Created by s416045 on 2016-04-19.
 */
public class ScoreboardAPI {

    private final MessageAPI messageManager;
    private final GameData gameData;
    private final String team1Name = "team1";
    private final String team2Name = "team2";
    private final String team3Name = "team3";
    private final String team4Name = "team4";


    public ScoreboardAPI(MessageAPI messageManager, GameData gameData) {
        this.messageManager = messageManager;

        this.gameData = gameData;
    }


    public void createWaitingScoreboard(Player p, GameUser gameUser) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        int money = gameUser.getMoney();
        int kills = gameUser.getKills();
        int deaths = gameUser.getDeaths();
        int wins = gameUser.getWins();
        int lose = gameUser.getLose();
        String userLanguage = gameUser.getLanguage();
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
        createTeamTags(scoreboard);
        p.setScoreboard(scoreboard);
    }

    public void createIngameScoreboard(Player p, GameUser user) {
        String team1Label = messageManager.getMessage(user.getLanguage(), "thewalls.scoreboard.ingame.TEAM1");
        String team2Label = this.messageManager.getMessage(user.getLanguage(), "thewalls.scoreboard.ingame.TEAM2");
        String team3Label = this.messageManager.getMessage(user.getLanguage(), "thewalls.scoreboard.ingame.TEAM3");
        String team4Label = this.messageManager.getMessage(user.getLanguage(), "thewalls.scoreboard.ingame.TEAM4");
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("sidebar", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§6§l *** TheWalls ***");

        Objective healthObj = scoreboard.registerNewObjective("showhealth", "health");
        healthObj.setDisplaySlot(DisplaySlot.BELOW_NAME);
        healthObj.setDisplayName(" §c§l❤");
        p.setHealth(p.getHealth());
        p.damage(0);

        Objective tablistobj = scoreboard.registerNewObjective("tablist", "dummy");
        tablistobj.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        //addTabListEntry(scoreboard, p.getName(), " §7"); //color tablist?

        addEntry(scoreboard, objective, "§§", "", 9);
        addEntry(scoreboard, objective, team1Label, "§60", 8);
        addEntry(scoreboard, objective, team2Label, "§60", 7);
        addEntry(scoreboard, objective, team3Label, "§60", 6);
        addEntry(scoreboard, objective, team4Label, "§60", 5);
        addEntry(scoreboard, objective, "§   ", "", 4);
        String language = user.getLanguage();
        addEntry(scoreboard, objective, this.messageManager.getMessage(language, "thewalls.scoreboard.kills"), String.valueOf(0), 3);
        addEntry(scoreboard, objective, this.messageManager.getMessage(language, "thewalls.scoreboard.money"), String.valueOf(user.getMoney()), 2);
        //addEntry(scoreboard, objective, "§bTEAM2§6", "0", 3);
        //addEntry(scoreboard, objective, "§cTEAM3§6", "0", 2);
        //addEntry(scoreboard, objective, "§eTEAM4§6", "0", 2);
        addEntry(scoreboard, objective, "§    ", "", 1);
        String websiteInfo = this.messageManager.getMessage(language, "scoreboard.website.address");
        addEntry(scoreboard, objective, websiteInfo, "", 0);
        createTeamTags(scoreboard);
        p.setScoreboard(scoreboard);
    }

    private void createTeamTags(Scoreboard scoreboard) {
        Team t1 = scoreboard.registerNewTeam(team1Name);
        t1.setColor(ChatColor.GREEN);
        Team t2 = scoreboard.registerNewTeam(team2Name);
        t2.setColor(ChatColor.BLUE);
        Team t3 = scoreboard.registerNewTeam(team3Name);
        t3.setColor(ChatColor.RED);
        Team t4 = scoreboard.registerNewTeam(team4Name);
        t4.setColor(ChatColor.YELLOW);
    }

    public void createJoinSpectatorScoreboard(Player p, GameUser user, GameUsers gameUsers) {
        String team1Label = messageManager.getMessage(user.getLanguage(), "thewalls.scoreboard.ingame.TEAM1");
        String team2Label = this.messageManager.getMessage(user.getLanguage(), "thewalls.scoreboard.ingame.TEAM2");
        String team3Label = this.messageManager.getMessage(user.getLanguage(), "thewalls.scoreboard.ingame.TEAM3");
        String team4Label = this.messageManager.getMessage(user.getLanguage(), "thewalls.scoreboard.ingame.TEAM4");
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("sidebar", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§6§l *** TheWalls ***");

        addEntry(scoreboard, objective, "§§", "", 9);
        addEntry(scoreboard, objective, team1Label, "§60", 8);
        addEntry(scoreboard, objective, team2Label, "§60", 7);
        addEntry(scoreboard, objective, team3Label, "§60", 6);
        addEntry(scoreboard, objective, team4Label, "§60", 5);
        addEntry(scoreboard, objective, "§   ", "", 4);
        addEntry(scoreboard, objective, "§    ", "", 1);

        String websiteInfo = this.messageManager.getMessage(user.getLanguage(), "scoreboard.website.address");
        addEntry(scoreboard, objective, websiteInfo, "", 0);

        Objective tablistobj = scoreboard.registerNewObjective("tablist", "dummy");
        tablistobj.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        createTeamTags(scoreboard);
        p.setScoreboard(scoreboard);
        refreshTags(p, gameUsers);
    }

    public void refreshTags(Player p, GameUsers gameUsers) {
        Scoreboard sc = p.getScoreboard();
        Team t1 = sc.getTeam(team1Name);
        Team t2 = sc.getTeam(team2Name);
        Team t3 = sc.getTeam(team3Name);
        Team t4 = sc.getTeam(team4Name);

        for (Map.Entry<String, GameUser> entry : gameUsers.getArenaUsers()) {
            GameUser user = entry.getValue();
            if (user.isSpectator()) {
                continue;
            }
            String userName = entry.getKey();
            GameData.GameTeam assignedTeam = user.getAssignedTeam();
            if (assignedTeam.equals(GameData.GameTeam.TEAM1)) {
                t1.addEntry(userName);
            } else if (assignedTeam.equals(GameData.GameTeam.TEAM2)) {
                t2.addEntry(userName);
            } else if (assignedTeam.equals(GameData.GameTeam.TEAM3)) {
                t3.addEntry(userName);
            } else if (assignedTeam.equals(GameData.GameTeam.TEAM4)) {
                t4.addEntry(userName);
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
        int val = Integer.parseInt(t.getSuffix().replace("§6", ""));
        t.setSuffix("§6" + (val + value));
    }

    public void refreshIngameScoreboard(int team1, int team2, int team3, int team4, GameUsers gameUsers) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Scoreboard scoreboard = p.getScoreboard();
            GameUser user = gameUsers.getGameUser(p.getName());
            String team1Label = messageManager.getMessage(user.getLanguage(), "thewalls.scoreboard.ingame.TEAM1");
            String team2Label = this.messageManager.getMessage(user.getLanguage(), "thewalls.scoreboard.ingame.TEAM2");
            String team3Label = this.messageManager.getMessage(user.getLanguage(), "thewalls.scoreboard.ingame.TEAM3");
            String team4Label = this.messageManager.getMessage(user.getLanguage(), "thewalls.scoreboard.ingame.TEAM4");
            updateIncreaseEntry(scoreboard, team1Label, team1);
            updateIncreaseEntry(scoreboard, team2Label, team2);
            updateIncreaseEntry(scoreboard, team3Label, team3);
            updateIncreaseEntry(scoreboard, team4Label, team4);
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

    public void updateDisplayName(int time, Player p, int numberOfPlayers) {
        Scoreboard scoreboard = p.getScoreboard();
        Objective sidebar = scoreboard.getObjective(DisplaySlot.SIDEBAR);
        String formattedTime = formatIntoHHMMSS(time);
        int arenaNumber = (Bukkit.getPort() - 65) % 10;
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

    public void addKillForTeam(GameData.GameTeam killerTeam, GameUsers gameUsers) {
        System.out.print("killer user ma " + killerTeam);
        if (killerTeam.equals(GameData.GameTeam.TEAM1)) {
            refreshIngameScoreboard(1, 0, 0, 0, gameUsers);
        } else if (killerTeam.equals(GameData.GameTeam.TEAM2)) {
            refreshIngameScoreboard(0, 1, 0, 0, gameUsers);
        } else if (killerTeam.equals(GameData.GameTeam.TEAM3)) {
            refreshIngameScoreboard(0, 0, 1, 0, gameUsers);
        } else if (killerTeam.equals(GameData.GameTeam.TEAM4)) {
            refreshIngameScoreboard(0, 0, 0, 1, gameUsers);
        }
    }
}
