package pl.grzegorz2047.thewalls.listeners;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.projectiles.ProjectileSource;
import pl.grzegorz2047.thewalls.GameData;
import pl.grzegorz2047.thewalls.GameUser;
import pl.grzegorz2047.thewalls.GameUsers;

/**
 * Created by grzeg on 17.05.2016.
 */
public class PlayersDamaging implements Listener {


    private final GameData gameData;
    private final GameUsers gameUsers;

    public PlayersDamaging(GameData gameData, GameUsers gameUsers) {
        this.gameData = gameData;
        this.gameUsers = gameUsers;
    }

    @EventHandler
    public void przyDamagu(EntityDamageEvent e) {
        if (!gameData.isStatus(GameData.GameStatus.INGAME)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    void onEntityDamageEntity(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getDamager() instanceof Player) {
            if (event.getEntity() instanceof Player) {
                Player attacked = (Player) event.getEntity();
                Player attacker = (Player) event.getDamager();
                GameUser attackedUser = gameUsers.getGameUser(attacked.getName());
                GameUser attackerUser = gameUsers.getGameUser(attacker.getName());
                if (checkIfTheSameTeam(attackedUser, attackerUser)) {
                    event.setCancelled(true);
                }
            }
        } else if (event.getDamager() instanceof Arrow) {
            if (event.getEntity() instanceof Player) {
                Player attacked = (Player) event.getEntity();
                ProjectileSource attackerEntity = ((Arrow) event.getDamager()).getShooter();

                if (attackerEntity instanceof Player) {
                    Player attacker = (Player) attackerEntity;

                    GameUser attackedUser = gameUsers.getGameUser(attacked.getName());
                    GameUser attackerUser = gameUsers.getGameUser(attacker.getName());
                    if (checkIfTheSameTeam(attackedUser, attackerUser)) {
                        event.setCancelled(true);
                    }
                    attacker.playSound(attacker.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                }
            }
        } else if (event.getDamager() instanceof Snowball) {
            if (event.getEntity() instanceof Player) {
                Player attacked = (Player) event.getEntity();
                ProjectileSource attackerEntity = ((Snowball) event.getDamager()).getShooter();

                if (attackerEntity instanceof Player) {
                    Player attacker = (Player) attackerEntity;
                    GameUser attackedUser = gameUsers.getGameUser(attacked.getName());
                    GameUser attackerUser = gameUsers.getGameUser(attacker.getName());
                    if (checkIfTheSameTeam(attackedUser, attackerUser)) {
                        event.setCancelled(true);
                    }
                }
            }
        } else if (event.getDamager() instanceof Egg) {
            if (event.getEntity() instanceof Player) {
                Player attacked = (Player) event.getEntity();
                ProjectileSource attackerEntity = ((Egg) event.getDamager()).getShooter();
                if (attackerEntity instanceof Player) {
                    Player attacker = (Player) attackerEntity;
                    GameUser attackedUser = gameUsers.getGameUser(attacked.getName());
                    GameUser attackerUser = gameUsers.getGameUser(attacker.getName());
                    if (checkIfTheSameTeam(attackedUser, attackerUser)) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    private boolean checkIfTheSameTeam(GameUser attackedUser, GameUser attackerUser) {
        return attackedUser.
                getAssignedTeam()
                .equals(
                        attackerUser.
                                getAssignedTeam());
    }
}
