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
import pl.grzegorz2047.thewalls.GameUsers;
import pl.grzegorz2047.thewalls.drop.BlockDrop;

import java.util.Map;

/**
 * Created by grzeg on 17.05.2016.
 */
public class BlockBreak implements Listener {

    private final GameData gameData;
    private final MessageAPI messageManager;
    private final Map<Material, BlockDrop> dropsMap;
    private GameUsers gameUsers;
    private StorageProtection storageProtection;

    public BlockBreak(GameData gameData, MessageAPI messageManager, Map<Material, BlockDrop> dropsMap, GameUsers gameUsers, StorageProtection storageProtection) {
        this.gameData = gameData;
        this.messageManager = messageManager;
        this.dropsMap = dropsMap;
        this.gameUsers = gameUsers;
        this.storageProtection = storageProtection;
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
            GameUser user = gameUsers.getGameUser(username);
            String language = user.getLanguage();
            Location blockLocation = block.getLocation();
            if (!storageProtection.isFurnaceOwner(username, blockLocation)) {
                p.sendMessage(messageManager.getMessage(language, "thewalls.msg.furnacenotyours"));
                e.setCancelled(true);
            } else {
                storageProtection.removeFurnaceProtection(user, blockLocation);
                p.sendMessage(messageManager.getMessage(language, "thewalls.msg.furnacenolongerprotected"));
            }
        } else {
            BlockDrop blockDrop = dropsMap.get(type);
            if (blockDrop != null)
                blockDrop.dropItems(e);
        }
    }


}
