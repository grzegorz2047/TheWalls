package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameData.GameStatus;

/**
 * Created by grzeg on 16.05.2016.
 */
public class PlayerDead implements Listener {

     private final GameData gameData;
    private Plugin plugin;


    public PlayerDead(GameData gameData, Plugin plugin) {
        this.gameData = gameData;
        this.plugin = plugin;
    }

    @EventHandler
    public void onDead(PlayerDeathEvent e) {
        e.setDeathMessage("");
        final Player killed = e.getEntity();
        final Player killer = killed.getKiller();


        if (gameData.isStatus(GameStatus.WAITING)) {
            gameData.handleWeirdDeath(killed);
        } else if (gameData.isStatus(GameStatus.INGAME)) {
            final String killedPlayerName = gameData.handleKilledPerson(killed);
            if (killer != null) {
                gameData.handleKiller(killer);
                messageDeath(e, killed, killer, killedPlayerName);
            } else {
                handleNoKiller(e, killed, killedPlayerName, killed.getLocation().getWorld(), plugin);
            }
        }
    }

    private void messageDeath(PlayerDeathEvent e, Player killed, Player killer, String killedPlayerName) {
        EntityDamageEvent reason = killed.getLastDamageCause();
        ItemStack itemInHand = killer.getItemInHand();
        Material itemInHandType = itemInHand.getType();
        String itemInHandTypeName = itemInHandType.name();
        EntityDamageEvent.DamageCause damageCause = reason.getCause();
        String killerName = killer.getName();
        if (damageCause == EntityDamageEvent.DamageCause.ENTITY_ATTACK || damageCause == EntityDamageEvent.DamageCause.CUSTOM) {
            //e.setDeathMessage("§7" + killer.getName() + "§0§l ❤ §r§7 " + killed.getName() + " §7Item: §c" + killer.getItemInHand().getType().name());
            e.setDeathMessage("§2✪ §a" + killerName + " §4✖ §c" + killedPlayerName + " §6§l⚔ §7" + itemInHandTypeName);
        } else if (damageCause == EntityDamageEvent.DamageCause.PROJECTILE) {
            e.setDeathMessage("§2✪ §a" + killerName + " §4✖ §c" + killedPlayerName + " §6§l⚔ §7" + itemInHandTypeName);
        }
    }

    private void handleNoKiller(PlayerDeathEvent e, Player killed, String killedPlayerName, World world, Plugin plugin) {
        e.setDeathMessage("§4 ✖ §7" + killedPlayerName);
        Bukkit.getScheduler().runTaskLater(plugin, (Runnable) () -> {
            killed.spigot().respawn();
            killed.teleport(world.getSpawnLocation());
            killed.setGameMode(GameMode.SPECTATOR);
        }, 1L);
    }



}
