package io.WINGS.PluginUpdater;

import io.WINGS.JDLogger.storage.SS;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class SelfUpdateFromBackupServer {

    public Boolean updateInProgress = false;
    Plugin pl = Bukkit.getPluginManager().getPlugin(SS.pl);
    FileConfiguration config = Objects.requireNonNull(pl).getConfig();

    public SelfUpdateFromBackupServer(CommandSender s) {
        pl.getLogger().warning("bck_srv_upd disabled");
    }
        /*
        if (updateInProgress) {
            s.sendMessage(ChatColor.RED + "Update from backup server already in progress");
            return;
        }

        s.sendMessage(UpdateData.prefix + ChatColor.RED + "Self-Updating " + UpdateData.UpdatePlugin + " from backup server...");

        updateInProgress = true;

        try {
            Method getFile = JavaPlugin.class.getDeclaredMethod("getFile");
            getFile.setAccessible(true);
            String plugin_jar = Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(UpdateData.UpdatePlugin)).getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
            File dest = new File(plugin_jar);

            //Connect
            URL url =
                    new URL(UpdateData.deprecatedURL +
                            UpdateData.UpdatePlugin +
                            UpdateData.ext);

            // Creating con
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", String.format("WINGS07/%s-SelfUpdater", UpdateData.UpdatePlugin));

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
         */
}
