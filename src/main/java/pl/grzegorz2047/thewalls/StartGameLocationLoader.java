package pl.grzegorz2047.thewalls;

import org.bukkit.Location;
import pl.grzegorz2047.thewalls.api.exception.IncorrectDataStringException;
import pl.grzegorz2047.thewalls.api.util.LocationUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by grzeg on 04.11.2016.
 */
public class StartGameLocationLoader {


    private Map<GameData.GameTeam, Location> teamSpawnLocations = new HashMap<>();
    private final HashMap<String, String> settings;

    public StartGameLocationLoader(HashMap<String, String> settings) {

        this.settings = settings;
    }

    public Location getStartLocation(GameData.GameTeam team) {
        return teamSpawnLocations.get(team);
    }

    public StartGameLocationLoader loadSpawns(WorldManagement worldManagement) {
        try {
            String worldName = worldManagement.getLoadedWorldName();
            teamSpawnLocations.put(GameData.GameTeam.TEAM1, LocationUtil.entityStringToLocation(
                    worldName,
                    settings.get("thewalls.spawns.team." + 1)));
            teamSpawnLocations.put(GameData.GameTeam.TEAM2, LocationUtil.entityStringToLocation(
                    worldName,
                    settings.get("thewalls.spawns.team." + 2)));
            teamSpawnLocations.put(GameData.GameTeam.TEAM3, LocationUtil.entityStringToLocation(
                    worldName,
                    settings.get("thewalls.spawns.team." + 3)));

            teamSpawnLocations.put(GameData.GameTeam.TEAM4, LocationUtil.entityStringToLocation(
                    worldName,
                    settings.get("thewalls.spawns.team." + 4)));
        } catch (IncorrectDataStringException e) {
            e.printStackTrace();
        }
        return this;
    }

}