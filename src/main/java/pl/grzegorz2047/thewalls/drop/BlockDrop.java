package pl.grzegorz2047.thewalls.drop;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static pl.grzegorz2047.thewalls.api.util.ColoringUtil.colorText;

public class BlockDrop {

    private final Random random = new Random();
    private final Material material;
    private final List<Drop> drops;

    public BlockDrop(Material material, List<Drop> drops) {
        this.material = material;
        this.drops = drops;
    }

    public void dropItems(BlockBreakEvent e) {

        PlayerInventory playerInv = e.getPlayer().getInventory();
        ItemStack itemInMainHand = playerInv.getItemInMainHand();
        ItemStack itemInOffHand = playerInv.getItemInOffHand();

        for (Drop drop : drops) {

            if (!drop.isProperTool(itemInMainHand) && !drop.isProperTool(itemInOffHand)) {
                continue;
            }

            int chance = drop.getChance();
            int randomNumber = random.nextInt(100);
            if (randomNumber <= chance) {
                if (drop instanceof ExperienceDrop) {
                    e.setExpToDrop(drop.getQuantity());
                } else {
                    e.setExpToDrop(0);
                    Location location = e.getBlock().getLocation();
                    e.getBlock().setType(Material.AIR);
                    location.getWorld().dropItemNaturally(location, drop.toItemStack());
                    e.getPlayer().sendMessage(colorText(drop.getMessage()));
                    e.setCancelled(true);
                }
                break;
            }
        }

        Collections.shuffle(drops);
    }
}
