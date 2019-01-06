package pl.grzegorz2047.thewalls.commands.walls;

import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.thewalls.TheWalls;
import pl.grzegorz2047.thewalls.api.command.BaseWithAliasCommand;
import pl.grzegorz2047.thewalls.commands.walls.args.StartArg;


/**
 * Created by grzegorz2047 on 30.12.2015.
 */
public class WallsCommand extends BaseWithAliasCommand {

    private TheWalls plugin;

    public WallsCommand(String baseCmd, String[] aliases, Plugin plugin) {
        super(baseCmd, aliases, plugin);
        this.commands.put(new String[]{"start"}, new StartArg(plugin));
    }
}
