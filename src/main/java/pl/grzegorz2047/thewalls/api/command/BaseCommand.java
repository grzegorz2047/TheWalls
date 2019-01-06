
package pl.grzegorz2047.thewalls.api.command;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class BaseCommand implements CommandExecutor {

    private BaseCommand(){}

    protected String baseCmd;
    public BaseCommand(String basecmd){
        this.baseCmd = basecmd;
    }

    protected Map<String, Arg> commands = new HashMap<String, Arg>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase(baseCmd)) {
            String subCommand = "";
            if (args.length != 0) {
                subCommand = args[0].toLowerCase();//lower case to ensure that all command are correct key
            }
            if (commands.get(subCommand) != null) {
                this.commands.get(subCommand).execute(sender, args);
                return true;
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cPodales bledne argumenty!"));
                return true;
            }
        }
        return true;
    }
}
