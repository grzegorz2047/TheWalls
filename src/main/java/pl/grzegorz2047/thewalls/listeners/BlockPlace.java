package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import pl.grzegorz2047.thewalls.Counter;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameUser;
import pl.grzegorz2047.thewalls.TheWalls;

import java.util.Arrays;
import java.util.List;

/**
 * Created by grzeg on 17.05.2016.
 */
public class BlockPlace implements Listener {

    private final TheWalls plugin;
    private final GameData gameData;
    private final Counter counter;

    public BlockPlace(TheWalls plugin) {
        this.plugin = plugin;
        gameData = plugin.getGameData();
        counter = gameData.getCounter();
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();

        List<Material> list = Arrays.asList(Material.FIRE, Material.TNT, Material.PISTON_BASE, Material.BEDROCK);
        Counter.CounterStatus counterStatus = counter.getStatus();
        Block block = e.getBlock();
        Material blockType = block.getType();
        if (counterStatus.equals(Counter.CounterStatus.COUNTINGTODROPWALLS)) {

            String username = p.getName();
            if (list.contains(blockType)) {
                GameUser user = gameData.getGameUser(username);
                e.getPlayer().sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.msg.cantuseitnow"));
                e.setCancelled(true);
                return;
            }
            if (blockType.equals(Material.FURNACE)) {
                GameUser user = gameData.getGameUser(username);
                if (user.getProtectedFurnaces() >= 3) {
                    e.getPlayer().sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.msg.furnacenotprotected"));
                    return;
                } else {
                    gameData.getProtectedFurnaces().put(block.getLocation(), username);
                    e.getPlayer().sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.msg.furnacenowprotected"));
                    user.setProtectedFurnaces(user.getProtectedFurnaces() + 1);
                    return;
                }
            }
        } else if (!counterStatus.equals(Counter.CounterStatus.DEATHMATCH)) {
            Block b = block;
            Entity ent = null;
            if (blockType == Material.TNT) {
                ent = b.getWorld().spawn(b.getLocation(), TNTPrimed.class);
                ((TNTPrimed) ent).setFuseTicks(20);
                block.getWorld().createExplosion(block.getLocation().getX(), block.getLocation().getY(), block.getLocation().getZ(), 1, true, false);
            }
        }
    }
}
