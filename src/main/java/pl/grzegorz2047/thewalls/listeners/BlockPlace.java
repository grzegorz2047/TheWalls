package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import pl.grzegorz2047.databaseapi.messages.MessageAPI;
import pl.grzegorz2047.thewalls.Counter;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameUser;

import java.util.Arrays;
import java.util.List;

/**
 * Created by grzeg on 17.05.2016.
 */
public class BlockPlace implements Listener {

    private final GameData gameData;
    private final Counter counter;
    private final MessageAPI messageManager;

    public BlockPlace(GameData gameData, MessageAPI messageManager) {
        this.gameData = gameData;
        counter = this.gameData.getCounter();
        this.messageManager = messageManager;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();

        List<Material> list = Arrays.asList(Material.FIRE, Material.TNT, Material.PISTON_HEAD, Material.BEDROCK);
        Counter.CounterStatus counterStatus = counter.getStatus();
        Block block = e.getBlock();
        Material blockType = block.getType();
        Location blockLocation = block.getLocation();
        if (counterStatus.equals(Counter.CounterStatus.COUNTINGTODROPWALLS)) {

            String username = player.getName();
            if (list.contains(blockType)) {
                GameUser user = gameData.getGameUser(username);
                player.sendMessage(messageManager.getMessage(user.getLanguage(), "thewalls.msg.cantuseitnow"));
                e.setCancelled(true);
                return;
            }
            if (blockType.equals(Material.FURNACE)) {
                GameUser user = gameData.getGameUser(username);
                String language = user.getLanguage();
                if (exceedsNumberOfProtectedFurnaces(user)) {
                    player.sendMessage(messageManager.getMessage(language, "thewalls.msg.furnacenotprotected"));
                    return;
                } else {
                    gameData.protectNewFurnace(blockLocation, username, user);
                    player.sendMessage(messageManager.getMessage(language, "thewalls.msg.furnacenowprotected"));
                    return;
                }
            }
        } else if (!counterStatus.equals(Counter.CounterStatus.DEATHMATCH)) {
            Block b = block;
            Entity ent = null;
            if (blockType == Material.TNT) {
                ent = b.getWorld().spawn(b.getLocation(), TNTPrimed.class);
                ((TNTPrimed) ent).setFuseTicks(20);
                block.getWorld().createExplosion(blockLocation.getX(), blockLocation.getY(), blockLocation.getZ(), 1, true, false);
            }
        }
    }



    private boolean exceedsNumberOfProtectedFurnaces(GameUser user) {
        return user.getProtectedFurnaces() >= 3;
    }
}
