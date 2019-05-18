package pl.grzegorz2047.thewalls.commands.walls;

import pl.grzegorz2047.thewalls.TheWalls;
import pl.grzegorz2047.thewalls.api.command.BaseWithAliasCommand;
import pl.grzegorz2047.thewalls.commands.walls.args.StartArg;


/**
 * Created by grzegorz2047 on 30.12.2015.
 */
public class WallsCommand extends BaseWithAliasCommand {

    public WallsCommand(String baseCmd, String[] aliases, TheWalls plugin) {
        super(baseCmd, aliases, plugin);
        this.commands.put(new String[]{"start"}, new StartArg(plugin.getGameData(), plugin.getMessageManager()));
    }
}
