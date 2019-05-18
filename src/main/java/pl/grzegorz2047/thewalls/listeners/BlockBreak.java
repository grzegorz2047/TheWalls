package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pl.grzegorz2047.databaseapi.messages.MessageAPI;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameUser;
import pl.grzegorz2047.thewalls.TheWalls;

import java.util.HashMap;

/**
 * Created by grzeg on 17.05.2016.
 */
public class BlockBreak implements Listener {

    private final TheWalls plugin;
    private final GameData gameData;
    private final MessageAPI messageManager;
    private final HashMap<String, GameUser> gameUsers;
    private final HashMap<Location, String> protectedFurnace;

    public BlockBreak(TheWalls plugin) {
        this.plugin = plugin;
        gameData = this.plugin.getGameData();
        messageManager = plugin.getMessageManager();
        gameUsers = gameData.getGameUsers();
        protectedFurnace = gameData.getProtectedFurnaces();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (!gameData.isStatus(GameData.GameStatus.INGAME)) {
            e.setCancelled(true);
            return;
        }
        Player p = e.getPlayer();
        Block block = e.getBlock();
        Material type = block.getType();
        if (type.equals(Material.FURNACE)) {
            String playerFurnace = protectedFurnace.get(block.getLocation());
            String username = p.getName();
            GameUser user = gameUsers.get(username);
            String language = user.getLanguage();
            if (!username.equals(playerFurnace)) {
                p.sendMessage(messageManager.getMessage(language, "thewalls.msg.furnacenotyours"));
                e.setCancelled(true);
                return;
            } else {
                protectedFurnace.remove(block.getLocation());
                user.setProtectedFurnaces(user.getProtectedFurnaces() - 1);
                p.sendMessage(messageManager.getMessage(language, "thewalls.msg.furnacenolongerprotected"));
            }


        }
    }

}
