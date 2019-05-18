package pl.grzegorz2047.thewalls.commands.surface;

import pl.grzegorz2047.databaseapi.messages.MessageAPI;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.TheWalls;
import pl.grzegorz2047.thewalls.api.command.BaseWithAliasCommand;
import pl.grzegorz2047.thewalls.commands.surface.args.SurfaceArg;


/**
 * Created by grzegorz2047 on 30.12.2015.
 */
public class SurfaceCommand extends BaseWithAliasCommand {

    public SurfaceCommand(String baseCmd, String[] aliases, TheWalls plugin, GameData gameData, MessageAPI messageManager) {
        super(baseCmd, aliases, plugin);
        this.commands.put(new String[]{""}, new SurfaceArg(gameData, messageManager));
    }
}
