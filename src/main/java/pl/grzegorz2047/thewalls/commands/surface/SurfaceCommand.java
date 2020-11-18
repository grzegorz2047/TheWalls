package pl.grzegorz2047.thewalls.commands.surface;

import pl.grzegorz2047.databaseapi.messages.MessageAPI;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameUsers;
import pl.grzegorz2047.thewalls.TheWalls;
import pl.grzegorz2047.thewalls.api.command.BaseWithAliasCommand;
import pl.grzegorz2047.thewalls.commands.surface.args.SurfaceArg;


/**
 * Created by grzegorz2047 on 30.12.2015.
 */
public class SurfaceCommand extends BaseWithAliasCommand {

    private final GameUsers gameUsers;

    public SurfaceCommand(String baseCmd, String[] aliases, GameData gameData, MessageAPI messageManager, GameUsers gameUsers) {
        super(baseCmd, aliases);
        this.gameUsers = gameUsers;
        this.commands.put(new String[]{""}, new SurfaceArg(gameData, messageManager, this.gameUsers));
    }
}
