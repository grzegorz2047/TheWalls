package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import pl.grzegorz2047.thewalls.Counter;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameUser;
import pl.grzegorz2047.thewalls.TheWalls;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by grzeg on 17.05.2016.
 */
public class PlayerInteract implements Listener {

    private final TheWalls plugin;

    public PlayerInteract(TheWalls plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        if (player.getItemInHand() == null) {
            return;
        }
        if (player.getGameMode().equals(GameMode.SPECTATOR)) {
            event.setCancelled(true);
            return;
        }
        if (player.getItemInHand().getType().equals(Material.BOOK)) {
            if (!plugin.getGameData().getStatus().equals(GameData.GameStatus.INGAME)) {
                player.openInventory(plugin.getGameData().getClassManager().getClassMenu());
                return;
            }
        }
        if (player.getItemInHand().getType().equals(Material.NETHER_STAR)) {
            GameUser user = plugin.getGameData().getGameUsers().get(player.getName());
            if (!plugin.getGameData().getStatus().equals(GameData.GameStatus.INGAME)) {
                player.sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.shoponlyingame"));
                return;
            }
            if (user.getAssignedTeam() == null) {
                return;
            }
            plugin.getShopMenuManager().openMainMenu(player, user, plugin.getMessageManager());
        }
        if (player.getItemInHand().getType().equals(Material.ENDER_PEARL)) {
            if (plugin.getGameData().getCounter().getStatus().equals(Counter.CounterStatus.COUNTINGTODROPWALLS)) {
                GameUser user = plugin.getGameData().getGameUsers().get(player.getName());
                player.sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.msg.cantplacelava"));
                event.setCancelled(true);
                return;
            }
        }
        if (event.getClickedBlock() == null) {
            return;
        }
        String protectedFurnacePlayer = plugin.getGameData().getProtectedFurnace().get(event.getClickedBlock().getLocation());
        if (protectedFurnacePlayer != null) {
            if (!protectedFurnacePlayer.equals(player.getName())) {
                GameUser user = plugin.getGameData().getGameUsers().get(player.getName());
                player.sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.msg.someonesprotectedfurnace"));
                event.setCancelled(true);
                return;
            }
        }
        if (!(event.getClickedBlock().getState() instanceof Chest)) {
            return;
        }
        Chest cb = (Chest) event.getClickedBlock().getState();

        if (!cb.getInventory().contains(Material.BEDROCK)) {
            return;
        }
        List<Material> materialList = Arrays.asList(Material.ENDER_PEARL, Material.EXP_BOTTLE, Material.GOLDEN_APPLE, Material.APPLE, Material.GOLD_INGOT, Material.IRON_INGOT, Material.WOOD);
        int pearl = 0;
        for (int i = 0; i < 4; i++) {
            //System.out.println("Losuje skrzynke!");
            Random r = new Random();
            PlayerInventory inventory = player.getInventory(); // The player's inventory
            //System.out.println("Test Destroy box");
            int index = r.nextInt(materialList.size() - 1);
            int amount = r.nextInt(3);
            amount += 1;
            Material mat = materialList.get(index);
            if (mat.equals(Material.ENDER_PEARL)) {
                if (pearl > 0) {
                    mat = Material.COOKED_BEEF;
                } else {
                    pearl++;
                }
            }
            ItemStack itemstack1 = new ItemStack(mat, amount);
            //chest.getInventory().addItem(itemstack1);
            inventory.addItem(itemstack1);
            player.updateInventory();
        }
        cb.getInventory().clear();
        event.getClickedBlock().setType(Material.AIR);
    }

}
