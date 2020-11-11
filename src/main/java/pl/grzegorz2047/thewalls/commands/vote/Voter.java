package pl.grzegorz2047.thewalls.commands.vote;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.grzegorz2047.thewalls.GameUser;

import java.util.ArrayList;
import java.util.List;

public class Voter {
    private List<String> voters = new ArrayList<>();

    public boolean addVote(Player p, GameUser user) {
        String playerName = p.getName();
        if (voters.contains(playerName)) {
            return false;
        }
        if (user.getAssignedTeam() == null) {
            return false;
        }
        voters.add(playerName);
        return true;
    }

    public boolean check() {
        return voters.size() >= getMinPlayersToStart();
    }

    private double getMinPlayersToStart() {
        return 5;
    }

    public int getRemaining() {
        return (int) (getMinPlayersToStart() - voters.size());
    }

    public void handlerPlayerQuit(Player p) {
        voters.remove(p.getName());
    }

    public void reset() {
        this.voters.clear();
    }
}
