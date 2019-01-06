package pl.grzegorz2047.thewalls.api.util;

/**
 * Created by Grzegorz2047. 31.08.2015.
 */
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

/**
 * Created by neksi on 2015-01-22.
 */
public class ItemUtil {

    public static ItemStack renameItem(ItemStack itemStack, String str){
        ItemStack itemClone = itemStack.clone();

        ItemMeta itemMeta = itemClone.getItemMeta();
        str = Character.toUpperCase(str.charAt(0)) + str.substring(1);
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', str));
        itemMeta.setLore(null);
        itemClone.setItemMeta(itemMeta);

        return itemClone;
    }

    /**
     * E.G: enchantBuilder(Material.LEATHER_CHESTPLATE, new Enchantment[]{Enchantment.THORNS}, new int[]{1});
     * @param material
     * @param enchantments
     * @param ints
     * @return
     */
    public static ItemStack enchantBuilder(Material material, Enchantment[] enchantments, int[] ints){
        ItemStack item = new ItemStack(material);
        for(int i=0; i < enchantments.length; i++){
            item.addUnsafeEnchantment(enchantments[i], ints[i]);
        }
        return item;
    }

    /**
     * E.G: applyToItemStack(PotionType.POISON, Potion.Tier.ONE, true, true, 20*5, 16388), 3);
     * @param type
     * @param tier
     * @param splash
     * @param changeTime
     * @param potionTime
     * @param durability
     * @return
     */
    public static ItemStack applyToItemStack(PotionType type, Potion.Tier tier, Boolean splash, Boolean changeTime, int potionTime, int durability) {
        if(!changeTime) {
            Potion potion = new Potion(type, tier, splash);
            return potion.toItemStack(1);
        } else{
            ItemStack potion = new ItemStack(Material.POTION);
            potion.setDurability((short)durability);
            PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
            PotionEffect potionEffect = new PotionEffect(type.getEffectType(), potionTime, 0);
            potionMeta.addCustomEffect(potionEffect, true);
            potion.setItemMeta(potionMeta);
            return potion;
        }
    }

    public static ItemStack setColor(Material material, int r, int g, int b){
        ItemStack itemstack = new ItemStack(material);
        LeatherArmorMeta lam = (LeatherArmorMeta)itemstack.getItemMeta();
        lam.setColor(Color.fromRGB(r,g,b));
        itemstack.setItemMeta(lam);

        return itemstack;
    }

    public static ItemStack setColor(ItemStack itemStack, int r, int g, int b){
        ItemStack itemstack = itemStack.clone();
        LeatherArmorMeta lam = (LeatherArmorMeta)itemstack.getItemMeta();
        lam.setColor(Color.fromRGB(r,g,b));
        itemstack.setItemMeta(lam);

        return itemstack;
    }
}