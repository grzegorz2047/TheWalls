package pl.grzegorz2047.thewalls.commands.walls.args;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.thewalls.Counter;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameUser;
import pl.grzegorz2047.thewalls.TheWalls;
import pl.grzegorz2047.thewalls.api.command.Arg;

/**
 * Created by grzeg on 17.05.2016.
 */
public class StartArg implements Arg {
    private final TheWalls plugin;

    public StartArg(Plugin plugin) {
        this.plugin = (TheWalls) plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        GameUser user = plugin.getGameData().getGameUsers().get(p.getName());
        if (!plugin.getGameData().getStatus().equals(GameData.GameStatus.WAITING)) {
            p.sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.msg.alreadystarted"));
            return;
        }
        if(user.getRank().contains("Admin")){
            plugin.getGameData().getCounter().start(Counter.CounterStatus.COUNTINGTOSTART);
        }


    }
}
