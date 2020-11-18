package pl.grzegorz2047.thewalls.commands.walls;

import pl.grzegorz2047.databaseapi.messages.MessageAPI;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameUsers;
import pl.grzegorz2047.thewalls.api.command.BaseWithAliasCommand;
import pl.grzegorz2047.thewalls.commands.walls.args.StartArg;


/**
 * Created by grzegorz2047 on 30.12.2015.
 */
public class WallsCommand extends BaseWithAliasCommand {

    public WallsCommand(String baseCmd, String[] aliases, GameUsers gameUsers, GameData gameData, MessageAPI messageManager) {
        super(baseCmd, aliases);
        this.commands.put(new String[]{"start"}, new StartArg(gameData, messageManager, gameUsers));
    }
}
