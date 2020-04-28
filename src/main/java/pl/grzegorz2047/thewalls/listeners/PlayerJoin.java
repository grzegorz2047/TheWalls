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

    private final GameData gameData;


    public PlayerJoin(GameData gameData) {
        this.gameData = gameData;

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (e.getPlayer() == null) {
            return;
        }
        e.setJoinMessage(null);
        Player p = e.getPlayer();
        gameData.addPlayerToArena(p);
    }


}
