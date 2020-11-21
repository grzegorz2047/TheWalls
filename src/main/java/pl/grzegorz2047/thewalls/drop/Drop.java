package pl.grzegorz2047.thewalls.drop;

import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * An abstract reward for breaking a specific block.
 */
public abstract class Drop {

    protected List<String> tools;
    protected int quantity;
    protected int chance;

    public int getQuantity() {
        return this.quantity;
    }

    public int getChance() {
        return this.chance;
    }

    public boolean isProperTool(ItemStack item) {
        boolean isAny = this.tools.stream().anyMatch(x -> x.equals("ANY"));
        if (isAny) {
            return true;
        } else {
            if (item == null) {
                return false;
            }
            String itemType = item.getType().toString();
            return this.tools.stream().anyMatch(x -> x.equals(itemType));
        }
    }

    public abstract String getMessage();

    /**
     * Creates a new item stack described by this Drop, if applicable.
     */
    public abstract ItemStack toItemStack();
}
