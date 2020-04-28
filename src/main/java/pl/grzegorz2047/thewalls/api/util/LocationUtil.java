package pl.grzegorz2047.thewalls.api.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import pl.grzegorz2047.thewalls.api.exception.IncorrectDataStringException;

/**
 * Created by Grzegorz2047. 23.09.2015.
 */
public class LocationUtil {


    public static String entityLocationToString(Location loc) {
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        double pitch = loc.getPitch();
        double yaw = loc.getYaw();
        String worldName = loc.getWorld().getName();

        StringBuilder sb = new StringBuilder();
        sb.
                append(x).append(':').
                append(y).append(':').
                append(z).append(':').
                append(pitch).append(':').
                append(yaw).append(':').
                append(worldName);

        return sb.toString();

    }

    public static String blockLocationToString(Location loc) {
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        String worldName = loc.getWorld().getName();

        StringBuilder sb = new StringBuilder();
        sb.
                append(x).append(':').
                append(y).append(':').
                append(z).append(':').
                append(worldName);

        return sb.toString();
    }

    public static Location blockStringToLocation(String loc) throws IncorrectDataStringException {
        String[] split = loc.split(":");
        if (split.length != 4) {
            throw new IncorrectDataStringException("Podales niepoprawy ciag znakow dlugosc  4 !=" + split.length);
        }
        double x = Double.parseDouble(split[0]);
        double y = Double.parseDouble(split[1]);
        double z = Double.parseDouble(split[2]);
        String worldName = split[3];
        return new Location(Bukkit.getWorld(worldName), x, y, z);
    }

    public static Location entityStringToLocation(String world, String loc) throws IncorrectDataStringException {
        System.out.println(world + " loc " + loc);
        String[] split = loc.split(":");
        if (split.length != 5) {
            throw new IncorrectDataStringException("Podales niepoprawy ciag znakow dlugosc  5 !=" + split.length);
        }
        double x = Double.parseDouble(split[0]);
        double y = Double.parseDouble(split[1]);
        double z = Double.parseDouble(split[2]);
        float p = Float.parseFloat(split[3]);
        float yaw = Float.parseFloat(split[4]);
        return new Location(Bukkit.getWorld(world), x, y, z, p, yaw);
    }

}
