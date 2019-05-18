package pl.grzegorz2047.thewalls.commands.team.args;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.databaseapi.messages.MessageAPI;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameUser;
import pl.grzegorz2047.thewalls.TheWalls;
import pl.grzegorz2047.thewalls.api.command.Arg;
import pl.grzegorz2047.thewalls.api.util.ColoringUtil;


/**
 * Created by grzegorz2047 on 30.12.2015.
 */
public class TeamArg implements Arg {

    TheWalls plugin;
    private GameData gameData;
    private MessageAPI messageManager;

    public TeamArg(Plugin plugin) {
        this.plugin = (TheWalls) plugin;
        gameData = this.plugin.getGameData();
        messageManager = this.plugin.getMessageManager();

    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        String username = p.getName();
        GameUser user = gameData.getGameUser(username);
        String userLanguage = user.getLanguage();
        if (gameData.isStatus(GameData.GameStatus.INGAME)) {
            String message = messageManager.getMessage(userLanguage, "thewalls.command.team.ingameerror");
            p.sendMessage(message);
            return;
        }
        boolean isNoArgs = args.length == 0;
        if (isNoArgs) {
            String message = messageManager.getMessage(userLanguage, "thewalls.command.team.noargs");
            p.sendMessage(message);
            return;
        } else {
            try {
                int teamNumber = Integer.parseInt(args[0]);
                GameData.GameTeam team = GameData.GameTeam.valueOf("TEAM" + teamNumber);
                GameData.GameTeam assignedTeam = user.getAssignedTeam();
                boolean hasTeam = assignedTeam != null;
                if (hasTeam) {
                    if (gameData.getTeam(team).contains(username)) {
                        p.sendMessage(messageManager.getMessage(userLanguage, "thewalls.command.team.alreadyinteam"));
                        return;
                    }
                }
                int numberOfPlayers = Bukkit.getOnlinePlayers().size();
                int maxPlayersOnServer = Bukkit.getMaxPlayers();
                int maxTeamSize = gameData.getMaxTeamSize();
                String teamName = team.name();
                int teamSize = gameData.getTeamSize(team);
                String teamSizeText = String.valueOf(teamSize);
                String maxTeamSizeText = String.valueOf(maxTeamSize);
                if (numberOfPlayers > maxPlayersOnServer) {
                    if (hasTeam) {
                        gameData.removeFromTeam(username, assignedTeam);
                    }
                    user.setAssignedTeam(team);
                    gameData.addPlayerToTeam(username, team);
                    p.sendMessage(messageManager.getMessage(userLanguage, "thewalls.command.team.jointeamsuccess")
                            .replace("{TEAM}", teamName).replace("{CURRENT}", teamSizeText)
                            .replace("{MAX}", maxTeamSizeText));
                } else {
                    boolean isFull = teamSize >= maxTeamSize;
                    if (isFull) {
                        p.sendMessage(messageManager.getMessage(userLanguage, "thewalls.command.team.fullteam"));
                        return;
                    } else {
                        boolean isLessThanTwoTeamSize = numberOfPlayers < (maxTeamSize * 2);
                        if (isLessThanTwoTeamSize) {
                            boolean isNotTeam1OrTeam2 = !(team.equals(GameData.GameTeam.TEAM1) || team.equals(GameData.GameTeam.TEAM2));
                            if (isNotTeam1OrTeam2) {
                                p.sendMessage(messageManager.getMessage(userLanguage, "thewalls.command.team.only2teams"));
                                return;
                            }
                        }
                        if (hasTeam) {
                            gameData.removeFromTeam(username, assignedTeam);
                        }
                        user.setAssignedTeam(team);
                        gameData.addPlayerToTeam(username, team);
                        p.sendMessage(messageManager.getMessage(userLanguage, "thewalls.command.team.jointeamsuccess")
                                .replace("{TEAM}", teamName).replace("{CURRENT}", teamSizeText)
                                .replace("{MAX}", maxTeamSizeText));

                    }
                    ColoringUtil.colorPlayerTab(p, team.getColor());
                    /*for (Map.Entry<String, GameUser> guser : plugin.getGameUsers().entrySet()) {
                        plugin.getScoreboardAPI().
                                colorTabListPlayer(p.getScoreboard(),
                                        guser.getKey(),
                                        GameData.getTeamColor(guser.getValue().getAssignedTeam()));
                    }*/
                }
            } catch (NumberFormatException ex) {
                String errormsg = messageManager.getMessage(userLanguage, "thewalls.command.team.error");
                String message = messageManager.getMessage(userLanguage, "thewalls.command.team.noargs");
                p.sendMessage(errormsg);
                p.sendMessage(message);
            } catch (IllegalArgumentException ex) {
                String errormsg = messageManager.getMessage(userLanguage, "thewalls.command.team.error");
                String message = messageManager.getMessage(userLanguage, "thewalls.command.team.noargs");
                p.sendMessage(errormsg);
                p.sendMessage(message);
                System.out.print("cos numer nie ogarnia?");
            }

        }
    }

    private boolean getStatus(GameData.GameStatus gameStatus) {
        return gameStatus.equals(GameData.GameStatus.INGAME);
    }

}
