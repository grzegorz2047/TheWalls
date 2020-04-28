package pl.grzegorz2047.thewalls;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by grzeg on 11.05.2016.
 */
public class WorldManagement {
    private static World loadedWorld;
    private static Random r = new Random();
    private final int numberOfMaps;
    private final HashMap<String, String> settings;

    private GameLocationLoader gameLocationLoader;

    public WorldManagement(int numberOfMaps, HashMap<String, String> settings) {
        this.numberOfMaps = numberOfMaps;
        this.settings = settings;

    }

    public void loadWorld(String mapName) {
        loadedWorld = Bukkit.createWorld(new WorldCreator(mapName));
        loadedWorld.setAutoSave(false);
        loadedWorld.setGameRule(GameRule.DO_INSOMNIA, false);
        loadedWorld.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        loadedWorld.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        loadedWorld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        loadedWorld.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        loadedWorld.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        loadedWorld.setTime(0);
        loadedWorld.setStorm(false);
        loadedWorld.setThundering(true);
        loadedWorld.setKeepSpawnInMemory(true);
        loadedWorld.setSpawnLocation(0, 147, 0);
        List<LivingEntity> livingEntities = loadedWorld.getLivingEntities();
        for (Entity e : livingEntities) {
            e.remove();
        }
        this.gameLocationLoader = new GameLocationLoader(settings, mapName);

    }

    public void disableSaving() {
        for (World w : Bukkit.getWorlds()) {
            w.setAutoSave(false);
        }
    }

    public void initNewWorld() {
        for (World w : Bukkit.getWorlds()) {
            if (w.equals(Bukkit.getWorlds().get(0))) {
                continue;
            }
            w.setKeepSpawnInMemory(false);
            boolean b = Bukkit.unloadWorld(w, false);
            loadedWorld = null;
        }
        int randomised = r.nextInt(numberOfMaps);

        String mapToLoad = "Walls_Mapa_" + randomised;
        String mainOutputWorldName = "walls_mapa";
        String destWorldPath = Bukkit.getWorldContainer().getAbsolutePath() + File.separator + mainOutputWorldName;
        String scrFolder = "/home/mcserver/minigames/TheWalls/Mapy";
        try {
            FileUtils.deleteDirectory(new File(destWorldPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileUtils.copyDirectory(FileUtils.getFile(scrFolder + File.separator + mapToLoad), FileUtils.getFile(destWorldPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadWorld(mainOutputWorldName);
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

    public Location getStartLocation(GameData.GameTeam assignedTeam) {
        return gameLocationLoader.getStartLocation(assignedTeam);
    }

    public void teleportPlayersOnDeathMatch(GameUsers gameusers) {
        gameLocationLoader.teleportPlayersOnDeathMatch(gameusers);
    }
}
