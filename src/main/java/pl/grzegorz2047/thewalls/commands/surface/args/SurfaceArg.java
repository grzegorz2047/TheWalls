package pl.grzegorz2047.thewalls.commands.surface.args;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.grzegorz2047.databaseapi.messages.MessageAPI;
import pl.grzegorz2047.thewalls.*;
import pl.grzegorz2047.thewalls.api.command.Arg;

/**
 * Created by grzeg on 17.05.2016.
 */
public class SurfaceArg implements Arg {

    private final GameData gameData;
    private final MessageAPI messageManager;
    private final GameUsers gameUsers;

    public SurfaceArg(GameData gameData, MessageAPI messageManager, GameUsers gameUsers) {
        this.gameData = gameData;
        this.messageManager = messageManager;
        this.gameUsers = gameUsers;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        GameUser user = gameUsers.getGameUser(p.getName());
        String language = user.getLanguage();
        if (!gameData.isStatus(GameData.GameStatus.INGAME)) {
            p.sendMessage(messageManager.getMessage(language, "thewalls.msg.surfacenotavailableyet"));
            return;
        }
        if (!gameData.getCounter().getStatus().equals(Counter.CounterStatus.COUNTINGTODROPWALLS)) {
            p.sendMessage(messageManager.getMessage(language, "thewalls.msg.surfacenotavailableyet"));
            return;
        }
        if (user.getRank().equals("Gracz")) {
            p.sendMessage(messageManager.getMessage(language, "thewalls.notavailableforplayers"));
            return;
        }
        if (!user.hasUsedSurface()) {
            Location loc = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getWorld().getHighestBlockYAt(p.getLocation()) + 5, p.getLocation().getZ());
            p.teleport(loc);
            user.setUsedSurface(true);
        } else {
            p.sendMessage(messageManager.getMessage(language, "thewalls.alreadyusedsurface"));
        }

    }
}
