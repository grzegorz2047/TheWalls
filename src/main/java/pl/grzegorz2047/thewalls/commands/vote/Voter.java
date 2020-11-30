package pl.grzegorz2047.thewalls.commands.vote;

import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameUser;

import java.util.ArrayList;
import java.util.List;

public class Voter {
    private final List<String> voters = new ArrayList<>();

    public boolean vote(GameUser user) {
        GameData.GameTeam assignedTeam = user.getAssignedTeam();
        String playerName = user.getUsername();
        if (voters.contains(playerName)) {
            return false;
        }
        if (assignedTeam == null) {
            return false;
        }
        voters.add(playerName);
        return true;
    }

    public boolean isEnoughNumberOfVotes() {
        return voters.size() >= getMinPlayersToStart();
    }

    private double getMinPlayersToStart() {
        return 5;
    }

    public int getRemaining() {
        return (int) (getMinPlayersToStart() - voters.size());
    }

    public void handlePlayerQuit(String name) {
        voters.remove(name);
    }

    public void reset() {
        this.voters.clear();
    }
}
