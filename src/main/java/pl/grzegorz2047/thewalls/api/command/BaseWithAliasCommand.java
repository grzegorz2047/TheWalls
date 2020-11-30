package pl.grzegorz2047.thewalls.api.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

import static pl.grzegorz2047.thewalls.api.util.ColoringUtil.colorText;

/**
 * Created by Grzegorz2047. 18.09.2015.
 */
public abstract class BaseWithAliasCommand extends BaseCommand {

    protected final String[] aliases;
    protected final Map<String[], Arg> commands = new HashMap<>();

    public BaseWithAliasCommand(String baseCmd, String[] aliases) {
        super(baseCmd);
        this.aliases = aliases;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Arg argument = null;
        if (cmd.getName().equalsIgnoreCase(baseCmd)) {
            String subCommand = "";
            if (args.length != 0) {
                // Lower case to ensure that all command are correct key
                subCommand = args[0].toLowerCase();
            }
            for (String[] key : commands.keySet()) {
                for (String alias : key) {
                    if (alias.equals(subCommand)) {
                        argument = commands.get(key);
                        break;
                    }
                }
            }

            if (argument != null) {
                argument.execute(sender, args);
                return true;
            } else {
                sender.sendMessage(colorText("&cYou used incorrect command parameters!"));
                return true;
            }
        } else {
            Bukkit.getLogger().warning("Plugin has wrong code near getCommand("+baseCmd+").set ...");
        }

        return true;
    }
}