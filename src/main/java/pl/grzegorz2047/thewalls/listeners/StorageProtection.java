package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.grzegorz2047.databaseapi.messages.MessageAPI;
import pl.grzegorz2047.thewalls.GameUser;
import pl.grzegorz2047.thewalls.GameUsers;

import java.util.HashMap;
import java.util.Optional;

public class StorageProtection {

    HashMap<Location, String> protectedFurnaces = new HashMap<Location, String>();
    private GameUsers gameUsers;
    private final MessageAPI messageManager;

    public StorageProtection(GameUsers gameUsers, MessageAPI messageManager) {
        this.gameUsers = gameUsers;
        this.messageManager = messageManager;
    }

    public Optional<String> getPlayerProtectedFurnace(Location location) {
        return Optional.ofNullable(protectedFurnaces.get(location));
    }

    public boolean isChestOwner(Player player, Location location) {
        Optional<String> protectedFurnacePlayer = getPlayerProtectedFurnace(location);
        if (!protectedFurnacePlayer.isPresent()) {
            return true;
        }
        String playerName = player.getName();
        if (protectedFurnacePlayer.get().equals(playerName)) {
            return true;
        }
        GameUser user = gameUsers.getGameUser(playerName);
        String userLanguage = user.getLanguage();
        player.sendMessage(messageManager.getMessage(userLanguage, "thewalls.msg.someonesprotectedfurnace"));
        return false;
    }

    public void removeFurnaceProtection(GameUser user, Location location) {
        protectedFurnaces.remove(location);
        user.setProtectedFurnaces(user.getProtectedFurnaces() - 1);
    }

    public boolean isFurnaceOwner(String username, Location blockLocation) {
        Optional<String> playerProtectedFurnace = getPlayerProtectedFurnace(blockLocation);
        return playerProtectedFurnace.map(s -> s.equals(username)).orElse(true);
    }

    public boolean protectNewFurnace(Location blockLocation, String username, GameUser user) {
        if (exceedsNumberOfProtectedFurnaces(user)) {
            return false;
        }
        protectedFurnaces.put(blockLocation, username);
        user.setProtectedFurnaces(user.getProtectedFurnaces() + 1);
        return true;

    }

    private boolean exceedsNumberOfProtectedFurnaces(GameUser user) {
        return user.getProtectedFurnaces() >= 3;
    }

    public boolean hasOwner(Location blockLocation) {
        return getPlayerProtectedFurnace(blockLocation).isPresent();
    }
}