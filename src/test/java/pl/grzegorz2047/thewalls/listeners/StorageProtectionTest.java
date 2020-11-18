package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.grzegorz2047.thewalls.GameUser;
import pl.grzegorz2047.thewalls.GameUserMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StorageProtectionTest {

    private StorageProtection storageProtection;

    @BeforeEach
    void setupTest() {
        storageProtection = new StorageProtection(null, null);
    }

    @Test
    void playerCanOnlyClaimThreeFurnaces() {
        String username = "Steve";
        GameUser player = new GameUserMock(username);

        // Player should be able to claim three furnaces
        Location firstLoc = new Location(null, 1, 1, 1);
        assertTrue(storageProtection.protectNewFurnace(firstLoc, username, player));
        Location secondLoc = new Location(null, 2, 2, 2);
        assertTrue(storageProtection.protectNewFurnace(secondLoc, username, player));
        Location thirdLoc = new Location(null, 3, 3, 3);
        assertTrue(storageProtection.protectNewFurnace(thirdLoc, username, player));
        assertEquals(3, player.getProtectedFurnaces());

        // Fourth claim should fail
        Location fourthLoc = new Location(null, 4, 4, 4);
        assertFalse(storageProtection.protectNewFurnace(fourthLoc, username, player));

        // We remove one of the protected blocks
        storageProtection.removeFurnaceProtection(player, secondLoc);
        assertEquals(2, player.getProtectedFurnaces());

        // The player can claim one more block again
        Location fifthLoc = new Location(null, 5, 5, 5);
        assertTrue(storageProtection.protectNewFurnace(fifthLoc, username, player));
        assertEquals(3, player.getProtectedFurnaces());
    }

    @Test
    void claimedFurnacesRecognizeTheirOwners() {
        String usernameOne = "Steve";
        GameUser playerOne = new GameUserMock(usernameOne);
        String usernameTwo = "Alex";
        GameUser playerTwo = new GameUserMock(usernameTwo);

        // Steve claims the first furnace, so Alex is not its owner
        Location firstLoc = new Location(null, 1, 1, 1);
        assertTrue(storageProtection.protectNewFurnace(firstLoc, usernameOne, playerOne));
        assertTrue(storageProtection.isFurnaceOwner(usernameOne, firstLoc));
        assertFalse(storageProtection.isFurnaceOwner(usernameTwo, firstLoc));

        // Alex claims the second furnace, so Steve is not its owner
        Location secondLoc = new Location(null, 2, 2, 2);
        assertTrue(storageProtection.protectNewFurnace(secondLoc, usernameTwo, playerTwo));
        assertFalse(storageProtection.isFurnaceOwner(usernameOne, secondLoc));
        assertTrue(storageProtection.isFurnaceOwner(usernameTwo, secondLoc));
    }
}
