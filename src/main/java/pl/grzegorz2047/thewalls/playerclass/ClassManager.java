package pl.grzegorz2047.thewalls.playerclass;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.grzegorz2047.thewalls.TheWalls;
import pl.grzegorz2047.thewalls.api.playersclasses.CustomInventory;
import pl.grzegorz2047.thewalls.api.util.CreateItemUtil;
import pl.grzegorz2047.thewalls.api.util.ItemUtil;

import java.util.HashMap;

/**
 * Created by grzeg on 22.05.2016.
 */
public class ClassManager {

    private final TheWalls plugin;

    public HashMap<CLASS, HashMap<String, CustomInventory>> getClassInventory() {
        return classInventory;
    }

    private HashMap<CLASS, HashMap<String, CustomInventory>> classInventory = new HashMap<CLASS, HashMap<String, CustomInventory>>();
    private Inventory classMenu;

    public enum CLASS {WOJOWNIK, DRWAL, GORNIK, LUCZNIK, KUCHARZ, ALCHEMIK}

    private HashMap<String, CLASS> classes = new HashMap<String, CLASS>();


    public ClassManager(TheWalls plugin) {
        this.plugin = plugin;
        for (CLASS c : CLASS.values()) {
            classInventory.put(c, new HashMap<String, CustomInventory>());
            classInventory.get(c).put("Gracz", new CustomInventory());
            classInventory.get(c).put("Vip", new CustomInventory());
        }
        addGornikItems();
        addWojownikItems();
        addLucznikItems();
        addDrwalItems();
        addKucharzItems();
        addAlchemikItems();
        classMenu = Bukkit.createInventory(null, 18, "Kits");
        createClassMenuItems();
    }

    private void createClassMenuItems() {
        ItemStack wojownik = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta wojownikim = wojownik.getItemMeta();
        wojownikim.setDisplayName("§7Wojownik");
        wojownik.setItemMeta(wojownikim);
        classMenu.setItem(1, wojownik);

        ItemStack drwal = new ItemStack(Material.IRON_AXE, 1);
        ItemMeta drwalim = wojownik.getItemMeta();
        drwalim.setDisplayName("§7Drwal");
        drwal.setItemMeta(drwalim);
        classMenu.setItem(3, drwal);

        ItemStack gornik = new ItemStack(Material.IRON_PICKAXE, 1);
        ItemMeta gornikim = wojownik.getItemMeta();
        gornikim.setDisplayName("§7Gornik");
        gornik.setItemMeta(gornikim);
        classMenu.setItem(5, gornik);

        ItemStack lucznik = new ItemStack(Material.BOW, 1);
        ItemMeta lucznikim = lucznik.getItemMeta();
        lucznikim.setDisplayName("§7Lucznik");
        lucznik.setItemMeta(lucznikim);
        classMenu.setItem(7, lucznik);

        ItemStack kucharz = new ItemStack(Material.GRILLED_PORK, 1);
        ItemMeta kucharzim = kucharz.getItemMeta();
        kucharzim.setDisplayName("§7Kucharz");
        kucharz.setItemMeta(kucharzim);
        classMenu.setItem(12, kucharz);

        ItemStack alchemik = new ItemStack(Material.POTION, 1);
        ItemMeta alchemikim = alchemik.getItemMeta();
        alchemikim.setDisplayName("§7Alchemik");
        alchemik.setItemMeta(alchemikim);
        classMenu.setItem(14, alchemik);
    }

    public Inventory getClassMenu() {
        return classMenu;
    }


    private void addGornikItems() {
        classInventory.get(CLASS.GORNIK).get("Gracz").getInventory().addItem(new ItemStack(Material.getMaterial(274), 1));
        classInventory.get(CLASS.GORNIK).get("Gracz").getInventory().addItem(new ItemStack(Material.getMaterial(263), 8));
        classInventory.get(CLASS.GORNIK).get("Gracz").getInventory().addItem(new ItemStack(Material.getMaterial(50), 8));
        classInventory.get(CLASS.GORNIK).get("Gracz").getInventory().addItem(new ItemStack(Material.getMaterial(364), 5));


        ItemStack pickaxe = new ItemStack(Material.getMaterial(257), 1);
        pickaxe.addEnchantment(Enchantment.DIG_SPEED, 1);
        classInventory.get(CLASS.GORNIK).get("Vip").getInventory().addItem(pickaxe);
        classInventory.get(CLASS.GORNIK).get("Vip").getInventory().addItem(new ItemStack(Material.getMaterial(263), 16));
        classInventory.get(CLASS.GORNIK).get("Vip").getInventory().addItem(new ItemStack(Material.getMaterial(50), 16));
        classInventory.get(CLASS.GORNIK).get("Vip").getInventory().addItem(new ItemStack(Material.getMaterial(364), 10));


    }

