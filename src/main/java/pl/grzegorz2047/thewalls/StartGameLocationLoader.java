package pl.grzegorz2047.thewalls;

import org.bukkit.Location;
import pl.grzegorz2047.thewalls.api.exception.IncorrectDataStringException;
import pl.grzegorz2047.thewalls.api.util.LocationUtil;

/**
 * Created by grzeg on 04.11.2016.
 */
public class StartGameLocationLoader {


    private final TheWalls plugin;


    private Location locteam1;
    private Location locteam2;
    private Location locteam3;
    private Location locteam4;

    public StartGameLocationLoader(TheWalls plugin) {
        this.plugin = plugin;
    }

    public Location getLocteam1() {
        return locteam1;
    }

    public Location getLocteam2() {
        return locteam2;
    }

    public Location getLocteam3() {
        return locteam3;
    }

    public Location getLocteam4() {
        return locteam4;
    }

    public StartGameLocationLoader invoke(WorldManagement worldManagement) {
        try {
            locteam1 = LocationUtil.entityStringToLocation(
                    worldManagement.getLoadedWorld().getName(),
                    plugin.getSettings().get("thewalls.spawns.team." + 1));
            locteam2 = LocationUtil.entityStringToLocation(
                    worldManagement.getLoadedWorld().getName(),
                    plugin.getSettings().get("thewalls.spawns.team." + 2));
            locteam3 = LocationUtil.entityStringToLocation(
                    worldManagement.getLoadedWorld().getName(),
                    plugin.getSettings().get("thewalls.spawns.team." + 3));

            locteam4 = LocationUtil.entityStringToLocation(
                    worldManagement.getLoadedWorld().getName(),
                    plugin.getSettings().get("thewalls.spawns.team." + 4));
        } catch (IncorrectDataStringException e) {
            e.printStackTrace();
        }
        return this;
    }
}