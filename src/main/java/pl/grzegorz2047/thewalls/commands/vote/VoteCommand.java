package pl.grzegorz2047.thewalls.commands.vote;

import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.api.command.BaseWithAliasCommand;
import pl.grzegorz2047.thewalls.commands.vote.args.StartArg;


/**
 * Created by grzegorz2047 on 30.12.2015.
 */
public class VoteCommand extends BaseWithAliasCommand {

    public VoteCommand(String baseCmd, String[] aliases, GameData gameData) {
        super(baseCmd, aliases);
        this.commands.put(new String[]{""}, new StartArg(gameData));
        this.commands.put(new String[]{"start"}, new StartArg(gameData));
    }
}
