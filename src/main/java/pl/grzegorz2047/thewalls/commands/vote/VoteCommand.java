package pl.grzegorz2047.thewalls.commands.vote;

import pl.grzegorz2047.thewalls.GameUsers;
import pl.grzegorz2047.thewalls.TheWalls;
import pl.grzegorz2047.thewalls.api.command.BaseWithAliasCommand;
import pl.grzegorz2047.thewalls.commands.vote.args.StartArg;


/**
 * Created by grzegorz2047 on 30.12.2015.
 */
public class VoteCommand extends BaseWithAliasCommand {

    public VoteCommand(String baseCmd, String[] aliases, TheWalls plugin, GameUsers gameUsers, Voter vote) {
        super(baseCmd, aliases, plugin);
        this.commands.put(new String[]{""}, new StartArg(plugin.getGameData(), plugin.getMessageManager(), gameUsers, vote));
        this.commands.put(new String[]{"start"}, new StartArg(plugin.getGameData(), plugin.getMessageManager(), gameUsers, vote));
    }
}
