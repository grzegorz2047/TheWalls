package pl.grzegorz2047.thewalls.listeners;

import java.util.Arrays;
import java.util.List;

public class CommandSanitizer {

    private static List<String> disallowedCommands = Arrays.asList("/minecraft:me","/me", "?");

    public static boolean isDisallowedCommand(String message) {
        String[] s = message.split(" ");
        return disallowedCommands.contains(s[0].toLowerCase());
    }

}
