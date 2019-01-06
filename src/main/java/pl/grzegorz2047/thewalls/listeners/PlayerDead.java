package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameData.GameStatus;
import pl.grzegorz2047.thewalls.GameUser;
import pl.grzegorz2047.thewalls.TheWalls;
import pl.grzegorz2047.thewalls.WorldManagement;
import pl.grzegorz2047.thewalls.api.util.ColoringUtil;

/**
 * Created by grzeg on 16.05.2016.
 */
public class PlayerDead implements Listener {

    private final TheWalls plugin;

    public PlayerDead(TheWalls plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDead(PlayerDeathEvent e) {
        e.setDeathMessage("");
        final Player p = e.getEntity();


        if (plugin.getGameData().getStatus().equals(GameStatus.WAITING)) {
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    if (p != null) {
                        p.setHealth(20);
                        Location spawn = new Location(plugin.getGameData().getWorldManagement().getLoadedWorld(), 0, 147, 0);
                        p.teleport(spawn);
                    }
                }
            }, 1l);
        } else if (plugin.getGameData().getStatus().equals(GameStatus.INGAME)) {
            plugin.getStatsManager().increaseValueBy(p.getName(), "deaths", 1);
            plugin.getStatsManager().increaseValueBy(p.getName(), "lose", 1);
            p.setHealth(20);
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    p.setHealth(20);
                    GameUser user = plugin.getGameData().getGameUsers().get(p.getName());
                    ColoringUtil.colorPlayerTab(p, "§7");
                    plugin.getGameData().makePlayerSpectator(user, p, plugin.getGameData().getWorldManagement().getLoadedWorld().getName());
                    plugin.getGameData().checkWinners();
                }
            }, 1l);
            Player killer = e.getEntity().getKiller();
            if (killer != null) {
                GameUser killerUser = plugin.getGameData().getGameUsers().get(killer.getName());
                killer.sendMessage(plugin.getMessageManager().getMessage(killerUser.getLanguage(), "thewalls.msg.givediamondforkill"));

                killer.getInventory().addItem(new ItemStack(Material.DIAMOND, 1));
                killerUser.increaseIngameKills(1);
                plugin.getStatsManager().increaseValueBy(killer.getName(), "kills", 1);
                plugin.getScoreboardAPI().updateEntry(killer.getScoreboard(), plugin.getMessageManager().getMessage(killerUser.getLanguage(), "thewalls.scoreboard.kills"), killerUser.getIngameKills());

                try {
                    System.out.print("killer user ma " + killerUser.getAssignedTeam());
                    if (killerUser.getAssignedTeam().equals(GameData.GameTeam.TEAM1)) {
                        plugin.getScoreboardAPI().refreshIngameScoreboard(1, 0, 0, 0);
                    } else if (killerUser.getAssignedTeam().equals(GameData.GameTeam.TEAM2)) {
                        plugin.getScoreboardAPI().refreshIngameScoreboard(0, 1, 0, 0);
                    } else if (killerUser.getAssignedTeam().equals(GameData.GameTeam.TEAM3)) {
                        plugin.getScoreboardAPI().refreshIngameScoreboard(0, 0, 1, 0);
                    } else if (killerUser.getAssignedTeam().equals(GameData.GameTeam.TEAM4)) {
                        plugin.getScoreboardAPI().refreshIngameScoreboard(0, 0, 0, 1);
                    }
                } catch (NullPointerException ex) {
                    System.out.print(ex.getMessage());
                }

                if (!killerUser.getRank().equals("Gracz")) {
                    plugin.getMoneyManager().changePlayerMoney(killer.getName(), plugin.getGameData().getMoneyForKill() * 2);
                    killer.sendMessage(plugin.getMessageManager().getMessage(killerUser.getLanguage(), "thewalls.msg.moneyforkill").replace("{MONEY}", String.valueOf(plugin.getGameData().getMoneyForKill() * 2)));
                    plugin.getScoreboardAPI().updateEntry(killer.getScoreboard(), plugin.getMessageManager().getMessage(killerUser.getLanguage(), "thewalls.scoreboard.money"), plugin.getGameData().getMoneyForKill() * 2);

                } else {
                    plugin.getMoneyManager().changePlayerMoney(killer.getName(), plugin.getGameData().getMoneyForKill());
                    killer.sendMessage(plugin.getMessageManager().getMessage(killerUser.getLanguage(), "thewalls.msg.moneyforkill").replace("{MONEY}", String.valueOf(plugin.getGameData().getMoneyForKill())));
                    plugin.getScoreboardAPI().updateEntry(killer.getScoreboard(), plugin.getMessageManager().getMessage(killerUser.getLanguage(), "thewalls.scoreboard.money"), plugin.getGameData().getMoneyForKill());

                }
                plugin.getMoneyManager().changePlayerMoney(killer.getName(), plugin.getGameData().getMoneyForKill());
                plugin.getPlayerManager().changePlayerExp(killer.getName(), plugin.getGameData().getExpForKill());
                EntityDamageEvent reason = p.getLastDamageCause();
                if (reason.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || reason.getCause() == EntityDamageEvent.DamageCause.CUSTOM) {
                    //e.setDeathMessage("§7" + killer.getName() + "§0§l ❤ §r§7 " + p.getName() + " §7Item: §c" + killer.getItemInHand().getType().name());
                    e.setDeathMessage("§2✪ §a" + killer.getName() + " §4✖ §c" + p.getName() + " §6§l⚔ §7" + killer.getItemInHand().getType().name());
                } else if (reason.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                    e.setDeathMessage("§2✪ §a" + killer.getName() + " §4✖ §c" + p.getName() + " §6§l⚔ §7" + killer.getItemInHand().getType().name());

                }
            } else {
                e.setDeathMessage("§4 ✖ §7" + p.getName());
                e.getEntity().teleport(e.getEntity().getLocation().getWorld().getSpawnLocation());
                e.getEntity().setGameMode(GameMode.SPECTATOR);
            }
        }
    }

}
