package pl.grzegorz2047.thewalls.commands.team.args;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameUser;
import pl.grzegorz2047.thewalls.TheWalls;
import pl.grzegorz2047.thewalls.api.command.Arg;
import pl.grzegorz2047.thewalls.api.util.ColoringUtil;

import java.util.List;


/**
 * Created by grzegorz2047 on 30.12.2015.
 */
public class TeamArg implements Arg {

    TheWalls plugin;

    public TeamArg(Plugin plugin) {
        this.plugin = (TheWalls) plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        GameUser user = plugin.getGameData().getGameUsers().get(p.getName());
        if (plugin.getGameData().getStatus().equals(GameData.GameStatus.INGAME)) {
            String message = plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.command.team.ingameerror");
            p.sendMessage(message);
            return;
        }
        if (args.length == 0) {
            String message = plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.command.team.noargs");
            p.sendMessage(message);
            return;
        } else {
            try {
                int number = Integer.parseInt(args[0]);
                GameData.GameTeam team = GameData.GameTeam.valueOf("TEAM" + number);
                if (user.getAssignedTeam() != null) {
                    if (plugin.getGameData().getTeams().get(team).contains(p.getName())) {
                        p.sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.command.team.alreadyinteam"));
                        return;
                    }
                }
                List<String> listteam = plugin.getGameData().getTeams().get(team);
                if (Bukkit.getOnlinePlayers().size() > Bukkit.getMaxPlayers()) {
                    if (user.getAssignedTeam() != null) {
                        plugin.getGameData().getTeams().get(user.getAssignedTeam()).remove(p.getName());
                    }
                    user.setAssignedTeam(team);
                    listteam.add(p.getName());
                    p.sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.command.team.jointeamsuccess")
                            .replace("{TEAM}", team.name()).replace("{CURRENT}", String.valueOf(listteam.size()))
                            .replace("{MAX}", String.valueOf(plugin.getGameData().getMaxTeamSize())));
                } else {
                    if (listteam.size() >= plugin.getGameData().getMaxTeamSize()) {
                        p.sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.command.team.fullteam"));
                        return;
                    } else {
                        if(Bukkit.getOnlinePlayers().size()<(plugin.getGameData().getMaxTeamSize()*2)){
                            if(!(team.equals(GameData.GameTeam.TEAM1) || team.equals(GameData.GameTeam.TEAM2))){
                                p.sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.command.team.only2teams"));
                                return;
                            }
                        }
                        if (user.getAssignedTeam() != null) {
                            plugin.getGameData().getTeams().get(user.getAssignedTeam()).remove(p.getName());
                        }
                        user.setAssignedTeam(team);
                        listteam.add(p.getName());
                        p.sendMessage(plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.command.team.jointeamsuccess")
                                .replace("{TEAM}", team.name()).replace("{CURRENT}", String.valueOf(listteam.size()))
                                .replace("{MAX}", String.valueOf(plugin.getGameData().getMaxTeamSize())));

                    }
                    ColoringUtil.colorPlayerTab(p, GameData.TeamtoColor(team));
                    /*for (Map.Entry<String, GameUser> guser : plugin.getGameUsers().entrySet()) {
                        plugin.getScoreboardAPI().
                                colorTabListPlayer(p.getScoreboard(),
                                        guser.getKey(),
                                        GameData.TeamtoColor(guser.getValue().getAssignedTeam()));
                    }*/
                }
            } catch (NumberFormatException ex) {
                String errormsg = plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.command.team.error");
                String message = plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.command.team.noargs");
                p.sendMessage(errormsg);
                p.sendMessage(message);
            } catch (IllegalArgumentException ex) {
                String errormsg = plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.command.team.error");
                String message = plugin.getMessageManager().getMessage(user.getLanguage(), "thewalls.command.team.noargs");
                p.sendMessage(errormsg);
                p.sendMessage(message);
                System.out.print("cos numer nie ogarnia?");
            }

        }
    }
}
