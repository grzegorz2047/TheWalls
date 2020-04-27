package pl.grzegorz2047.thewalls;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
 import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.Random;

/**
 * Created by grzeg on 11.05.2016.
 */
public class WorldManagement {
    private static World loadedWorld;
    private static Random r = new Random();

    public void loadWorld(int numberOfMaps) {
        int randomised = r.nextInt(numberOfMaps);

        loadedWorld = Bukkit.createWorld(new WorldCreator("Walls_Mapa_" + randomised));
        loadedWorld.setAutoSave(false);
        loadedWorld.setGameRule(GameRule.DO_INSOMNIA, false);
        loadedWorld.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        loadedWorld.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        loadedWorld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        loadedWorld.setTime(0);
        loadedWorld.setKeepSpawnInMemory(true);
        loadedWorld.setStorm(false);
        loadedWorld.setThundering(true);
        loadedWorld.setSpawnLocation(0, 147, 0);
        for (Entity e : loadedWorld.getEntities()) {
            e.remove();
        }
    }

    public void disableSaving() {
        for (World w : Bukkit.getWorlds()) {
            w.setAutoSave(false);
        }
    }

    public void reloadLoadedWorld(String numberOfMaps) {
        for (World w : Bukkit.getWorlds()) {
            if(w.equals(Bukkit.getWorlds().get(0))){
                continue;
            }
            Bukkit.unloadWorld(w, false);
        }
        loadWorld(Integer.parseInt(numberOfMaps));
    }


    public World getLoadedWorld() {
        return loadedWorld;
    }

    public void setProtected(boolean protect) {
        RegionManager rm = getRegionManager();

        ProtectedRegion pomiedzywalls1Region = rm.getRegion("pomiedzywalls1");
        Map<Flag<?>, Object> pomiedzywalls1RegionFlags = pomiedzywalls1Region.getFlags();
        ProtectedRegion pomiedzywalls2Region = rm.getRegion("pomiedzywalls2");
        Map<Flag<?>, Object> pomiedzywalls2RegionFlags = pomiedzywalls2Region.getFlags();
        if (protect) {
            try {
                for (int i = 1; i <= 8; i++) {
                    ProtectedRegion region = rm.getRegion("wall" + i);
                    Map<Flag<?>, Object> regionFlags = region.getFlags();
                    regionFlags.put(Flags.ENTRY, StateFlag.State.DENY);
                    regionFlags.put(Flags.BUILD, StateFlag.State.DENY);
                    regionFlags.put(Flags.CREEPER_EXPLOSION, StateFlag.State.DENY);
                    regionFlags.put(Flags.TNT, StateFlag.State.DENY);
                }
                pomiedzywalls1RegionFlags.put(Flags.ENDERPEARL, StateFlag.State.DENY);
                pomiedzywalls1RegionFlags.put(Flags.BUILD, StateFlag.State.DENY);
                pomiedzywalls1RegionFlags.put(Flags.ENTRY, StateFlag.State.DENY);
                pomiedzywalls2RegionFlags.put(Flags.ENDERPEARL, StateFlag.State.DENY);
                pomiedzywalls2RegionFlags.put(Flags.BUILD, StateFlag.State.DENY);
                pomiedzywalls2RegionFlags.put(Flags.ENTRY, StateFlag.State.DENY);

            } catch (NullPointerException e) {
                System.out.println("Brakuje regionow do muru!");
            }
        } else {
            try {
                for (int i = 1; i <= 8; i++) {
                    ProtectedRegion region = rm.getRegion("wall" + i);
                    Map<Flag<?>, Object> regionFlags = region.getFlags();
                    regionFlags.put(Flags.ENTRY, StateFlag.State.ALLOW);
                    regionFlags.put(Flags.BUILD, StateFlag.State.ALLOW);
                    regionFlags.put(Flags.CREEPER_EXPLOSION, StateFlag.State.ALLOW);
                    regionFlags.put(Flags.TNT, StateFlag.State.ALLOW);
                }
                pomiedzywalls1RegionFlags.put(Flags.ENDERPEARL, StateFlag.State.ALLOW);
                pomiedzywalls1RegionFlags.put(Flags.BUILD, StateFlag.State.ALLOW);
                pomiedzywalls1RegionFlags.put(Flags.ENTRY, StateFlag.State.ALLOW);
                pomiedzywalls2RegionFlags.put(Flags.ENDERPEARL, StateFlag.State.ALLOW);
                pomiedzywalls2RegionFlags.put(Flags.BUILD, StateFlag.State.ALLOW);
                pomiedzywalls2RegionFlags.put(Flags.ENTRY, StateFlag.State.ALLOW);
                System.out.println("Zmieniono region!");
            } catch (NullPointerException e) {
                System.out.println("Brakuje regionow do muru wall8 albo pomiedzywalls !");
            }
        }


        try {
            rm.save();
            System.out.println("Zapis flagi regionu!");
        } catch (Exception exp) {
        }
    }

    private RegionManager getRegionManager() {
        WorldGuard wg = WorldGuard.getInstance();
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();

        return container.get(BukkitAdapter.adapt(loadedWorld));
    }

    public void removeWalls() {
        RegionManager rm = getRegionManager();
        //Bukkit.broadcastMessage("Swiat to "+swiat);

        try {
            for (int i = 1; i <= 8; i++) {
                ProtectedRegion protectedRegion = rm.getRegion("wall" + i);
                BlockVector3 minimumPoint = protectedRegion.getMinimumPoint();
                int minx = minimumPoint.getBlockX();
                int miny = minimumPoint.getBlockY();
                int minz = minimumPoint.getBlockZ();

                BlockVector3 maximumPoint = protectedRegion.getMaximumPoint();
                int maxx = maximumPoint.getBlockX();
                int maxy = maximumPoint.getBlockY();
                int maxz = maximumPoint.getBlockZ();
                for (int x = minx; x <= maxx; x = x + 1) {
                    for (int y = miny; y <= maxy; y = y + 1) {
                        for (int z = minz; z <= maxz; z = z + 1) {
                            Location blockloc = new Location(loadedWorld, x, y, z);
                            //System.out.println("xyz"+x+" "+y+" "+z+" ");
                            blockloc.getBlock().setType(Material.AIR);
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Nie ma ktorego z tych regionow wall1,wall2 ...");
        }

        try {
            rm.save();
            System.out.println("Zapis flagi regionu!");
        } catch (Exception exp) {
        }
        this.setProtected(false);
    }

    public String getLoadedWorldName() {
        return loadedWorld.getName();
    }
}