    private void addWojownikItems() {
        ItemStack sword = new ItemStack(Material.getMaterial(267), 1);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        classInventory.get(CLASS.WOJOWNIK).get("Gracz").getInventory().addItem(sword);
        classInventory.get(CLASS.WOJOWNIK).get("Gracz").getInventory().addItem(new ItemStack(Material.getMaterial(364), 5));
        classInventory.get(CLASS.WOJOWNIK).get("Gracz").getInventory().addItem(new ItemStack(Material.getMaterial(322), 2));

        ItemStack vipsword = new ItemStack(Material.getMaterial(267), 1);
        vipsword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        classInventory.get(CLASS.WOJOWNIK).get("Vip").getInventory().addItem(vipsword);
        classInventory.get(CLASS.WOJOWNIK).get("Vip").getInventory().addItem(new ItemStack(Material.getMaterial(364), 10));
        classInventory.get(CLASS.WOJOWNIK).get("Vip").getInventory().addItem(new ItemStack(Material.getMaterial(322), 4));
    }

    private void addLucznikItems() {
        classInventory.get(CLASS.LUCZNIK).get("Gracz").getInventory().addItem(new ItemStack(Material.getMaterial(261), 1));
        classInventory.get(CLASS.LUCZNIK).get("Gracz").getInventory().addItem(new ItemStack(Material.getMaterial(262), 32));
        classInventory.get(CLASS.LUCZNIK).get("Gracz").getInventory().addItem(new ItemStack(Material.getMaterial(364), 5));

        ItemStack vipbow = new ItemStack(Material.getMaterial(261), 1);
        vipbow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
        classInventory.get(CLASS.LUCZNIK).get("Vip").getInventory().addItem(vipbow);
        classInventory.get(CLASS.LUCZNIK).get("Vip").getInventory().addItem(new ItemStack(Material.getMaterial(262), 64));
        classInventory.get(CLASS.LUCZNIK).get("Vip").getInventory().addItem(new ItemStack(Material.getMaterial(364), 10));
        classInventory.get(CLASS.LUCZNIK).get("Vip").getInventory().addItem(new ItemStack(Material.getMaterial(322), 3));
        classInventory.get(CLASS.LUCZNIK).get("Vip").getInventory().addItem(new ItemStack(Material.getMaterial(298), 1));
        classInventory.get(CLASS.LUCZNIK).get("Vip").getInventory().addItem(new ItemStack(Material.getMaterial(301), 1));

    }

    public void addDrwalItems() {
        classInventory.get(CLASS.DRWAL).get("Gracz").getInventory().addItem(new ItemStack(Material.getMaterial(275), 1));
        classInventory.get(CLASS.DRWAL).get("Gracz").getInventory().addItem(new ItemStack(Material.getMaterial(17), 16));
        classInventory.get(CLASS.DRWAL).get("Gracz").getInventory().addItem(new ItemStack(Material.getMaterial(364), 5));

        classInventory.get(CLASS.DRWAL).get("Vip").getInventory().addItem(new ItemStack(Material.getMaterial(258), 1));
        classInventory.get(CLASS.DRWAL).get("Vip").getInventory().addItem(new ItemStack(Material.getMaterial(17), 32));
        classInventory.get(CLASS.DRWAL).get("Vip").getInventory().addItem(new ItemStack(Material.getMaterial(65), 16));
        classInventory.get(CLASS.DRWAL).get("Vip").getInventory().addItem(new ItemStack(Material.getMaterial(364), 10));
    }

    public void addKucharzItems() {
        ItemStack sword = new ItemStack(Material.getMaterial(267), 1);
        sword.addEnchantment(Enchantment.KNOCKBACK, 1);
        classInventory.get(CLASS.KUCHARZ).get("Gracz").getInventory().addItem(sword);
        classInventory.get(CLASS.KUCHARZ).get("Gracz").getInventory().addItem(new ItemStack(Material.getMaterial(354), 1));
        classInventory.get(CLASS.KUCHARZ).get("Gracz").getInventory().addItem(new ItemStack(Material.getMaterial(297), 16));
        classInventory.get(CLASS.KUCHARZ).get("Gracz").getInventory().addItem(new ItemStack(Material.getMaterial(363), 32));
        classInventory.get(CLASS.KUCHARZ).get("Gracz").getInventory().addItem(new ItemStack(Material.getMaterial(322), 16));
        classInventory.get(CLASS.KUCHARZ).get("Gracz").getInventory().addItem(new ItemStack(Material.getMaterial(61), 2));
        classInventory.get(CLASS.KUCHARZ).get("Gracz").getInventory().addItem(new ItemStack(Material.getMaterial(263), 4));

        ItemStack vipsword = new ItemStack(Material.getMaterial(267), 1);
        sword.addEnchantment(Enchantment.KNOCKBACK, 2);
        classInventory.get(CLASS.KUCHARZ).get("Vip").getInventory().addItem(vipsword);
        classInventory.get(CLASS.KUCHARZ).get("Vip").getInventory().addItem(new ItemStack(Material.getMaterial(354), 1));
        classInventory.get(CLASS.KUCHARZ).get("Vip").getInventory().addItem(new ItemStack(Material.getMaterial(297), 16));
        classInventory.get(CLASS.KUCHARZ).get("Vip").getInventory().addItem(new ItemStack(Material.getMaterial(363), 32));
        classInventory.get(CLASS.KUCHARZ).get("Vip").getInventory().addItem(new ItemStack(Material.getMaterial(322), 16));
        classInventory.get(CLASS.KUCHARZ).get("Vip").getInventory().addItem(new ItemStack(Material.getMaterial(61), 2));
        classInventory.get(CLASS.KUCHARZ).get("Vip").getInventory().addItem(new ItemStack(Material.getMaterial(263), 4));
    }

