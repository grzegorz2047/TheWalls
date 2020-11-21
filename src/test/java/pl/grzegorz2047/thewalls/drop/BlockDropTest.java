package pl.grzegorz2047.thewalls.drop;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.block.BlockMock;
import be.seeseemelk.mockbukkit.entity.ItemEntityMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import com.google.common.collect.ImmutableList;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class BlockDropTest {

    private static ServerMock serverMock;

    @BeforeAll
    static void setup() {
        serverMock = MockBukkit.mock();
    }

    @Test
    void blockDropsOnlyWhenCorrectToolIsUsed() {

        // Create a new drop of a single diamond with 100% chance to appear
        String message = "SUCCESS";
        Drop drop = new ItemDrop(Material.DIAMOND, ImmutableList.of("APPLE"), 1, 100, message);
        BlockDrop blockDrop = new BlockDrop(null, ImmutableList.of(drop));

        // Set up a mock world
        PlayerMock player = serverMock.addPlayer();
        assertNotNull(player.getInventory());
        assertFalse(drop.isProperTool(player.getInventory().getItemInMainHand()));
        assertFalse(drop.isProperTool(player.getInventory().getItemInOffHand()));
        WorldMock world = serverMock.addSimpleWorld("test");
        BlockMock block = world.getBlockAt(0, 2, 0);
        assertFalse(block.getType().isAir());

        // Player does not have a correct tool equipped yet
        BlockBreakEvent event;
        event = new BlockBreakEvent(block, player);
        blockDrop.dropItems(event);
        assertNull(player.nextMessage());

        // Player has the tool equipped in main hand
        player.getInventory().setItemInMainHand(new ItemStack(Material.APPLE));
        event = new BlockBreakEvent(block, player);
        blockDrop.dropItems(event);
        assertEquals(message, player.nextMessage());

        // Player has the tool equipped in off hand
        player.getInventory().setItemInMainHand(new ItemStack(Material.CORNFLOWER));
        player.getInventory().setItemInOffHand(new ItemStack(Material.APPLE));
        event = new BlockBreakEvent(block, player);
        blockDrop.dropItems(event);
        assertEquals(message, player.nextMessage());

        // Player no longer has the correct tool equipped
        player.getInventory().setItemInOffHand(new ItemStack(Material.BLUE_DYE));
        event = new BlockBreakEvent(block, player);
        blockDrop.dropItems(event);
        assertNull(player.nextMessage());

        // Two diamonds should have spawned by now
        assertEquals(2, world.getEntities().stream()
            .filter(entity -> entity instanceof ItemEntityMock)
            .filter(mock -> ((ItemEntityMock)mock).getItemStack().getType().equals(Material.DIAMOND))
            .count());
    }

    @AfterAll
    static void clean() {
        MockBukkit.unmock();
        serverMock = null;
    }
}
