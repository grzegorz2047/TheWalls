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
import pl.grzegorz2047.thewalls.GameUser;
import pl.grzegorz2047.thewalls.TheWalls;

import java.util.Arrays;
import java.util.List;

/**
 * Created by grzeg on 17.05.2016.
 */
public class BlockPlace implements Listener {

    private final TheWalls plugin;

    public BlockPlace(TheWalls plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void PrzyStawieniu(BlockPlaceEvent e) {
        Player p = e.getPlayer();

        List<Material> list = Arrays.asList(Material.FIRE, Material.TNT, Material.PISTON_BASE, Material.BEDROCK);
        if (plugin.getGameData().getCounter().getStatus().equals(Counter.CounterStatus.COUNTINGTODROPWALLS)) {

            if (list.contains(e.getBlock().getType())) {
                GameUser user = plugin.getGameData().getGameUser(p.getName());
                e.getPlayer().sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.msg.cantuseitnow"));
                e.setCancelled(true);
                return;
            }
            if (e.getBlock().getType().equals(Material.FURNACE)) {
                GameUser user = plugin.getGameData().getGameUser(p.getName());
                if (user.getProtectedFurnaces() >= 3) {
                    e.getPlayer().sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.msg.furnacenotprotected"));
                    return;
                } else {
                    plugin.getGameData().getProtectedFurnaces().put(e.getBlock().getLocation(), p.getName());
                    e.getPlayer().sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.msg.furnacenowprotected"));
                    user.setProtectedFurnaces(user.getProtectedFurnaces() + 1);
                    return;
                }
            }
        } else if (!plugin.getGameData().getCounter().getStatus().equals(Counter.CounterStatus.DEATHMATCH)) {
            Block b = e.getBlock();
            Entity ent = null;
            if (e.getBlock().getType() == Material.TNT) {
                ent = b.getWorld().spawn(b.getLocation(), TNTPrimed.class);
                ((TNTPrimed) ent).setFuseTicks(20);
                e.getBlock().getWorld().createExplosion(e.getBlock().getLocation().getX(), e.getBlock().getLocation().getY(), e.getBlock().getLocation().getZ(), 1, true, false);
            }
        }
    }
}
