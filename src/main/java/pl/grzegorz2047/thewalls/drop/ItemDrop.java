package pl.grzegorz2047.thewalls.drop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * A drop variant that results in an item being awarded to the player.
 */
public final class ItemDrop extends Drop {

    private final Material material;
    private final String message;

    public ItemDrop(Material material, List<String> tools, int quantity, int chanceVal, String message) {
        this.material = material;
        this.tools = tools;
        this.quantity = quantity;
        this.chance = chanceVal;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public ItemStack toItemStack() {
        return new ItemStack(material, quantity);
    }
}
