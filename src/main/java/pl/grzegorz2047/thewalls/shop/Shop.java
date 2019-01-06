package pl.grzegorz2047.thewalls.shop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.grzegorz2047.databaseapi.SQLUser;
import pl.grzegorz2047.databaseapi.messages.MessageAPI;
import pl.grzegorz2047.databaseapi.shop.Item;
import pl.grzegorz2047.databaseapi.shop.ShopAPI;
import pl.grzegorz2047.thewalls.api.util.CreateItemUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by grzeg on 21.05.2016.
 */
public class Shop {

    private final ShopAPI shopAPI;
    private HashMap<Integer, Item> normalItems;
    private HashMap<Integer, Item> tempItems;

    public Shop(ShopAPI shopAPI) {
        this.shopAPI = shopAPI;
        this.normalItems = shopAPI.getShopItems("perm");
        this.tempItems = shopAPI.getShopItems("temp");


    }

    public void openPermItems(Player p, SQLUser user, MessageAPI msg) {
        Inventory inv = Bukkit.createInventory(null, 54, "Perm items");
        for (Map.Entry<Integer, Item> entry : normalItems.entrySet()) {
            Item item = entry.getValue();
            ItemStack it = item.toItemStack();
            ItemMeta im = it.getItemMeta();
            List<String> lore = new ArrayList<String>();
            lore.add("");
            lore.add(msg.getMessage(user.getLanguage(), "shop.item.price").replace("{MONEY}", String.valueOf(item.getPrice())));//"§7Gracz §aCena:§6 120 §7monet"
            lore.add(msg.getMessage(user.getLanguage(), "shop.item.amount").replace("{AMOUNT}", String.valueOf(item.getAmount())));//"§c1 szt."
            im.setLore(lore);
            it.setItemMeta(im);
            inv.setItem(item.getSlot(), it);
        }

        if (p != null) {
            p.openInventory(inv);
        }
    }

    public void openTempItems(Player p, SQLUser user, MessageAPI msg) {
        Inventory inv = Bukkit.createInventory(null, 54, "Temp items");
        for (Map.Entry<Integer, Item> entry : tempItems.entrySet()) {
            Item item = entry.getValue();
            ItemStack it = item.toItemStack();
            ItemMeta im = it.getItemMeta();
            List<String> lore = new ArrayList<String>();
            lore.add("");
            lore.add(msg.getMessage(user.getLanguage(), "shop.item.price").replace("{MONEY}", String.valueOf(item.getPrice())));//"§7Gracz §aCena:§6 120 §7monet"
            lore.add(msg.getMessage(user.getLanguage(), "shop.item.amount").replace("{AMOUNT}", String.valueOf(item.getAmount())));//"§c1 szt."
            im.setLore(lore);
            it.setItemMeta(im);
            inv.setItem(item.getSlot(), it);
        }

        if (p != null) {
            p.openInventory(inv);
        }
    }

    public void openMainMenu(Player p, SQLUser user, MessageAPI msg) {
        Inventory inv = Bukkit.createInventory(null, 9, "Main");
        inv.setItem(3, CreateItemUtil.createItem(Material.QUARTZ, msg.getMessage(user.getLanguage(), "shop.item.tempitems")));
        inv.setItem(5, CreateItemUtil.createItem(Material.MAGMA_CREAM, msg.getMessage(user.getLanguage(), "shop.item.permitems")));
            p.openInventory(inv);

    }

    public HashMap<Integer, Item> getNormalItems() {
        return normalItems;
    }

    public HashMap<Integer, Item> getTempItems() {
        return tempItems;
    }
}
