package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scoreboard.Scoreboard;
import pl.grzegorz2047.databaseapi.DatabaseAPI;
import pl.grzegorz2047.databaseapi.MoneyAPI;
import pl.grzegorz2047.databaseapi.StatsAPI;
import pl.grzegorz2047.databaseapi.messages.MessageAPI;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameData.GameStatus;
import pl.grzegorz2047.thewalls.GameUser;
import pl.grzegorz2047.thewalls.TheWalls;
import pl.grzegorz2047.thewalls.WorldManagement;
import pl.grzegorz2047.thewalls.api.util.ColoringUtil;
import pl.grzegorz2047.thewalls.scoreboard.ScoreboardAPI;

/**
 * Created by grzeg on 16.05.2016.
 */
public class PlayerDead implements Listener {

    private final TheWalls plugin;
    private final GameData gameData;
    private final StatsAPI statsManager;
    private final ScoreboardAPI scoreboardAPI;
    private final MessageAPI messageManager;
    private final MoneyAPI moneyManager;
    private final DatabaseAPI playerManager;
    private final WorldManagement worldManagement;

    public PlayerDead(TheWalls plugin) {
        this.plugin = plugin;
        gameData = plugin.getGameData();
        statsManager = plugin.getStatsManager();
        scoreboardAPI = plugin.getScoreboardAPI();
        messageManager = plugin.getMessageManager();
        moneyManager = this.plugin.getMoneyManager();
        playerManager = plugin.getPlayerManager();
        worldManagement = gameData.getWorldManagement();
    }

    @EventHandler
    public void onDead(PlayerDeathEvent e) {
        e.setDeathMessage("");
        Player entity = e.getEntity();
        final Player p = entity;


        final World loadedWorld = worldManagement.getLoadedWorld();
        if (gameData.isStatus(GameStatus.WAITING)) {
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    if (p != null) {
                        p.setHealth(20);
                        Location spawn = new Location(loadedWorld, 0, 147, 0);
                        p.teleport(spawn);
                    }
                }
            }, 1l);
        } else if (gameData.isStatus(GameStatus.INGAME)) {
            final String killedPlayerName = p.getName();
            statsManager.increaseValueBy(killedPlayerName, "deaths", 1);
            statsManager.increaseValueBy(killedPlayerName, "lose", 1);
            p.setHealth(20);
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    p.setHealth(20);
                    GameUser user = gameData.getGameUser(killedPlayerName);
                    ColoringUtil.colorPlayerTab(p, "§7");
                    gameData.makePlayerSpectator(user, p, loadedWorld.getName());
                    gameData.checkWinners();
                }
            }, 1l);
            Player killer = entity.getKiller();
            if (killer != null) {
                String killerName = killer.getName();
                GameUser killerUser = gameData.getGameUser(killerName);
                String killerLanguage = killerUser.getLanguage();
                killer.sendMessage(messageManager.getMessage(killerLanguage, "thewalls.msg.givediamondforkill"));

                PlayerInventory inventory = killer.getInventory();
                inventory.addItem(new ItemStack(Material.DIAMOND, 1));
                killerUser.increaseIngameKills(1);
                statsManager.increaseValueBy(killerName, "kills", 1);
                Scoreboard killerScoreboard = killer.getScoreboard();
                scoreboardAPI.updateEntry(killerScoreboard, messageManager.getMessage(killerLanguage, "thewalls.scoreboard.kills"), killerUser.getIngameKills());

                try {
                    GameData.GameTeam killerTeam = killerUser.getAssignedTeam();
                    System.out.print("killer user ma " + killerTeam);
                    if (killerTeam.equals(GameData.GameTeam.TEAM1)) {
                        scoreboardAPI.refreshIngameScoreboard(1, 0, 0, 0);
                    } else if (killerTeam.equals(GameData.GameTeam.TEAM2)) {
                        scoreboardAPI.refreshIngameScoreboard(0, 1, 0, 0);
                    } else if (killerTeam.equals(GameData.GameTeam.TEAM3)) {
                        scoreboardAPI.refreshIngameScoreboard(0, 0, 1, 0);
                    } else if (killerTeam.equals(GameData.GameTeam.TEAM4)) {
                        scoreboardAPI.refreshIngameScoreboard(0, 0, 0, 1);
                    }
                } catch (NullPointerException ex) {
                    System.out.print(ex.getMessage());
                }

                String killerRank = killerUser.getRank();
                int moneyForKill = gameData.getMoneyForKill();
                if (!killerRank.equals("Gracz")) {
                    moneyManager.changePlayerMoney(killerName, moneyForKill * 2);
                    killer.sendMessage(messageManager.getMessage(killerLanguage, "thewalls.msg.moneyforkill").replace("{MONEY}", String.valueOf(moneyForKill * 2)));
                    scoreboardAPI.updateEntry(killerScoreboard, messageManager.getMessage(killerLanguage, "thewalls.scoreboard.money"), moneyForKill * 2);

                } else {
                    moneyManager.changePlayerMoney(killerName, moneyForKill);
                    killer.sendMessage(messageManager.getMessage(killerLanguage, "thewalls.msg.moneyforkill").replace("{MONEY}", String.valueOf(moneyForKill)));
                    scoreboardAPI.updateEntry(killerScoreboard, messageManager.getMessage(killerLanguage, "thewalls.scoreboard.money"), moneyForKill);

                }
                moneyManager.changePlayerMoney(killerName, moneyForKill);
                int expForKill = gameData.getExpForKill();
                playerManager.changePlayerExp(killerName, expForKill);
                EntityDamageEvent reason = p.getLastDamageCause();
                ItemStack itemInHand = killer.getItemInHand();
                Material itemInHandType = itemInHand.getType();
                String itemInHandTypeName = itemInHandType.name();
                EntityDamageEvent.DamageCause damageCause = reason.getCause();
                if (damageCause == EntityDamageEvent.DamageCause.ENTITY_ATTACK || damageCause == EntityDamageEvent.DamageCause.CUSTOM) {
                    //e.setDeathMessage("§7" + killer.getName() + "§0§l ❤ §r§7 " + p.getName() + " §7Item: §c" + killer.getItemInHand().getType().name());
                    e.setDeathMessage("§2✪ §a" + killerName + " §4✖ §c" + killedPlayerName + " §6§l⚔ §7" + itemInHandTypeName);
                } else if (damageCause == EntityDamageEvent.DamageCause.PROJECTILE) {
                    e.setDeathMessage("§2✪ §a" + killerName + " §4✖ §c" + killedPlayerName + " §6§l⚔ §7" + itemInHandTypeName);

                }
            } else {
                e.setDeathMessage("§4 ✖ §7" + killedPlayerName);
                World world = entity.getLocation().getWorld();
                entity.teleport(world.getSpawnLocation());
                entity.setGameMode(GameMode.SPECTATOR);
            }
        }
    }

}
