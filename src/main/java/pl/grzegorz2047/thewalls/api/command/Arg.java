package pl.grzegorz2047.thewalls.api.command;

import org.bukkit.command.CommandSender;

/**
 * Created by Grzegorz2047. 28.08.2015.
 */
public interface Arg {
    void execute(CommandSender sender, String[] args);
}