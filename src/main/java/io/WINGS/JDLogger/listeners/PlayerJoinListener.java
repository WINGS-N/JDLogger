package io.WINGS.JDLogger.listeners;

import io.WINGS.JDLogger.storage.BS;
import io.WINGS.JDLogger.storage.SS;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class PlayerJoinListener implements Listener {

    Plugin pl = Bukkit.getPluginManager().getPlugin(SS.pl);
    FileConfiguration config = pl.getConfig();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) throws InterruptedException {
        Player p = e.getPlayer();
        if (p.hasPermission("jdl.rollback")) {
            if (config.getInt("ConfigVersion") < 8) {
                p.sendMessage(ChatColor.RED + "[JDLogger] [WARNING] YOUR CONFIG VERSION = " + config.getInt("ConfigVersion") + "!");
                p.sendMessage(ChatColor.RED + "[JDLogger] [WARNING] CONFIG FILE WILL BE REMOVED NOW AND SERVER WIL BE STOPPED, THIS VERSION IS TOO OLD!");
                File congigfile = new File("plugins/JDLogger/config.yml");
                congigfile.delete();
                p.sendMessage(ChatColor.RED + "[JDLogger] [WARNING] USING THREAD SLEEP FOR WAIT, THIS MAY CAUSE ERRORS AND LOW TPS!");
                p.sendMessage(ChatColor.GREEN + "[JDLogger] [INFO] 30 seconds left!");
                Thread.sleep(30000);
                Bukkit.shutdown();
            }
            else if (config.getInt("ConfigVersion") == 8) {
                config.set("LagMeter.TPS.LowTPSMessage", "You haven't taken damage because tps = %f");
                config.set("ConfigVersion", 9);
                p.sendMessage(ChatColor.YELLOW + "[JDLogger] [WARNING] [CONFIG UPDATE] LagMeter.LowTPSMessage set to default, config version updated");
                p.sendMessage(ChatColor.YELLOW + "[JDLogger] [WARNING] Reloading JDLogger with PlugMan... If PlugMan not installed, restart server.");
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "plugman reload JDLogger");
            }
            else if (config.getInt("ConfigVersion") == 11) {
                p.sendMessage(ChatColor.YELLOW + "[JDLogger] [WARNING] [CONFIG UPDATE] Config version updated, please, restart your server to reconfigure JDLogger");
                File congigfile = new File("plugins/JDLogger/config.yml");
                congigfile.delete();
                p.sendMessage(ChatColor.YELLOW + "[JDLogger] [WARNING] Reloading JDLogger with PlugMan... If PlugMan not installed, restart server.");
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "plugman reload JDLogger");
            }
            else if (BS.dev) {
                p.sendMessage(ChatColor.YELLOW + "[JDLogger] [WARNING] " + SS.DevBuild);
            }
        }
    }
}
