package pl.grzegorz2047.thewalls;

import org.bukkit.Location;
import pl.grzegorz2047.thewalls.api.exception.IncorrectDataStringException;
import pl.grzegorz2047.thewalls.api.util.LocationUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by grzeg on 04.11.2016.
 */
public class GameLocationLoader {


    private Map<GameData.GameTeam, Location> teamSpawnLocations = new HashMap<>();
    private final HashMap<String, String> settings;
    private String spawnTeam1;
    private String spawnTeam2;
    private String spawnTeam3;
    private String spawnTeam4;

    public GameLocationLoader(HashMap<String, String> settings) {
        this.settings = settings;
        spawnTeam1 = settings.get("thewalls.spawns.team." + 1);
        spawnTeam2 = settings.get("thewalls.spawns.team." + 2);
        spawnTeam3 = settings.get("thewalls.spawns.team." + 3);
        spawnTeam4 = settings.get("thewalls.spawns.team." + 4);
    }

    public Location getStartLocation(GameData.GameTeam team) {
        return teamSpawnLocations.get(team);
    }

    public void loadSpawns(String worldName) {
        teamSpawnLocations = new HashMap<>();
        try {
            teamSpawnLocations.put(GameData.GameTeam.TEAM1, LocationUtil.entityStringToLocation(
                    worldName,
                    spawnTeam1));
            teamSpawnLocations.put(GameData.GameTeam.TEAM2, LocationUtil.entityStringToLocation(
                    worldName,
                    spawnTeam2));
            teamSpawnLocations.put(GameData.GameTeam.TEAM3, LocationUtil.entityStringToLocation(
                    worldName,
                    spawnTeam3));

            teamSpawnLocations.put(GameData.GameTeam.TEAM4, LocationUtil.entityStringToLocation(
                    worldName,
                    spawnTeam4));
        } catch (IncorrectDataStringException e) {
            e.printStackTrace();
        }
    }

}