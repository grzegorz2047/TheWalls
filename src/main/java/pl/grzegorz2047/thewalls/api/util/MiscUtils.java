package pl.grzegorz2047.thewalls.api.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * @author Grzegorz
 */
public class MiscUtils {

    public static void removeFromInv(Inventory inv, Material mat, int dmgValue, int amount, byte data) {
        if (inv.contains(mat)) {
            int remaining = amount;
            ItemStack[] contents = inv.getContents();
            for (ItemStack is : contents) {
                if (is != null) {
                    if (is.getType() == mat) {
                        if (data != -1) {
                            if (is.getData() != null) {
                                if (is.getData().getData() == data) {
                                    if (is.getDurability() == dmgValue || dmgValue <= 0) {
                                        if (is.getAmount() > remaining) {
                                            is.setAmount(is.getAmount() - remaining);
                                            remaining = 0;
                                        } else if (is.getAmount() <= remaining) {
                                            if (remaining > 0) {
                                                remaining -= is.getAmount();
                                                is.setType(Material.AIR);
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            if (is.getDurability() == dmgValue || dmgValue <= 0) {
                                if (is.getAmount() > remaining) {
                                    is.setAmount(is.getAmount() - remaining);
                                    remaining = 0;
                                } else if (is.getAmount() <= remaining) {
                                    if (remaining > 0) {
                                        remaining -= is.getAmount();
                                        is.setType(Material.AIR);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            inv.setContents(contents);
        }
    }

    public static boolean hasEnoughItems(List<ItemStack> reqitems, Inventory inv) {
        for (ItemStack item : reqitems) {
            if (!inv.containsAtLeast(item, item.getAmount())) {
                return false;
            }
        }

        return true;
    }
    public static boolean hasEnoughItems(ItemStack reqitem, Inventory inv) {
        return inv.containsAtLeast(reqitem, reqitem.getAmount());
    }
    public static void removeRequiredItems(List<ItemStack> reqitems, Inventory inv) {
        for (ItemStack item : reqitems) {
            removeFromInv(inv, item.getType(), item.getDurability(), item.getAmount(), item.getData().getData());
        }
    }
    public static void removeRequiredItems(ItemStack reqitem, Inventory inv) {
        removeFromInv(inv, reqitem.getType(), reqitem.getDurability(), reqitem.getAmount(), reqitem.getData().getData());
    }
    public static String argsToString(String args[], int minindex, int maxindex) {
        StringBuilder sb = new StringBuilder();
        for (int i = minindex; i < maxindex; i++) {
            sb.append(args[i]);
            sb.append(" ");
        }
        return sb.toString();
    }

    public static boolean isPlayerNearby(Player p, int radius) {//Znaleziony kod
        Location l = p.getLocation();
        for (Player player : p.getLocation().getWorld().getPlayers()) {
            if (p.equals(player)) {
                continue;
            }
            double distance = l.distance(player.getLocation());
            if (distance <= radius) {
                //System.out.println(distance);
                return true;
            }
        }
        return false;
    }

}