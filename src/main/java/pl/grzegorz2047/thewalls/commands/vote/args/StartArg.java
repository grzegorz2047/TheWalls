package pl.grzegorz2047.thewalls.commands.vote.args;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.grzegorz2047.databaseapi.messages.MessageAPI;
import pl.grzegorz2047.thewalls.Counter;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameUser;
import pl.grzegorz2047.thewalls.GameUsers;
import pl.grzegorz2047.thewalls.api.command.Arg;
import pl.grzegorz2047.thewalls.commands.vote.Voter;

/**
 * Created by grzeg on 17.05.2016.
 */
public class StartArg implements Arg {

    private final GameData gameData;


    public StartArg(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        gameData.vote(p);
    }


}
