package pl.grzegorz2047.thewalls.commands.vote.args;

import org.bukkit.Bukkit;
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
    private final MessageAPI messageManager;
    private final Counter counter;
    private GameUsers gameUsers;
    private Voter vote;

    public StartArg(GameData gameData, MessageAPI messageManager, GameUsers gameUsers, Voter vote) {
        this.messageManager = messageManager;
        this.gameData = gameData;
        counter = gameData.getCounter();
        this.gameUsers = gameUsers;
        this.vote = vote;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        String username = p.getName();
        GameUser user = gameUsers.getGameUser(username);
        String userLanguage = user.getLanguage();
        if (!gameData.isStatus(GameData.GameStatus.WAITING)) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', messageManager.getMessage(userLanguage, "thewalls.msg.alreadystarted")));
            return;
        }
        boolean successVote = vote.addVote(p, user);
        if (!successVote) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',messageManager.getMessage(userLanguage, "thewalls.msg.noTeamOrAlreadyVoted")));
            return;
        }
        boolean check = vote.check();
        if (check) {
            counter.start(Counter.CounterStatus.VOTED_COUNTING_TO_START);
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',messageManager.getMessage(userLanguage, "thewalls.msg.noMoneyWhenVoting")));
            Bukkit.broadcastMessage(messageManager.getMessage(userLanguage, "thewalls.countingstarted"));
        } else {
            int remaining = vote.getRemaining();
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',messageManager.getMessage(userLanguage, "thewalls.msg.noMoneyWhenVoting")));
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',messageManager.getMessage(userLanguage, "thewalls.msg.remainingToStart").replace("%REMAINING%", String.valueOf(remaining))));
        }
        return;
    }
}
