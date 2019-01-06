package pl.grzegorz2047.thewalls.api.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * Created by grzeg on 10.12.2015.
 */
public class CreateItemUtil {

    public static ItemStack createItem(Material mat, int amount) {
        return new ItemStack(mat, amount);
    }

    public static ItemStack createItem(Material mat, int amount, Enchantment ench, int level) {
        ItemStack item = new ItemStack(mat, amount);
        ItemMeta im = item.getItemMeta();
        im.addEnchant(ench, level, true);
        item.setItemMeta(im);
        return item;
    }
    public static ItemStack createItem(Material mat, int amount, String name, Enchantment ench, int level, String[] lore) {
        ItemStack item = new ItemStack(mat, amount);
        ItemMeta im = item.getItemMeta();
        im.setLore(Arrays.asList(lore));
        im.addEnchant(ench, level, true);
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack createItem(Material mat, int amount, String name, Enchantment ench1, int level1, Enchantment ench2, int level2) {
        ItemStack item = new ItemStack(mat, amount);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        im.addEnchant(ench1, level1, true);
        im.addEnchant(ench2, level2, true);
        item.setItemMeta(im);
        return item;
    }
    public static ItemStack createItem(Material mat, int amount, String name, Enchantment ench1, int level1, Enchantment ench2, int level2, String[] lore) {
        ItemStack item = new ItemStack(mat, amount);
        ItemMeta im = item.getItemMeta();
        im.setLore(Arrays.asList(lore));
        im.addEnchant(ench1, level1, true);
        im.addEnchant(ench2, level2, true);
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack createItem(Material mat, int amount, Enchantment ench1, int level1, Enchantment ench2, int level2, Enchantment ench3, int level3) {
        ItemStack item = new ItemStack(mat, amount);
        ItemMeta im = item.getItemMeta();
        im.addEnchant(ench1, level1, true);
        im.addEnchant(ench2, level2, true);
        im.addEnchant(ench3, level3, true);
        item.setItemMeta(im);
        return item;
    }


    public static ItemStack createItem(Material mat, int amount, String name) {
        ItemStack is = new ItemStack(mat, amount);
        ItemMeta md = is.getItemMeta();
        md.setDisplayName(name);
        is.setItemMeta(md);
        return is;
    }
    public static ItemStack createItem(Material mat, int amount, String name, String[] lore) {
        ItemStack is = new ItemStack(mat, amount);
        ItemMeta md = is.getItemMeta();
        md.setLore(Arrays.asList(lore));
        md.setDisplayName(name);
        is.setItemMeta(md);
        return is;
    }

    public static ItemStack createItem(Material mat, String name) {
        ItemStack is = new ItemStack(mat, 1);
        ItemMeta md = is.getItemMeta();
        md.setDisplayName(name);
        is.setItemMeta(md);
        return is;
    }

    public static ItemStack createItem(Material mat, String name, String[] lore) {
        ItemStack is = new ItemStack(mat, 1);
        ItemMeta md = is.getItemMeta();
        md.setLore(Arrays.asList(lore));
        md.setDisplayName(name);
        is.setItemMeta(md);
        return is;
    }

    public static ItemStack createItem(ItemStack item, String name, String[] lore) {
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        im.setLore(Arrays.asList(lore));
        item.setItemMeta(im);
        return item;
    }

}