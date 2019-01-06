package pl.grzegorz2047.thewalls;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

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
        loadedWorld.setGameRuleValue("doMobGriefing", "false");
        loadedWorld.setGameRuleValue("doDayCycle", "false");
        loadedWorld.setGameRuleValue("doMobSpawning", "false");
        loadedWorld.setTime(0);
        loadedWorld.setKeepSpawnInMemory(true);
        loadedWorld.setStorm(false);
        loadedWorld.setThundering(true);
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
        WorldGuardPlugin wg = getWorldGuard();


        RegionManager rm = wg.getRegionManager(loadedWorld);

        if (protect) {
            try {
                for (int i = 1; i <= 8; i++) {
                    rm.getRegion("wall" + i).getFlags().put(DefaultFlag.ENTRY, StateFlag.State.DENY);
                    rm.getRegion("wall" + i).getFlags().put(DefaultFlag.BUILD, StateFlag.State.DENY);
                    rm.getRegion("wall" + i).getFlags().put(DefaultFlag.CREEPER_EXPLOSION, StateFlag.State.DENY);
                    rm.getRegion("wall" + i).getFlags().put(DefaultFlag.TNT, StateFlag.State.DENY);
                }
                rm.getRegion("pomiedzywalls1").getFlags().put(DefaultFlag.ENDERPEARL, StateFlag.State.DENY);
                rm.getRegion("pomiedzywalls1").getFlags().put(DefaultFlag.BUILD, StateFlag.State.DENY);
                rm.getRegion("pomiedzywalls1").getFlags().put(DefaultFlag.ENTRY, StateFlag.State.DENY);
                rm.getRegion("pomiedzywalls2").getFlags().put(DefaultFlag.ENDERPEARL, StateFlag.State.DENY);
                rm.getRegion("pomiedzywalls2").getFlags().put(DefaultFlag.BUILD, StateFlag.State.DENY);
                rm.getRegion("pomiedzywalls2").getFlags().put(DefaultFlag.ENTRY, StateFlag.State.DENY);

            } catch (NullPointerException e) {
                System.out.println("Brakuje regionow do muru!");
            }
        } else {
            try {
                for (int i = 1; i <= 8; i++) {
                    rm.getRegion("wall" + i).getFlags().put(DefaultFlag.ENTRY, StateFlag.State.ALLOW);
                    rm.getRegion("wall" + i).getFlags().put(DefaultFlag.BUILD, StateFlag.State.ALLOW);
                    rm.getRegion("wall" + i).getFlags().put(DefaultFlag.CREEPER_EXPLOSION, StateFlag.State.ALLOW);
                    rm.getRegion("wall" + i).getFlags().put(DefaultFlag.TNT, StateFlag.State.ALLOW);
                }
                rm.getRegion("pomiedzywalls1").getFlags().put(DefaultFlag.ENDERPEARL, StateFlag.State.ALLOW);
                rm.getRegion("pomiedzywalls1").getFlags().put(DefaultFlag.BUILD, StateFlag.State.ALLOW);
                rm.getRegion("pomiedzywalls1").getFlags().put(DefaultFlag.ENTRY, StateFlag.State.ALLOW);
                rm.getRegion("pomiedzywalls2").getFlags().put(DefaultFlag.ENDERPEARL, StateFlag.State.ALLOW);
                rm.getRegion("pomiedzywalls2").getFlags().put(DefaultFlag.BUILD, StateFlag.State.ALLOW);
                rm.getRegion("pomiedzywalls2").getFlags().put(DefaultFlag.ENTRY, StateFlag.State.ALLOW);
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

    private static WorldGuardPlugin getWorldGuard() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            System.out.println("Nie ma pluginu WorldGuard!");
        }

        return (WorldGuardPlugin) plugin;
    }

    public void removeWalls() {
        WorldGuardPlugin wg = getWorldGuard();

        RegionManager rm = wg.getRegionManager(loadedWorld);
        //Bukkit.broadcastMessage("Swiat to "+swiat);

        try {
            for (int i = 1; i <= 8; i++) {
                int minx = rm.getRegion("wall" + i).getMinimumPoint().getBlockX();
                int miny = rm.getRegion("wall" + i).getMinimumPoint().getBlockY();
                int minz = rm.getRegion("wall" + i).getMinimumPoint().getBlockZ();

                int maxx = rm.getRegion("wall" + i).getMaximumPoint().getBlockX();
                int maxy = rm.getRegion("wall" + i).getMaximumPoint().getBlockY();
                int maxz = rm.getRegion("wall" + i).getMaximumPoint().getBlockZ();
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

}
