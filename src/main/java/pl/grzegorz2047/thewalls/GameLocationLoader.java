package pl.grzegorz2047.thewalls;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.grzegorz2047.thewalls.api.exception.IncorrectDataStringException;
import pl.grzegorz2047.thewalls.api.util.LocationUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by grzeg on 04.11.2016.
 */
public class GameLocationLoader {

    private final Map<GameData.GameTeam, Location> teamSpawnLocations = new HashMap<>();
    private final Map<GameData.GameTeam, Location> dmTeamSpawnLocations = new HashMap<>();
    private final HashMap<String, String> settings;

    public GameLocationLoader(HashMap<String, String> settings, String worldName) {
        this.settings = settings;
        String spawnPath = "thewalls.spawns.team.";
        for (int i = 1; i <= 4; i++) {
            try {
                GameData.GameTeam fromNumber = GameData.GameTeam.fromNumber(i);
                dmTeamSpawnLocations.put(fromNumber, getTeamDmSpawn(i, worldName));
                teamSpawnLocations.put(fromNumber, getSpawn(worldName, settings.get(spawnPath + i)));
            } catch (IncorrectDataStringException e) {
                e.printStackTrace();
            }

        }
    }

    private Location getSpawn(String worldName, String locPath) throws IncorrectDataStringException {
        return LocationUtil.entityStringToLocation(worldName, locPath);
    }

    public Location getStartLocation(GameData.GameTeam team) {
        return teamSpawnLocations.get(team);
    }


    private Location getTeamDmSpawn(int i, String worldName) throws IncorrectDataStringException {
        return getSpawn(worldName, settings.get("thewalls.spawns.dm.team." + i));
    }

    public void teleportToDeathMatch(Player p, GameData.GameTeam assignedTeam) {
        p.teleport(dmTeamSpawnLocations.get(assignedTeam));
    }

    public void teleportPlayersOnDeathMatch(GameUsers gameUsers) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            GameUser gameUser = gameUsers.getGameUser(p.getName());
            if (gameUser.getAssignedTeam() == null) {
                p.teleport(dmTeamSpawnLocations.get(GameData.GameTeam.TEAM1));
                continue;
            }
            teleportToDeathMatch(p, gameUser.getAssignedTeam());
        }
    }


}