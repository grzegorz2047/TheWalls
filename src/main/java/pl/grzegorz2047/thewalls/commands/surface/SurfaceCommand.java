package pl.grzegorz2047.thewalls.commands.surface;

import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.thewalls.TheWalls;
import pl.grzegorz2047.thewalls.api.command.BaseWithAliasCommand;
import pl.grzegorz2047.thewalls.commands.surface.args.SurfaceArg;
import pl.grzegorz2047.thewalls.commands.team.args.TeamArg;


/**
 * Created by grzegorz2047 on 30.12.2015.
 */
public class SurfaceCommand extends BaseWithAliasCommand {

    private TheWalls plugin;

    public SurfaceCommand(String baseCmd, String[] aliases, Plugin plugin) {
        super(baseCmd, aliases, plugin);
        this.commands.put(new String[]{""}, new SurfaceArg(plugin));
    }
}
