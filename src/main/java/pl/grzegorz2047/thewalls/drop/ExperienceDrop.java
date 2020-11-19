package pl.grzegorz2047.thewalls.drop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * A drop that results in experience orbs being awarded to the player.
 */
public final class ExperienceDrop extends Drop {

    public ExperienceDrop(List<String> tools, int quantity, int chance) {
        this.tools = tools;
        this.quantity = quantity;
        this.chance = chance;
    }

    public String getMessage() {
        return "";
    }

    public ItemStack toItemStack() {
        return new ItemStack(Material.COBBLESTONE, quantity);
    }
}
