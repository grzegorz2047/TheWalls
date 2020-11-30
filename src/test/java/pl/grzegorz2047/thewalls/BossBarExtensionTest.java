package pl.grzegorz2047.thewalls;


import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class BossBarExtensionTest {

    private BossBarExtension bossBarExtension;
    private BossBar bossBarMock;

    @BeforeEach
    public void atBeginning() {
        bossBarMock = Mockito.mock(BossBar.class);
        String[] titles = {"Zapraszamy na ts.mc-walls.pl", "Wesprzyj nas na mc-walls.pl"};
        BossBarData bossBarData = new BossBarData(titles, new BarColor[]{BarColor.BLUE, BarColor.GREEN}, 60);
        bossBarExtension = new BossBarExtension(bossBarMock, bossBarData);
    }

    @Test
    void updateBossBar() {
        Player testPlayer = Mockito.mock(Player.class);
        Mockito.when(testPlayer.getName()).thenReturn("testPlayer");
//        bossBarExtension.addToBossBar(testPlayer);
        bossBarExtension.updateBossBar();
        ArgumentCaptor<String> captorTitle = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<BarColor> captorColor = ArgumentCaptor.forClass(BarColor.class);
        verify(bossBarMock, times(1)).setTitle(captorTitle.capture());
        verify(bossBarMock, times(1)).setColor(captorColor.capture());
        assertNotNull(captorTitle.getValue());
        assertNotNull(captorColor.getValue());
//        List<Player> players = mock.getPlayers();
//        assertEquals(1,players.size());

    }
}