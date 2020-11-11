package pl.grzegorz2047.thewalls.playerclass;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.grzegorz2047.databaseapi.messages.MessageAPI;
import pl.grzegorz2047.thewalls.GameUser;
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
    private final MessageAPI messageManager;

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
        messageManager = plugin.getMessageManager();
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

        ItemStack kucharz = new ItemStack(Material.COOKED_PORKCHOP, 1);
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
        Inventory standardDiggerInventory = classInventory.get(CLASS.GORNIK).get("Gracz").getInventory();
        standardDiggerInventory.addItem(new ItemStack(Material.STONE_PICKAXE, 1));
        standardDiggerInventory.addItem(new ItemStack(Material.CHARCOAL, 8));
        standardDiggerInventory.addItem(new ItemStack(Material.TORCH, 8));
        standardDiggerInventory.addItem(new ItemStack(Material.COOKED_BEEF, 5));


        ItemStack pickaxe = new ItemStack(Material.IRON_PICKAXE, 1);
        pickaxe.addEnchantment(Enchantment.DIG_SPEED, 1);
        Inventory vipDiggerInventory = classInventory.get(CLASS.GORNIK).get("Vip").getInventory();
        vipDiggerInventory.addItem(pickaxe);
        vipDiggerInventory.addItem(new ItemStack(Material.CHARCOAL, 16));
        vipDiggerInventory.addItem(new ItemStack(Material.TORCH, 16));
        vipDiggerInventory.addItem(new ItemStack(Material.COOKED_BEEF, 10));


    }

    private void addWojownikItems() {
        ItemStack sword = new ItemStack(Material.IRON_SWORD, 1);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        Inventory standardFighterInventory = classInventory.get(CLASS.WOJOWNIK).get("Gracz").getInventory();
        standardFighterInventory.addItem(sword);
        standardFighterInventory.addItem(new ItemStack(Material.COOKED_BEEF, 5));
        standardFighterInventory.addItem(new ItemStack(Material.GOLDEN_APPLE, 2));

        ItemStack vipsword = new ItemStack(Material.IRON_SWORD, 1);
        vipsword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        Inventory vipFighterInventory = classInventory.get(CLASS.WOJOWNIK).get("Vip").getInventory();
        vipFighterInventory.addItem(vipsword);
        vipFighterInventory.addItem(new ItemStack(Material.COOKED_BEEF, 10));
        vipFighterInventory.addItem(new ItemStack(Material.GOLDEN_APPLE, 4));
    }

    private void addLucznikItems() {
        Inventory standardBowmanInventory = classInventory.get(CLASS.LUCZNIK).get("Gracz").getInventory();
        standardBowmanInventory.addItem(new ItemStack(Material.BOW, 1));
        standardBowmanInventory.addItem(new ItemStack(Material.ARROW, 32));
        standardBowmanInventory.addItem(new ItemStack(Material.COOKED_BEEF, 5));

        ItemStack vipbow = new ItemStack(Material.BOW, 1);
        vipbow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
        Inventory vipBowmanInventory = classInventory.get(CLASS.LUCZNIK).get("Vip").getInventory();
        vipBowmanInventory.addItem(vipbow);
        vipBowmanInventory.addItem(new ItemStack(Material.ARROW, 64));
        vipBowmanInventory.addItem(new ItemStack(Material.COOKED_BEEF, 10));
        vipBowmanInventory.addItem(new ItemStack(Material.GOLDEN_APPLE, 3));
        vipBowmanInventory.addItem(new ItemStack(Material.LEATHER_HELMET, 1));
        vipBowmanInventory.addItem(new ItemStack(Material.LEATHER_BOOTS, 1));

    }

    public void addDrwalItems() {
        Inventory standardLumberjackInventory = classInventory.get(CLASS.DRWAL).get("Gracz").getInventory();
        standardLumberjackInventory.addItem(new ItemStack(Material.SPIDER_EYE, 1));
        standardLumberjackInventory.addItem(new ItemStack(Material.OAK_WOOD, 16));
        standardLumberjackInventory.addItem(new ItemStack(Material.COOKED_BEEF, 5));

        Inventory vipLumberjackInventory = classInventory.get(CLASS.DRWAL).get("Vip").getInventory();
        vipLumberjackInventory.addItem(new ItemStack(Material.IRON_AXE, 1));
        vipLumberjackInventory.addItem(new ItemStack(Material.OAK_WOOD, 32));
        vipLumberjackInventory.addItem(new ItemStack(Material.LADDER, 16));
        vipLumberjackInventory.addItem(new ItemStack(Material.COOKED_BEEF, 10));
    }

    public void addKucharzItems() {
        ItemStack sword = new ItemStack(Material.IRON_SWORD, 1);
        sword.addEnchantment(Enchantment.KNOCKBACK, 1);
        Inventory standardFoodInventory = classInventory.get(CLASS.KUCHARZ).get("Gracz").getInventory();
        standardFoodInventory.addItem(sword);
        standardFoodInventory.addItem(new ItemStack(Material.CAKE, 1));
        standardFoodInventory.addItem(new ItemStack(Material.BREAD, 16));
        standardFoodInventory.addItem(new ItemStack(Material.BEEF, 32));
        standardFoodInventory.addItem(new ItemStack(Material.GOLDEN_APPLE, 16));
        standardFoodInventory.addItem(new ItemStack(Material.FURNACE, 2));
        standardFoodInventory.addItem(new ItemStack(Material.CHARCOAL, 4));

        ItemStack vipsword = new ItemStack(Material.IRON_SWORD, 1);
        sword.addEnchantment(Enchantment.KNOCKBACK, 2);
        Inventory vipFoodInventory = classInventory.get(CLASS.KUCHARZ).get("Vip").getInventory();
        vipFoodInventory.addItem(vipsword);
        vipFoodInventory.addItem(new ItemStack(Material.CAKE, 1));
        vipFoodInventory.addItem(new ItemStack(Material.BREAD, 16));
        vipFoodInventory.addItem(new ItemStack(Material.BEEF, 32));
        vipFoodInventory.addItem(new ItemStack(Material.GOLDEN_APPLE, 16));
        vipFoodInventory.addItem(new ItemStack(Material.FURNACE, 2));
        vipFoodInventory.addItem(new ItemStack(Material.CHARCOAL, 4));
    }

    public void addAlchemikItems() {
        HashMap<String, CustomInventory> alchemyInventories = classInventory.get(CLASS.ALCHEMIK);
        Inventory standardPlayerAlchemyInventory = alchemyInventories.get("Gracz").getInventory();
        Inventory vipAlchemyInventory = alchemyInventories.get("Vip").getInventory();

        standardPlayerAlchemyInventory.addItem(CreateItemUtil.createItem(Material.BREWING_STAND, 1));
        standardPlayerAlchemyInventory.addItem(CreateItemUtil.createItem(Material.CAULDRON, 1));
        standardPlayerAlchemyInventory.addItem(CreateItemUtil.createItem(Material.WATER_BUCKET, 1));
        standardPlayerAlchemyInventory.addItem(CreateItemUtil.createItem(Material.GLASS_BOTTLE, 6));
        standardPlayerAlchemyInventory.addItem(CreateItemUtil.createItem(Material.NETHER_WART, 3));
        standardPlayerAlchemyInventory.addItem(CreateItemUtil.createItem(Material.MAGMA_CREAM, 1));
        standardPlayerAlchemyInventory.addItem(CreateItemUtil.createItem(Material.SUGAR, 1));
        standardPlayerAlchemyInventory.addItem(CreateItemUtil.createItem(Material.GLOWSTONE_DUST, 1));
        standardPlayerAlchemyInventory.addItem(CreateItemUtil.createItem(Material.GLISTERING_MELON_SLICE, 1));
        standardPlayerAlchemyInventory.addItem(CreateItemUtil.createItem(Material.GHAST_TEAR, 1));
        standardPlayerAlchemyInventory.addItem(CreateItemUtil.createItem(Material.GUNPOWDER, 3));
        standardPlayerAlchemyInventory.addItem(CreateItemUtil.createItem(Material.COOKED_BEEF, 12));

        vipAlchemyInventory.addItem(CreateItemUtil.createItem(Material.BREWING_STAND, 1));
        vipAlchemyInventory.addItem(CreateItemUtil.createItem(Material.CAULDRON, 1));
        vipAlchemyInventory.addItem(CreateItemUtil.createItem(Material.WATER_BUCKET, 1));
        vipAlchemyInventory.addItem(CreateItemUtil.createItem(Material.GLASS_BOTTLE, 6));
        vipAlchemyInventory.addItem(CreateItemUtil.createItem(Material.NETHER_WART, 3));
        vipAlchemyInventory.addItem(CreateItemUtil.createItem(Material.MAGMA_CREAM, 1));
        vipAlchemyInventory.addItem(CreateItemUtil.createItem(Material.SUGAR, 1));
        vipAlchemyInventory.addItem(CreateItemUtil.createItem(Material.GLOWSTONE_DUST, 1));
        vipAlchemyInventory.addItem(CreateItemUtil.createItem(Material.GLISTERING_MELON_SLICE, 1));
        vipAlchemyInventory.addItem(CreateItemUtil.createItem(Material.GHAST_TEAR, 1));
        vipAlchemyInventory.addItem(CreateItemUtil.createItem(Material.GUNPOWDER, 3));
        vipAlchemyInventory.addItem(CreateItemUtil.createItem(Material.COOKED_BEEF, 12));

    }

    public HashMap<String, CLASS> getPlayerClasses() {
        return classes;
    }

    public void givePlayerClass(Player p, GameUser user) {
        ClassManager.CLASS playerClass = this.getPlayerClasses().get(p.getName());
        if (playerClass == null) {
            playerClass = ClassManager.CLASS.GORNIK;
        }
        String kittype = "Gracz";
        if (!user.getRank().equals("Gracz")) {
            kittype = "Vip";
        }
        for (ItemStack it : this.getClassInventory().get(playerClass).get(kittype).getInventory()) {
            if (it != null) {
                p.getInventory().addItem(it);
            }
        }
        p.sendMessage(messageManager.getMessage(user.getLanguage(), "thewalls.msg.classgiven").replace("{CLASS}", playerClass.name()));
    }

}
