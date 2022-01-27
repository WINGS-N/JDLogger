package WINGS.PluginUpdater;

import WINGS.DLogger.storage.SS;
import WINGS.DLogger.storage.UpdateData;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class SelfUpdateFromBackupServer {

    public Boolean updateInProgress = false;
    Plugin pl = Bukkit.getPluginManager().getPlugin(SS.pl);
    FileConfiguration config = pl.getConfig();

    public SelfUpdateFromBackupServer(CommandSender s) {
        if (updateInProgress) {
            s.sendMessage(ChatColor.RED + "Update from backup server already in progress");
            return;
        }

        s.sendMessage(UpdateData.prefix + ChatColor.RED + "Self-Updating " + UpdateData.UpdatePlugin + " from backup server...");

        updateInProgress = true;

        try {
            Method getFile = JavaPlugin.class.getDeclaredMethod("getFile");
            getFile.setAccessible(true);
            File dest = new File("plugins/" + UpdateData.UpdatePlugin + UpdateData.ext);

            //Connect
            URL url =
                    new URL(UpdateData.deprecatedURL +
                            UpdateData.UpdatePlugin +
                            UpdateData.ext);

            // Creating con
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "WINGS07/JDLogger-SelfUpdater");

            // Get input stream
            try (InputStream input = con.getInputStream()) {
                Files.copy(input, dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            s.sendMessage(UpdateData.prefix + ChatColor.RED + "Update from backup server success!");
        } catch (Exception ex) {
            ex.printStackTrace();
            s.sendMessage(UpdateData.prefix + ChatColor.RED + "Update from backup server failed, " + ex.getMessage());
        } finally {
            updateInProgress = false;
        }
    }
}
