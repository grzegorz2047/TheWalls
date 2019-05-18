package pl.grzegorz2047.thewalls.commands.walls.args;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.grzegorz2047.databaseapi.messages.MessageAPI;
import pl.grzegorz2047.thewalls.Counter;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameUser;
import pl.grzegorz2047.thewalls.TheWalls;
import pl.grzegorz2047.thewalls.api.command.Arg;

import java.util.HashMap;

/**
 * Created by grzeg on 17.05.2016.
 */
public class StartArg implements Arg {

    private final GameData gameData;
    private final HashMap<String, GameUser> gameUsers;
    private final MessageAPI messageManager;
    private final Counter counter;

    public StartArg(GameData gameData, MessageAPI messageManager) {
        this.messageManager = messageManager;
        this.gameData = gameData;
        gameUsers = gameData.getGameUsers();
        counter = gameData.getCounter();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        String username = p.getName();
        GameUser user = gameUsers.get(username);
        if (!gameData.getStatus().equals(GameData.GameStatus.WAITING)) {
            p.sendMessage(messageManager.getMessage(user.getLanguage(), "thewalls.msg.alreadystarted"));
            return;
        }
        if (user.getRank().contains("Admin")) {
            counter.start(Counter.CounterStatus.COUNTINGTOSTART);
        }


    }
}
