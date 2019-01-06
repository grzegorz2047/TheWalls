package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;
import pl.grzegorz2047.thewalls.Counter;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.TheWalls;

import java.util.Random;

/**
 * Created by grzeg on 16.05.2016.
 */
public class EntityExplode implements Listener {


    private TheWalls plugin;

    public EntityExplode(TheWalls plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if(plugin.getGameData().getCounter().getStatus().equals(Counter.CounterStatus.COUNTINGTODROPWALLS)){
            event.setCancelled(true);
            return;
        }
        for (Block b : event.blockList()) {
            if (b.getType() == Material.BEDROCK) {
                event.blockList().clear();
            }
        }
        if (event.getEntity() instanceof Creeper) {
            ((Creeper) event.getEntity()).damage(30);
        }

        Random r = new Random();
        for (Block b : event.blockList()) {
            int rix = r.nextBoolean() ? -1 : 1;
            int riz = r.nextBoolean() ? -1 : 1;

            Material m = b.getType();
            if (m.equals(Material.GRASS) || m.equals(Material.MYCEL)) {
                b.setType(Material.DIRT);
            }

            if (!m.isSolid() || m.equals(Material.LEAVES) || m.equals(Material.GLASS) | m.equals(Material.TNT)) {
                b.setType(Material.AIR);
            }

            if (!b.getType().equals(Material.AIR)) {
                Entity ent = null;

                if (b.getType().equals(Material.TNT)) {
                    ent = b.getWorld().spawn(b.getLocation(), TNTPrimed.class);
                    ((TNTPrimed) ent).setFuseTicks(3);
                } else {
                    ent = b.getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
                    ((FallingBlock) ent).setDropItem(false);
                }

                ent.setFallDistance(0);
                ent.setVelocity(new Vector(r.nextBoolean() ? (rix * (0.25D + (r.nextInt(3) / 5))) : 0.0D, 0.6D + (r.nextInt(2) / 4.5D), r.nextBoolean() ? (riz * (0.25D + (r.nextInt(3) / 5))) : 0.0D));
                b.setTypeId(0, false);
            }
        }

        event.blockList().clear();
    }

}
