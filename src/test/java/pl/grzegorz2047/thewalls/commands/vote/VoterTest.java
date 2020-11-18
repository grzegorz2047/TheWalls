package pl.grzegorz2047.thewalls.commands.vote;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameUser;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VoterTest {

    private Voter voter;

    @BeforeEach
    public void initialize() {
        this.voter = new Voter();
    }


    @Test
    void whenPlayerDontHaveTeamAndVotingThenReturnFalse() {
        GameUser mock = getMockedGameUser("grzegorz", null);
        boolean grzegorzNoTeamVoted = this.voter.vote(mock);
        assertFalse(grzegorzNoTeamVoted);

    }

    @Test
    void whenPlayerIsInATeamAndVotingThenReturnTrue() {
        GameUser mock = getMockedGameUser("eniu", GameData.GameTeam.TEAM1);
        boolean eniuWithTeam1Voted = this.voter.vote(mock);
        assertTrue(eniuWithTeam1Voted);
    }

    @Test
    void whenPlayerIsInATeamAndVotingTwiceThenReturnFalse() {
        GameUser mock = getMockedGameUser("eniu", GameData.GameTeam.TEAM1);
        boolean eniuWithTeam1Voted = this.voter.vote(mock);
        assertTrue(eniuWithTeam1Voted);
        boolean eniuWithTeam1VotedAgain = this.voter.vote(mock);
        assertFalse(eniuWithTeam1VotedAgain);
    }

    @Test
    void isEnoughNumberOfVotes() {
        addNVotes(4);

        assertFalse(this.voter.isEnoughNumberOfVotes());
        addNVotes(1);
        assertTrue(this.voter.isEnoughNumberOfVotes());
    }

    private List<String> addNVotes(int numberOfPlayersWhoVoted) {
        assertTrue(numberOfPlayersWhoVoted >= 0);
        ArrayList<String> voters = new ArrayList<>();
        for (int i = 0; i < numberOfPlayersWhoVoted; i++) {
            String randomPlayerName = RandomStringUtils.random(16);

            GameUser mock = getMockedGameUser(randomPlayerName, GameData.GameTeam.TEAM1);
            voters.add(randomPlayerName);
            this.voter.vote(mock);
        }
        return voters;
    }

    private GameUser getMockedGameUser(String playerName, GameData.GameTeam assignedTeam) {
        GameUser mock = Mockito.mock(GameUser.class);
        Mockito.when(mock.getAssignedTeam()).thenReturn(assignedTeam);
        Mockito.when(mock.getUsername()).thenReturn(playerName);
        return mock;
    }

    @Test
    void getRemaining() {
        addNVotes(4);
        assertEquals(1, this.voter.getRemaining());
        addNVotes(1);
        assertEquals(0, this.voter.getRemaining());
        addNVotes(1);
        assertEquals(-1, this.voter.getRemaining());
    }

    @Test
    void handlePlayerQuit() {
        String playerName = "ABC1";
        boolean addedVote = this.voter.vote(getMockedGameUser(playerName, GameData.GameTeam.TEAM1));
        assertTrue(addedVote);
        assertEquals(4, this.voter.getRemaining());
        this.voter.handlePlayerQuit(playerName);
        assertEquals(5, this.voter.getRemaining());
    }

    @Test
    void reset() {
        addNVotes(4);
        assertEquals(1, this.voter.getRemaining());
        this.voter.reset();
        assertEquals(5, this.voter.getRemaining());
    }
}