package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scoreboard.Scoreboard;
import pl.grzegorz2047.databaseapi.messages.MessageAPI;
import pl.grzegorz2047.databaseapi.shop.Item;
import pl.grzegorz2047.databaseapi.shop.ShopAPI;
import pl.grzegorz2047.databaseapi.shop.Transaction;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameUser;
import pl.grzegorz2047.thewalls.GameUsers;
import pl.grzegorz2047.thewalls.api.itemmenu.event.ChooseItemEvent;
import pl.grzegorz2047.thewalls.playerclass.ClassManager;
import pl.grzegorz2047.thewalls.scoreboard.ScoreboardAPI;
import pl.grzegorz2047.thewalls.shop.Shop;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by grzeg on 21.05.2016.
 */
public class ChooseItem implements Listener {

    private final GameData gameData;
    private final MessageAPI messageManager;
    private final Shop shopMenuManager;

    private final ShopAPI shopManager;
    private final ScoreboardAPI scoreboardAPI;
    private final ClassManager classManager;
    private final GameUsers gameUsers;

    public ChooseItem(MessageAPI messageManager, GameData gameData, Shop shopMenuManager, ScoreboardAPI scoreboardAPI, ShopAPI shopManager, ClassManager classManager, GameUsers gameUsers) {
        this.gameData = gameData;
        this.messageManager = messageManager;
        this.shopMenuManager = shopMenuManager;
        this.shopManager = shopManager;
        this.scoreboardAPI = scoreboardAPI;
        this.classManager = classManager;
        this.gameUsers = gameUsers;
    }


