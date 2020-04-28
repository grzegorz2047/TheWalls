package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import pl.grzegorz2047.databaseapi.messages.MessageAPI;
import pl.grzegorz2047.thewalls.GameUser;
import pl.grzegorz2047.thewalls.GameUsers;

import java.util.HashMap;

public class StorageProtection {

    HashMap<Location, String> protectedFurnaces = new HashMap<Location, String>();
    private GameUsers gameUsers;
    private final MessageAPI messageManager;

    public StorageProtection(GameUsers gameUsers, MessageAPI messageManager) {
        this.gameUsers = gameUsers;
        this.messageManager = messageManager;
    }

    public String getPlayerProtectedFurnace(Location location) {
        return protectedFurnaces.get(location);
    }

    public boolean isChestOwner(Player player, String playerName, Block clickedBlock) {
        String protectedFurnacePlayer = getPlayerProtectedFurnace(clickedBlock.getLocation());
        if (protectedFurnacePlayer != null) {
            if (!protectedFurnacePlayer.equals(playerName)) {
                GameUser user = gameUsers.getGameUser(playerName);
                String userLanguage = user.getLanguage();
                player.sendMessage(messageManager.getMessage(userLanguage, "thewalls.msg.someonesprotectedfurnace"));
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    public void removeFurnaceProtection(GameUser user, Location location) {
        protectedFurnaces.remove(location);
        user.setProtectedFurnaces(user.getProtectedFurnaces() - 1);
    }

    public boolean isFurnaceOwner(String username, Location blockLocation) {
        return getPlayerProtectedFurnace(blockLocation).equals(username);
    }

    public void protectNewFurnace(Location blockLocation, String username, GameUser user) {
        protectedFurnaces.put(blockLocation, username);
        user.setProtectedFurnaces(user.getProtectedFurnaces() + 1);
    }
}