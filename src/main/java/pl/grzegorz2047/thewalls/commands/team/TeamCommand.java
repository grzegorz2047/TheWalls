package pl.grzegorz2047.thewalls.commands.team;

import pl.grzegorz2047.databaseapi.messages.MessageAPI;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameUsers;
import pl.grzegorz2047.thewalls.TheWalls;
import pl.grzegorz2047.thewalls.api.command.BaseWithAliasCommand;
import pl.grzegorz2047.thewalls.commands.team.args.TeamArg;


/**
 * Created by grzegorz2047 on 30.12.2015.
 */
public class TeamCommand extends BaseWithAliasCommand {



    public TeamCommand(String baseCmd, String[] aliases, GameUsers gameUsers, GameData gameData, MessageAPI messageManager) {
        super(baseCmd, aliases);
        this.commands.put(new String[]{"1", "2", "3", "4"}, new TeamArg(gameUsers, gameData, messageManager));
    }
}
