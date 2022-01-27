package WINGS.DLogger.listeners.lags;

import WINGS.DLogger.storage.SS;
import WINGS.providers.TPS.GetTPS;
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

public class LagDMGevent implements Listener {

    Plugin plugin = Bukkit.getPluginManager().getPlugin("JDLogger");
    Logger log = Bukkit.getLogger();
    FileConfiguration config = plugin.getConfig();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void LagDeath(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Entity p = e.getEntity();

            if (config.getBoolean("DEV.DEBUG")) {
                log.info(SS.PlayerDamageDetected);
            }

            double tps1m = GetTPS.run()[0];
            double tps5m = GetTPS.run()[1];
            double tps15m = GetTPS.run()[2];
            String tpsmsg = String.format("1m = %f, 5m = %f, 15m = %f", tps1m, tps5m, tps15m);

            if (p.hasPermission(SS.accessPerm)) {
                if (tps1m <= config.getDouble("LagMeter.ActivateTPS")) {
                    if (config.getBoolean("DEV.DEBUG")) {
                        log.info(SS.LowTPSdetected + tpsmsg);
                    }
                    e.setCancelled(true);
                    if (config.getBoolean("DEV.DEBUG")) {
                        log.info(SS.CancelingEvent + e.getEventName());
                    }
                    p.sendMessage(ChatColor.BLUE + SS.LagometrPrefix + SS.NULL + ChatColor.DARK_RED + String.format(SS.TPSDeath1, tps1m));
                }
            }
        }
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void LagDamage(EntityDamageByEntityEvent e) {
        Entity mob = e.getEntity();
        e.getDamager();
        e.getCause();
        if (e.getDamager() instanceof Player) {
            if (GetTPS.run()[0] <= config.getDouble("LagMeter.ActivateTPS")) {
                e.setCancelled(true);
            }
        }
    }
}
