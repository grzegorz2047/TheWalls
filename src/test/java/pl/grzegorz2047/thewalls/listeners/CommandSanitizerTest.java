package pl.grzegorz2047.thewalls.listeners;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandSanitizerTest {

    @Test
    void isDisallowedCommand() {
        assertTrue(CommandSanitizer.isDisallowedCommand("/me"));
        assertTrue(CommandSanitizer.isDisallowedCommand("/minecraft:me"));
    }
}