    public void addAlchemikItems() {
        classInventory.get(CLASS.ALCHEMIK).get("Gracz").getInventory().addItem(CreateItemUtil.createItem(Material.getMaterial(379), 1));
        classInventory.get(CLASS.ALCHEMIK).get("Gracz").getInventory().addItem(CreateItemUtil.createItem(Material.getMaterial(380), 1));
        classInventory.get(CLASS.ALCHEMIK).get("Gracz").getInventory().addItem(CreateItemUtil.createItem(Material.getMaterial(326), 1));
        classInventory.get(CLASS.ALCHEMIK).get("Gracz").getInventory().addItem(CreateItemUtil.createItem(Material.getMaterial(374), 6));
        classInventory.get(CLASS.ALCHEMIK).get("Gracz").getInventory().addItem(CreateItemUtil.createItem(Material.getMaterial(372), 3));
        classInventory.get(CLASS.ALCHEMIK).get("Gracz").getInventory().addItem(CreateItemUtil.createItem(Material.getMaterial(378), 1));
        classInventory.get(CLASS.ALCHEMIK).get("Gracz").getInventory().addItem(CreateItemUtil.createItem(Material.getMaterial(353), 1));
        classInventory.get(CLASS.ALCHEMIK).get("Gracz").getInventory().addItem(CreateItemUtil.createItem(Material.getMaterial(348), 1));
        classInventory.get(CLASS.ALCHEMIK).get("Gracz").getInventory().addItem(CreateItemUtil.createItem(Material.getMaterial(382), 1));
        classInventory.get(CLASS.ALCHEMIK).get("Gracz").getInventory().addItem(CreateItemUtil.createItem(Material.getMaterial(370), 1));
        classInventory.get(CLASS.ALCHEMIK).get("Gracz").getInventory().addItem(CreateItemUtil.createItem(Material.getMaterial(289), 3));
        classInventory.get(CLASS.ALCHEMIK).get("Gracz").getInventory().addItem(CreateItemUtil.createItem(Material.getMaterial(364), 12));

        classInventory.get(CLASS.ALCHEMIK).get("Vip").getInventory().addItem(CreateItemUtil.createItem(Material.getMaterial(379), 1));
        classInventory.get(CLASS.ALCHEMIK).get("Vip").getInventory().addItem(CreateItemUtil.createItem(Material.getMaterial(380), 1));
        classInventory.get(CLASS.ALCHEMIK).get("Vip").getInventory().addItem(CreateItemUtil.createItem(Material.getMaterial(326), 1));
        classInventory.get(CLASS.ALCHEMIK).get("Vip").getInventory().addItem(CreateItemUtil.createItem(Material.getMaterial(374), 6));
        classInventory.get(CLASS.ALCHEMIK).get("Vip").getInventory().addItem(CreateItemUtil.createItem(Material.getMaterial(372), 3));
        classInventory.get(CLASS.ALCHEMIK).get("Vip").getInventory().addItem(CreateItemUtil.createItem(Material.getMaterial(378), 1));
        classInventory.get(CLASS.ALCHEMIK).get("Vip").getInventory().addItem(CreateItemUtil.createItem(Material.getMaterial(353), 1));
        classInventory.get(CLASS.ALCHEMIK).get("Vip").getInventory().addItem(CreateItemUtil.createItem(Material.getMaterial(348), 1));
        classInventory.get(CLASS.ALCHEMIK).get("Vip").getInventory().addItem(CreateItemUtil.createItem(Material.getMaterial(382), 1));
        classInventory.get(CLASS.ALCHEMIK).get("Vip").getInventory().addItem(CreateItemUtil.createItem(Material.getMaterial(370), 1));
        classInventory.get(CLASS.ALCHEMIK).get("Vip").getInventory().addItem(CreateItemUtil.createItem(Material.getMaterial(289), 3));
        classInventory.get(CLASS.ALCHEMIK).get("Vip").getInventory().addItem(CreateItemUtil.createItem(Material.getMaterial(364), 12));

    }

    public HashMap<String, CLASS> getPlayerClasses() {
        return classes;
    }


}
