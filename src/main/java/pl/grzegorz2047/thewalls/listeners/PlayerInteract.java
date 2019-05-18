package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import pl.grzegorz2047.databaseapi.messages.MessageAPI;
import pl.grzegorz2047.thewalls.Counter;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameUser;
import pl.grzegorz2047.thewalls.TheWalls;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by grzeg on 17.05.2016.
 */
public class PlayerInteract implements Listener {

    private final TheWalls plugin;
    private final GameData gameData;
    private final MessageAPI messageManager;
    private final Counter counter;

    public PlayerInteract(TheWalls plugin) {
        this.plugin = plugin;
        gameData = plugin.getGameData();
        messageManager = plugin.getMessageManager();
        counter = gameData.getCounter();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack itemInHand = player.getItemInHand();
        if (itemInHand == null) {
            return;
        }
        GameMode playerGameMode = player.getGameMode();
        if (playerGameMode.equals(GameMode.SPECTATOR)) {
            event.setCancelled(true);
            return;
        }
        Material itemInHandType = itemInHand.getType();
        if (itemInHandType.equals(Material.BOOK)) {
            if (!gameData.isStatus(GameData.GameStatus.INGAME)) {
                player.openInventory(gameData.getClassManager().getClassMenu());
                return;
            }
        }
        String playerName = player.getName();
        if (itemInHandType.equals(Material.NETHER_STAR)) {
            GameUser user = gameData.getGameUser(playerName);
            if (!gameData.isStatus(GameData.GameStatus.INGAME)) {
                player.sendMessage(messageManager.getMessage(user.getLanguage(), "thewalls.shoponlyingame"));
                return;
            }
            if (user.getAssignedTeam() == null) {
                return;
            }
            plugin.getShopMenuManager().openMainMenu(player, user, messageManager);
        }
        if (itemInHandType.equals(Material.ENDER_PEARL)) {
            if (counter.getStatus().equals(Counter.CounterStatus.COUNTINGTODROPWALLS)) {
                GameUser user = gameData.getGameUser(playerName);
                player.sendMessage(messageManager.getMessage(user.getLanguage(), "thewalls.msg.cantplacelava"));
                event.setCancelled(true);
                return;
            }
        }
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null) {
            return;
        }
        String protectedFurnacePlayer = gameData.getPlayerProtectedFurnace(clickedBlock.getLocation());
        if (protectedFurnacePlayer != null) {
            if (!protectedFurnacePlayer.equals(playerName)) {
                GameUser user = gameData.getGameUser(playerName);
                String userLanguage = user.getLanguage();
                player.sendMessage(messageManager.getMessage(userLanguage, "thewalls.msg.someonesprotectedfurnace"));
                event.setCancelled(true);
                return;
            }
        }
        if (!(clickedBlock.getState() instanceof Chest)) {
            return;
        }
        Chest cb = (Chest) clickedBlock.getState();

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
        clickedBlock.setType(Material.AIR);
    }

    private String getPlayerProtectedFurnace(HashMap<Location, String> protectedFurnaces, Location location) {
        return protectedFurnaces.get(location);
    }

}
