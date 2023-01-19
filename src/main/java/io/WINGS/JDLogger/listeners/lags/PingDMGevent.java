package io.WINGS.JDLogger.listeners.lags;

import io.WINGS.JDLogger.storage.SS;
import io.WINGS.JDLogger.storage.debug;
import io.WINGS.JDLogger.storage.defaults;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

public class PingDMGevent implements Listener {

    Plugin plugin = Bukkit.getPluginManager().getPlugin("JDLogger");
    Logger log = Bukkit.getLogger();
    FileConfiguration config = plugin.getConfig();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void PingDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            if (debug.get()) {
                log.info(SS.PlayerDamageDetected);
            }

            int ping = p.getPing();

            if (p.hasPermission(SS.BigPingPerm)) {
                if (ping >= config.getDouble("LagMeter.PING.ActivatePING", defaults.LagMeter_PING_ActivatePING)) {
                    if (debug.get()) {
                        log.info(String.format(SS.HighPingDetected, p.getName(), ping));
                    }
                    e.setCancelled(true);
                    if (debug.get()) {
                        log.info(SS.CancelingEvent + e.getEventName());
                    }
                    p.sendMessage(SS.LagmetrPrefix + String.format(SS.HighPing, ping));
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void LagDamage(EntityDamageByEntityEvent e) {
        if (!config.getBoolean("LagMeter.PING.HighPingPreventEntityDamage", defaults.LagMeter_PING_HighPingPreventEntityDamage)) return;
        Entity damager = e.getDamager();
        if (damager instanceof Player) {
            int ping = ((Player) damager).getPing();
            if (ping >= config.getDouble("LagMeter.PING.ActivatePING", defaults.LagMeter_PING_ActivatePING)) {
                if (debug.get()) {
                    log.info(String.format(SS.HighPingEntityDamageDetected, damager.getName(), ping));
                    log.info(SS.CancelingEvent + e.getEventName());
                }
                e.setCancelled(true);
                damager.sendMessage(SS.LagmetrPrefix + String.format(SS.HighPingMob, ping));
            }
        }
    }
}
