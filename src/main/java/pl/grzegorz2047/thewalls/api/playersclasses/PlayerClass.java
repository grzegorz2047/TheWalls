package pl.grzegorz2047.thewalls.api.playersclasses;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Grzegorz2047. 14.09.2015.
 */
public abstract class PlayerClass {

    protected int price;
    private int maxLevel;


    private HashMap<Integer, CustomInventory> inventory;
    private HashMap<Integer, Integer> levelprice;
    private HashMap<Integer, List<PotionEffect>> potionEffects;
    private HashMap<Integer, Boolean> specialPerm;

    public PlayerClass() {
        inventory = new HashMap<Integer, CustomInventory>();
        levelprice = new HashMap<Integer, Integer>();
        potionEffects = new HashMap<Integer, List<PotionEffect>>();
        specialPerm = new HashMap<Integer, Boolean>();
        setMaxLevel(0);
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public HashMap<Integer, Integer> getLevelprice() {
        return levelprice;
    }

    /*
            Use it before putting into inventory

         */

    public HashMap<Integer, Boolean> getSpecialPerm() {
        return specialPerm;
    }

    public boolean needsSpecialPerms(int level) {
        if (specialPerm.isEmpty()) {
            return false;
        } else {
            Validate.notNull(specialPerm.get(level));
            return specialPerm.get(level);
        }


    }

    public HashMap<Integer, List<PotionEffect>> getPotionEffects() {
        return potionEffects;
    }

    protected void setMaxLevel(int level) {
        this.maxLevel = level;
        for (int i = 0; i <= level; i++) {
            inventory.put(i, new CustomInventory());
            potionEffects.put(i, new ArrayList<PotionEffect>());
            specialPerm.put(i, false);
        }
    }

    protected ItemStack createItem(Material mat, int amount) {
        return new ItemStack(mat, amount);
    }

    protected ItemStack createItem(Material mat, int amount, Enchantment ench, int level) {
        ItemStack item = new ItemStack(mat, amount);
        ItemMeta im = item.getItemMeta();
        im.addEnchant(ench, level, true);
        item.setItemMeta(im);
        return item;
    }

    protected ItemStack createItem(Material mat, int amount, Enchantment ench1, int level1, Enchantment ench2, int level2) {
        ItemStack item = new ItemStack(mat, amount);
        ItemMeta im = item.getItemMeta();
        im.addEnchant(ench1, level1, true);
        im.addEnchant(ench2, level2, true);
        item.setItemMeta(im);
        return item;
    }

    protected ItemStack createItem(Material mat, int amount, Enchantment ench1, int level1, Enchantment ench2, int level2, Enchantment ench3, int level3) {
        ItemStack item = new ItemStack(mat, amount);
        ItemMeta im = item.getItemMeta();
        im.addEnchant(ench1, level1, true);
        im.addEnchant(ench2, level2, true);
        im.addEnchant(ench3, level3, true);
        item.setItemMeta(im);
        return item;
    }

    public void giveClass(Player p, int lvl) {
        //System.out.print("Daje klase! wielkosc "+inventory.get(lvl).getInventory().getContents().length);
        p.getInventory().setContents(inventory.get(lvl).getInventory().getContents().clone());
        p.getInventory().setArmorContents(inventory.get(lvl).getArmorContents().clone());
        this.givePotionEffects(p, lvl);
        p.updateInventory();
    }

    private void givePotionEffects(Player p, int level) {
        if (!this.potionEffects.isEmpty()) {
            p.addPotionEffects(this.getPotionEffects().get(level));
        } else {
            for (PotionEffect effect : this.potionEffects.get(level)) {
                p.removePotionEffect(effect.getType());
            }

        }
    }

    public CustomInventory getLevelInventory(int level) {
        return this.inventory.get(level);
    }

    protected int getPriceLevel(int level) {
        return this.levelprice.get(level);
    }

    protected void addItemsToInventory() {
        //inventory.get(0).addItem(new ItemStack(Material.POTION,3),new ItemStack(Material.NETHER_WARTS,1),new ItemStack(Material.SPECKLED_MELON,1),new ItemStack(Material.BREWING_STAND,1));
        //example
    }

}
