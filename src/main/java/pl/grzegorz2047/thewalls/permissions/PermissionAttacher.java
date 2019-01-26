package pl.grzegorz2047.thewalls.permissions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import pl.grzegorz2047.databaseapi.SQLUser;

/**
 * Created by grzeg on 16.05.2016.
 */
public class PermissionAttacher {
    private static Plugin plugin = Bukkit.getPluginManager().getPlugin("TheWalls");

    public static void attachSpectatorPermissions(Player p, String worldName) {
        p.addAttachment(plugin, "worldguard.region.bypass." + worldName, true);
    }

    public static void attachAdminsPermissions(Player p, String worldName) {
        //p.addAttachment(plugin, "worldguard.region.bypass."+worldName, true);
        p.addAttachment(plugin, "bukkit.command.gamemode", true);
        p.addAttachment(plugin, "bukkit.command.time.set", true);
        p.addAttachment(plugin, "bukkit.command.teleport", true);
        p.addAttachment(plugin, "bukkit.command.toggledownfall", true);
        p.addAttachment(plugin, "bukkit.command.weather", true);
        p.addAttachment(plugin, "vanish.vanish", true);
        p.addAttachment(plugin, "vanish.adminalerts", true);
        p.addAttachment(plugin, "cg.admin", true);
        p.addAttachment(plugin, "nocheatplus.admin", true);
    }

    public static void attachModsPermissions(Player p, String worldName) {
        //p.addAttachment(plugin, "worldguard.region.bypass."+worldName, true);
        p.addAttachment(plugin, "bukkit.command.time.set", true);
        p.addAttachment(plugin, "bukkit.command.teleport", true);
        p.addAttachment(plugin, "bukkit.command.toggledownfall", true);
        p.addAttachment(plugin, "bukkit.command.weather", true);
        p.addAttachment(plugin, "vanish.vanish", true);
        p.addAttachment(plugin, "cg.admin", true);
        p.addAttachment(plugin, "nocheatplus.admin", true);
    }

    public static void attachPlayersPermissions(Player p, String worldName) {
        //p.addAttachment(plugin, "worldguard.region.bypass."+worldName, true);
        p.addAttachment(plugin, "bukkit.command.me", false);

    }

    public static void dettachPermissions(Plugin plugin, Player p) {
        for (PermissionAttachmentInfo permissionAttachmentInfo : p.getEffectivePermissions()) {
            p.removeAttachment(permissionAttachmentInfo.getAttachment());
        }
    }
}
