package pl.grzegorz2047.thewalls.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

import static pl.grzegorz2047.thewalls.api.util.ColoringUtil.colorText;

public class BaseCommand implements CommandExecutor {

    protected final Map<String, Arg> commands = new HashMap<>();
    protected String baseCmd;

    public BaseCommand(String basecmd) {
        this.baseCmd = basecmd;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase(baseCmd)) {
            String subCommand = "";
            if (args.length != 0) {
                // Lower case to ensure that all command are correct key
                subCommand = args[0].toLowerCase();
            }
            if (commands.get(subCommand) != null) {
                this.commands.get(subCommand).execute(sender, args);
                return true;
            } else {
                sender.sendMessage(colorText("&cPodales bledne argumenty!"));
                return true;
            }
        }
        return true;
    }
}
