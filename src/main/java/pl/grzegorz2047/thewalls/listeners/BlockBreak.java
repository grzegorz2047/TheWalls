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

    private final GameData gameData;
    private final MessageAPI messageManager;

    public BlockBreak(GameData gameData, MessageAPI messageManager) {
        this.gameData = gameData;
        this.messageManager = messageManager;
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
            String username = p.getName();
            GameUser user = gameData.getGameUser(username);
            String language = user.getLanguage();
            Location blockLocation = block.getLocation();
            if (!isFurnaceOwner(username, blockLocation)) {
                p.sendMessage(messageManager.getMessage(language, "thewalls.msg.furnacenotyours"));
                e.setCancelled(true);
            } else {
                gameData.removeFurnaceProtection(user, blockLocation);
                p.sendMessage(messageManager.getMessage(language, "thewalls.msg.furnacenolongerprotected"));
            }
        }
    }

    private boolean isFurnaceOwner(String username, Location blockLocation) {
        return gameData.getPlayerProtectedFurnace(blockLocation).equals(username);
    }


}
