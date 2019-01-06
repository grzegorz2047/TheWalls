package pl.grzegorz2047.thewalls.commands.tep;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.grzegorz2047.thewalls.Counter;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameUser;
import pl.grzegorz2047.thewalls.TheWalls;

/**
 * Created by grzeg on 20.05.2016.
 */
public class TepCommand implements CommandExecutor {
    private final TheWalls plugin;

    public TepCommand(TheWalls plugin) {
        this.plugin = plugin;
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
                GameUser toPlayerUser = plugin.getGameData().getGameUsers().get(teammate);
                if (toPlayerUser == null) {
                    GameUser gameUser = plugin.getGameData().getGameUsers().get(p.getName());
                    p.sendMessage(plugin.getMessageManager().getMessage(gameUser.getLanguage(), "thewalls.cantteptothisperson"));
                    return true;
                }
                GameData.GameTeam toPlayerTeam = toPlayerUser.getAssignedTeam();
                if (toPlayerTeam != null) {
                    GameUser gameUser = plugin.getGameData().getGameUsers().get(p.getName());
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
