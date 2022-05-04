package WINGS7N.DLogger.listeners.lags;

import java.util.logging.Logger;

import WINGS7N.DLogger.storage.BS;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

import WINGS7N.DLogger.storage.SS;
import net.md_5.bungee.api.ChatColor;

public class PingDMGevent implements Listener {

    Plugin plugin = Bukkit.getPluginManager().getPlugin("JDLogger");
    Logger log = Bukkit.getLogger();
    FileConfiguration config = plugin.getConfig();
    boolean debug = config.getBoolean("DEV.DEBUG");

    @EventHandler(priority = EventPriority.HIGHEST)
    public void PingDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {

            if (BS.dev) {
                debug = true;
            }

            Player p = (Player) e.getEntity();

            if (debug) {
                log.info(SS.PlayerDamageDetected);
            }

            int ping = p.getPing();

            if (p.hasPermission(SS.BigPingPerm)) {
                if (ping >= config.getDouble("LagMeter.ActivatePING")) {
                    if (debug) {
                        log.info(String.format(SS.HighPingDetected, p.getName(), ping));
                    }
                    e.setCancelled(true);
                    if (debug) {
                        log.info(SS.CancelingEvent + e.getEventName());
                    }
                    p.sendMessage(ChatColor.BLUE + SS.LagometrPrefix + SS.NULL + ChatColor.DARK_RED + String.format(SS.BigPing, ping));
                }
            }
        }
    }
}
