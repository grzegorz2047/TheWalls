package pl.grzegorz2047.thewalls.commands.tep;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.grzegorz2047.thewalls.*;

/**
 * Created by grzeg on 20.05.2016.
 */
public class TepCommand implements CommandExecutor {
    private final TheWalls plugin;
    private final GameUsers gameUsers;

    public TepCommand(TheWalls plugin, GameUsers gameUsers) {
        this.plugin = plugin;
        this.gameUsers = gameUsers;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length >= 1) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("nope!");
                return false;
            }
            Player p = (Player) sender;

            String teammate = args[0];
            Player toPlayer = Bukkit.getPlayer(teammate);
            if (toPlayer != null) {
                GameUser toPlayerUser = gameUsers.getGameUser(teammate);
                if (toPlayerUser == null) {
                    GameUser gameUser = gameUsers.getGameUser(p.getName());
                    p.sendMessage(plugin.getMessageManager().getMessage(gameUser.getLanguage(), "thewalls.cantteptothisperson"));
                    return true;
                }
                GameData.GameTeam toPlayerTeam = toPlayerUser.getAssignedTeam();
                if (toPlayerTeam != null) {
                    GameUser gameUser = gameUsers.getGameUser(p.getName());
                    if (gameUser.getRank().equals("Gracz")) {
                        p.sendMessage(plugin.getMessageManager().getMessage(gameUser.getLanguage(), "thewalls.notavailableforplayers"));
                        return true;
                    }
                    if (!plugin.getGameData().getCounter().getStatus().equals(Counter.CounterStatus.COUNTINGTODROPWALLS)) {
                        p.sendMessage(plugin.getMessageManager().getMessage(gameUser.getLanguage(), "thewalls.canttepnow"));
                        return true;
                    }
                    if (gameUser.getAssignedTeam() != null) {
                        if (toPlayerTeam.equals(gameUser.getAssignedTeam())) {
                            p.teleport(toPlayer);
                            p.sendMessage(plugin.getMessageManager().getMessage(gameUser.getLanguage(), "thewalls.teleportsuccess").replace("{NICK}", toPlayer.getName()));
                            toPlayer.sendMessage(plugin.getMessageManager().getMessage(gameUser.getLanguage(), "thewalls.playerteleported").replace("{NICK}", p.getName()));

                        } else {
                            p.sendMessage(plugin.getMessageManager().getMessage(gameUser.getLanguage(), "thewalls.notinyourteam").replace("{NICK}", toPlayer.getName()));
                        }
                    }
                }

            }


        }
        return true;
    }
}
