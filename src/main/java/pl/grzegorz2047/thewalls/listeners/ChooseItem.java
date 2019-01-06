package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import pl.grzegorz2047.databaseapi.shop.Item;
import pl.grzegorz2047.databaseapi.shop.Transaction;
import pl.grzegorz2047.thewalls.GameUser;
import pl.grzegorz2047.thewalls.TheWalls;
import pl.grzegorz2047.thewalls.api.itemmenu.event.ChooseItemEvent;
import pl.grzegorz2047.thewalls.playerclass.ClassManager;

import java.time.Instant;
import java.util.Map;

/**
 * Created by grzeg on 21.05.2016.
 */
public class ChooseItem implements Listener {

    private final TheWalls plugin;

    public ChooseItem(TheWalls plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onChoose(ChooseItemEvent e) {
        Player p = e.getPlayer();
        String title = e.getInventory().getTitle();
        // System.out.println("A");
        if (title != null) {
            //System.out.println("B");
            if (e.getClicked() == null) {
                return;
            }
            //  System.out.println("C");
            if (e.getClicked().getType() == null) {
                return;
            }
            if (title.equals("Kits")) {
                e.setCancelled(true);
                GameUser user = plugin.getGameData().getGameUsers().get(p.getName());
                ClassManager.CLASS kit = null;
                if (e.getClicked().getType().equals(Material.IRON_SWORD)) {
                    plugin.getGameData().getClassManager().getPlayerClasses().put(p.getName(), ClassManager.CLASS.WOJOWNIK);
                    kit = ClassManager.CLASS.WOJOWNIK;
                } else if (e.getClicked().getType().equals(Material.IRON_PICKAXE)) {
                    plugin.getGameData().getClassManager().getPlayerClasses().put(p.getName(), ClassManager.CLASS.GORNIK);
                    kit = ClassManager.CLASS.GORNIK;

                } else if (e.getClicked().getType().equals(Material.IRON_AXE)) {
                    plugin.getGameData().getClassManager().getPlayerClasses().put(p.getName(), ClassManager.CLASS.DRWAL);
                    kit = ClassManager.CLASS.DRWAL;

                } else if (e.getClicked().getType().equals(Material.GRILLED_PORK)) {
                    plugin.getGameData().getClassManager().getPlayerClasses().put(p.getName(), ClassManager.CLASS.KUCHARZ);
                    kit = ClassManager.CLASS.KUCHARZ;

                } else if (e.getClicked().getType().equals(Material.POTION)) {
                    plugin.getGameData().getClassManager().getPlayerClasses().put(p.getName(), ClassManager.CLASS.ALCHEMIK);
                    kit = ClassManager.CLASS.ALCHEMIK;

                } else if (e.getClicked().getType().equals(Material.BOW)) {
                    plugin.getGameData().getClassManager().getPlayerClasses().put(p.getName(), ClassManager.CLASS.LUCZNIK);
                    kit = ClassManager.CLASS.LUCZNIK;

                }
                if (kit == null) {
                    return;
                }
                p.sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.msg.kitgiven").replace("{KIT}", kit.name()));
                p.closeInventory();
            }
            if (title.equals("Main")) {
                e.setCancelled(true);
                if (e.getClicked().getType().equals(Material.QUARTZ)) {
                    GameUser user = plugin.getGameData().getGameUsers().get(p.getName());
                    plugin.getShopMenuManager().openTempItems(p, user, plugin.getMessageManager());
                }
                if (e.getClicked().getType().equals(Material.MAGMA_CREAM)) {
                    GameUser user = plugin.getGameData().getGameUsers().get(p.getName());
                    plugin.getShopMenuManager().openPermItems(p, user, plugin.getMessageManager());
                }
                return;
            }
            //System.out.println("D");
            if (title.equals("Perm items")) {
                GameUser user = plugin.getGameData().getGameUsers().get(p.getName());
                e.setCancelled(true);

                int slot = e.getSlot();
                for (Map.Entry<Integer, Item> entry : plugin.getShopMenuManager().getNormalItems().entrySet()) {
                    Item item = entry.getValue();
                    if (item.getSlot() == slot) {
                        for (Transaction t : user.getTransactions()) {
                            if (t.getItemid() == item.getItemid()) {
                                p.sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "shop.alreadybought"));
                                return;
                            }
                        }
                        if (user.getMoney() >= item.getPrice()) {
                            if (p.getInventory().firstEmpty() != -1) {
                                plugin.getShopManager().buyItem(p.getName(), String.valueOf(item.getItemid()), Instant.EPOCH.getEpochSecond());
                                user.changeMoney(-item.getPrice());
                                plugin.getMoneyManager().changePlayerMoney(p.getName(), -item.getPrice());
                                Scoreboard scoreboard = p.getScoreboard();
                                plugin.getScoreboardAPI().updateIncreaseEntry(scoreboard, plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.scoreboard.money"), -item.getPrice());
                                user.getTransactions().add(new Transaction(user.getUserid(), item.getItemid(), 0));
                                p.getInventory().addItem(item.toItemStack());
                                p.sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "shop.success"));
                                return;
                            } else {
                                p.sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "shop.emptyinventoryfirst"));
                                return;
                            }
                        } else {
                            p.sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "shop.insufficientmoney"));
                            return;
                        }
                    }
                }
            }
            if (title.equals("Temp items")) {
                GameUser user = plugin.getGameData().getGameUsers().get(p.getName());
                e.setCancelled(true);

                int slot = e.getSlot();
                for (Map.Entry<Integer, Item> entry : plugin.getShopMenuManager().getTempItems().entrySet()) {
                    Item item = entry.getValue();
                    if (item.getSlot() == slot) {
                        if (user.getBoughtTempItems().contains(item.getMaterial())) {
                            p.sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "shop.alreadyboughtinthisround"));
                            return;
                        }
                        if (user.getMoney() >= item.getPrice()) {
                            if (p.getInventory().firstEmpty() != -1) {
                                user.changeMoney(-item.getPrice());
                                plugin.getMoneyManager().changePlayerMoney(p.getName(), -item.getPrice());
                                user.getBoughtTempItems().add(item.getMaterial());
                                p.getInventory().addItem(item.toItemStack());
                                Scoreboard scoreboard = p.getScoreboard();
                                plugin.getScoreboardAPI().updateIncreaseEntry(scoreboard, plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.scoreboard.money"), -item.getPrice());
                                p.sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "shop.success"));
                                return;
                            } else {
                                p.sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "shop.emptyinventoryfirst"));
                                return;
                            }
                        } else {
                            p.sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "shop.insufficientmoney"));
                            return;
                        }
                    }
                }

            }
        }
    }
}