    @EventHandler
    public void onChoose(ChooseItemEvent e) {
        Player p = e.getPlayer();
        String title = e.getTitle();

        if (title != null) {
            ItemStack clicked = e.getClicked();
            if (clicked == null) {
                return;
            }

            Material clickedType = clicked.getType();
            if (clickedType == null) {
                return;
            }

            String playerName = p.getName();
            if (title.equals("Kits")) {
                e.setCancelled(true);
                GameUser user = gameUsers.getGameUser(playerName);
                ClassManager.CLASS kit = null;
                ClassManager classManager = this.classManager;
                HashMap<String, ClassManager.CLASS> playerClasses = classManager.getPlayerClasses();
                if (clickedType.equals(Material.IRON_SWORD)) {
                    playerClasses.put(playerName, ClassManager.CLASS.WOJOWNIK);
                    kit = ClassManager.CLASS.WOJOWNIK;
                } else if (clickedType.equals(Material.IRON_PICKAXE)) {
                    playerClasses.put(playerName, ClassManager.CLASS.GORNIK);
                    kit = ClassManager.CLASS.GORNIK;

                } else if (clickedType.equals(Material.IRON_AXE)) {
                    playerClasses.put(playerName, ClassManager.CLASS.DRWAL);
                    kit = ClassManager.CLASS.DRWAL;

                } else if (clickedType.equals(Material.COOKED_PORKCHOP)) {
                    playerClasses.put(playerName, ClassManager.CLASS.KUCHARZ);
                    kit = ClassManager.CLASS.KUCHARZ;

                } else if (clickedType.equals(Material.POTION)) {
                    playerClasses.put(playerName, ClassManager.CLASS.ALCHEMIK);
                    kit = ClassManager.CLASS.ALCHEMIK;

                } else if (clickedType.equals(Material.BOW)) {
                    playerClasses.put(playerName, ClassManager.CLASS.LUCZNIK);
                    kit = ClassManager.CLASS.LUCZNIK;

                }
                if (kit == null) {
                    return;
                }
                String userLanguage = user.getLanguage();
                p.sendMessage(messageManager.getMessage(userLanguage, "thewalls.msg.kitgiven").replace("{KIT}", kit.name()));
                p.closeInventory();
            }
            if (title.equals("Main")) {
                e.setCancelled(true);
                if (clickedType.equals(Material.QUARTZ)) {
                    GameUser user = gameUsers.getGameUser(playerName);
                    shopMenuManager.openTempItems(p, user, messageManager);
                }
                if (clickedType.equals(Material.MAGMA_CREAM)) {
                    GameUser user = gameUsers.getGameUser(playerName);
                    shopMenuManager.openPermItems(p, user, messageManager);
                }
                return;
            }

            PlayerInventory playerInventory = p.getInventory();
            if (title.equals("Perm items")) {
                GameUser user = gameUsers.getGameUser(playerName);
                e.setCancelled(true);

                int slot = e.getSlot();
                for (Map.Entry<Integer, Item> entry : getListOfItems(shopMenuManager.getNormalItems())) {
                    Item item = entry.getValue();
                    if (item.getSlot() == slot) {
                        String userLanguage = user.getLanguage();
                        for (Transaction t : user.getTransactions()) {
                            if (t.getItemid() == item.getItemid()) {
                                p.sendMessage(messageManager.getMessage(userLanguage, "shop.alreadybought"));
                                return;
                            }
                        }
                        if (user.getMoney() >= item.getPrice()) {
                            if (playerInventory.firstEmpty() != -1) {
                                shopManager.buyItem(playerName, String.valueOf(item.getItemid()), Instant.EPOCH.getEpochSecond());
                                user.changeMoney(-item.getPrice());
                                Scoreboard scoreboard = p.getScoreboard();
                                scoreboardAPI.updateIncreaseEntry(scoreboard, messageManager.getMessage(userLanguage, "thewalls.scoreboard.money"), -item.getPrice());
                                user.getTransactions().add(new Transaction(user.getUserid(), item.getItemid(), 0));
                                playerInventory.addItem(item.toItemStack());
                                p.sendMessage(messageManager.getMessage(userLanguage, "shop.success"));
                                return;
                            } else {
                                p.sendMessage(messageManager.getMessage(userLanguage, "shop.emptyinventoryfirst"));
                                return;
                            }
                        } else {
                            p.sendMessage(messageManager.getMessage(userLanguage, "shop.insufficientmoney"));
                            return;
                        }
                    }
                }
            }
            if (title.equals("Temp items")) {
                GameUser user = gameUsers.getGameUser(playerName);
                e.setCancelled(true);

                int slot = e.getSlot();
                for (Map.Entry<Integer, Item> entry : getListOfItems(shopMenuManager.getTempItems())) {
                    Item item = entry.getValue();
                    if (item.getSlot() == slot) {
                        String userLanguage = user.getLanguage();
                        List<Material> userBoughtTempItems = user.getBoughtTempItems();
                        if (userBoughtTempItems.contains(item.getMaterial())) {
                            p.sendMessage(messageManager.getMessage(userLanguage, "shop.alreadyboughtinthisround"));
                            return;
                        }
                        if (user.getMoney() >= item.getPrice()) {
                            if (playerInventory.firstEmpty() != -1) {
                                user.changeMoney(-item.getPrice());
                                userBoughtTempItems.add(item.getMaterial());
                                playerInventory.addItem(item.toItemStack());
                                Scoreboard scoreboard = p.getScoreboard();
                                scoreboardAPI.updateIncreaseEntry(scoreboard, messageManager.getMessage(userLanguage, "thewalls.scoreboard.money"), -item.getPrice());
                                p.sendMessage(messageManager.getMessage(userLanguage, "shop.success"));
                                return;
                            } else {
                                p.sendMessage(messageManager.getMessage(userLanguage, "shop.emptyinventoryfirst"));
                                return;
                            }
                        } else {
                            p.sendMessage(messageManager.getMessage(userLanguage, "shop.insufficientmoney"));
                            return;
                        }
                    }
                }
            }
        }
    }

    private Set<Map.Entry<Integer, Item>> getListOfItems(HashMap<Integer, Item> tempItems) {
        return tempItems.entrySet();
    }
}
