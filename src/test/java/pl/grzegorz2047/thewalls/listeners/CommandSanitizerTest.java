package pl.grzegorz2047.thewalls.listeners;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandSanitizerTest {

    @Test
    void whenCommandisDisallowedThenReturnTrue() {
        assertTrue(CommandSanitizer.isDisallowedCommand("/me"));
        assertTrue(CommandSanitizer.isDisallowedCommand("/minecraft:me"));
    }
    @Test
    void whenCommandissallowedThenReturnFalse() {
        assertFalse(CommandSanitizer.isDisallowedCommand("/bukkit"));
        assertFalse(CommandSanitizer.isDisallowedCommand("no elo"));
    }
}