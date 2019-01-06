package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameUser;
import pl.grzegorz2047.thewalls.TheWalls;

/**
 * Created by grzeg on 17.05.2016.
 */
public class BlockBreak implements Listener {

    private final TheWalls plugin;

    public BlockBreak(TheWalls plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!plugin.getGameData().getStatus().equals(GameData.GameStatus.INGAME)) {
            e.setCancelled(true);
            return;
        }
        Player p = e.getPlayer();
        if (e.getBlock().getType().equals(Material.FURNACE)) {
            String playerFurnace = plugin.getGameData().getProtectedFurnace().get(e.getBlock().getLocation());
            if (!p.getName().equals(playerFurnace)) {
                GameUser user = plugin.getGameData().getGameUsers().get(p.getName());
                e.getPlayer().sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.msg.furnacenotyours"));
                e.setCancelled(true);
                return;
            } else {
                GameUser user = plugin.getGameData().getGameUsers().get(p.getName());
                plugin.getGameData().getProtectedFurnace().remove(e.getBlock().getLocation());
                user.setProtectedFurnaces(user.getProtectedFurnaces() - 1);
                e.getPlayer().sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.msg.furnacenolongerprotected"));
            }


        }
    }

}
