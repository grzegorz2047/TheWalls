package pl.grzegorz2047.thewalls.drop;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BlockDrop {
    private final Material material;
    private final List<Drop> drops;

    public BlockDrop(Material material, List<Drop> drops) {
        this.material = material;
        this.drops = drops;
    }

    public void dropItems(BlockBreakEvent e) {
        boolean chosen = false;
        Random r = new Random();
        for (Drop drop : drops) {
            ItemStack itemInHand = e.getPlayer().getItemInHand();
            if (!drop.withProperTool(itemInHand)) {
                continue;
            }
            int chance = drop.getChance();
            int randomNumber = r.nextInt(100);
            if (randomNumber <= chance) {
                if (drop.isExp()) {
                    e.setExpToDrop(drop.getQuantity());
                } else {
                    e.setExpToDrop(0);
                    Location location = e.getBlock().getLocation();
                    e.getBlock().setType(Material.AIR);
                    location.getWorld().dropItemNaturally(location, drop.toItemStack());
                    e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', drop.getMessage()));
                    e.setCancelled(true);
                }
                break;
            }
        }
        Collections.shuffle(drops);
    }
}
