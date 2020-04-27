package pl.grzegorz2047.thewalls.drop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Drop {
    private final List<String> tools;
    private Material material;
    private String message = "";
    private boolean isExp = false;
    private int quantity;
    private int chance;

    public Drop(List<String> tools, int quantity, int chance) {
        this.tools = tools;
        this.quantity = quantity;
        this.chance = chance;
        isExp = true;
        material = Material.COBBLESTONE;
    }

    public Drop(Material material, List<String> tools, int quantity, int chanceVal, String message) {
        this.material = material;
        this.tools = tools;
        this.quantity = quantity;
        this.chance = chanceVal;
        this.message = message;
    }

    public boolean isExp() {
        return this.isExp;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public int getChance() {
        return this.chance;
    }

    public boolean withProperTool(ItemStack item) {
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

    public ItemStack toItemStack() {
        return new ItemStack(material, quantity);
    }

    public String getMessage() {
        return message;
    }
}
