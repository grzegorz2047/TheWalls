package pl.grzegorz2047.thewalls.commands.surface.args;

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
public class SurfaceArg implements Arg {
    private final TheWalls plugin;

    public SurfaceArg(Plugin plugin) {
        this.plugin = (TheWalls) plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        GameUser user = plugin.getGameData().getGameUsers().get(p.getName());
        if (!plugin.getGameData().getStatus().equals(GameData.GameStatus.INGAME)) {
            p.sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.msg.surfacenotavailableyet"));
            return;
        }
        if (!plugin.getGameData().getCounter().getStatus().equals(Counter.CounterStatus.COUNTINGTODROPWALLS)) {
            p.sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.msg.surfacenotavailableyet"));
            return;
        }
        if (user.getRank().equals("Gracz")) {
            p.sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.notavailableforplayers"));
            return;
        }
        if(!user.hasUsedSurface()){
            Location loc = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getWorld().getHighestBlockYAt(p.getLocation())+5, p.getLocation().getZ());
            p.teleport(loc);
            user.setUsedSurface(true);
        }else{
            p.sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.alreadyusedsurface"));
        }

    }
}
