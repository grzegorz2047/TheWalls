package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.WorldSaveEvent;
import pl.grzegorz2047.databaseapi.messages.MessageAPI;
import pl.grzegorz2047.thewalls.Counter;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameUser;

/**
 * Created by grzeg on 17.05.2016.
 */
public class GeneralBlocking implements Listener {


     private final GameData gameData;
    private final MessageAPI messageManager;
    private final Counter counter;

    public GeneralBlocking(GameData gameData, MessageAPI messageManager) {
        this.gameData = gameData;
        this.messageManager = messageManager;
        this.counter = this.gameData.getCounter();
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        if (!gameData.isStatus(GameData.GameStatus.INGAME)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent e) {
        if (e.getBucket() == Material.LAVA_BUCKET) {
            Player p = e.getPlayer();
            GameUser user = gameData.getGameUser(p.getName());
            if (counter.getStatus().equals(Counter.CounterStatus.COUNTINGTODROPWALLS)) {
                p.sendMessage(messageManager.getMessage(user.getLanguage(), "thewalls.msg.cantplacelava"));
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void tepEnderPearl(PlayerTeleportEvent e) {
        if (counter.getStatus().equals(Counter.CounterStatus.COUNTINGTODROPWALLS)) {
            if (e.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
                Player p = e.getPlayer();
                GameUser user = gameData.getGameUser(p.getName());
                e.getPlayer().sendMessage(messageManager.getMessage(user.getLanguage(), "thewalls.msg.cantuseitnow"));
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent e) {
        if (e.getEntity() instanceof LivingEntity) {
            e.setCancelled(false);
        }
    }

    @EventHandler
    public void onSpawn(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onWOrld(WorldSaveEvent e) {
        System.out.println("Ktos zapisje? >:<");
    }
}
