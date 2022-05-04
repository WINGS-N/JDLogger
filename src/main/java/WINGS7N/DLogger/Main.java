package WINGS7N.DLogger;

import WINGS7N.DLogger.listeners.CommandListener;
import WINGS7N.DLogger.listeners.PlayerJoinListener;
import WINGS7N.DLogger.listeners.lags.LagDMGevent;
import WINGS7N.DLogger.listeners.lags.PingDMGevent;
import WINGS7N.DLogger.storage.BS;
import WINGS7N.DLogger.storage.IS;
import WINGS7N.DLogger.storage.SS;
import WINGS7N.DLogger.storage.UpdateData;
import WINGS7N.PluginUpdater.DownloadWinUpdater;
import WINGS7N.PluginUpdater.SelfUpdate;
import WINGS7N.providers.NMS.APIunsupported;
import WINGS7N.providers.NMS.GetNMSver;
import WINGS7N.providers.metrics.Metrics;
import WINGS7N.providers.metrics.MetricsData;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

public class Main extends JavaPlugin implements Listener {

    public Logger log = getLogger();
    private static final CommandListener commandListener = new CommandListener();
    FileConfiguration config = this.getConfig();
    File winupdater = new File("plugins/" + UpdateData.UpdatePlugin + "_WinUPD" + UpdateData.ext);

    public String os = System.getProperty("os.name");
    public boolean debug = config.getBoolean("DEV.DEBUG");

    public void onEnable() {
        if (BS.dev) {
            log.warning(SS.DevBuild);
            debug = true;
            Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        }

        if (os.contains("Windows")) {
            if (!winupdater.exists()) {
                new DownloadWinUpdater(Bukkit.getConsoleSender());
            } else {
                log.warning(SS.WindowsSelfUpdateNote);
            }
        } else if (BS.dev) {
            log.warning(SS.NoDevBuildSelfUpdate);
        } else {
            if (winupdater.exists()) {
                winupdater.delete();
            }
            new SelfUpdate(Bukkit.getConsoleSender());
        }

        //CFG ver checks:
        if (!new File("plugins/JDLogger/config.yml").exists()) {
            log.info(SS.CFGnull);
        } else if (config.getInt("ConfigVersion") > IS.CFGver) {
            log.warning(SS.CFGver1);
        } else if (config.getInt("ConfigVersion") < IS.CFGver) {
            log.warning(SS.CFGver0);
        } else if (config.getInt("ConfigVersion") < 8) {
            Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        } else if (config.getInt("ConfigVersion") == IS.CFGver) {
            log.info(SS.CFGverPASS);
        }

        //Config defaults
        this.saveDefaultConfig();
        config.addDefault("ConfigVersion", IS.CFGver);
        config.addDefault("LOG.LogInventory", true);
        config.addDefault("LOG.LogArmor", true);
        config.addDefault("LOG.DateFormat", "dd.MM.yy_HH.mm.ss");
        config.addDefault("LOG.TimeZone", "Asia/Seoul");

        config.addDefault("LagMeter.enable", true);
        config.addDefault("LagMeter.ActivateTPS", 16.0);
        if (APIunsupported.run(GetNMSver.run()).equals("UNKNOWN")) {
            config.addDefault("LagMeter.ActivatePING", 700);
        }
        config.addDefault("LagMeter.prefix", "[JDL >>> LAGMETER]");
        config.addDefault("LagMeter.LowTPSMessage", "You haven't taken damage because tps = %s");
        if (APIunsupported.run(GetNMSver.run()).equals("UNKNOWN")) {
            config.addDefault("LagMeter.BigPingMessage", "You haven't taken damage because your ping = %s");
        }

        config.addDefault("DEV.DEBUG", false);
        config.options().copyDefaults(true);
        saveConfig();


        log.info(SS.Loading);
        //Print Bukkit Ver
        log.info(SS.Detected + Bukkit.getVersion());

        //Main JDL component
        Bukkit.getPluginManager().registerEvents(new Death(), this);
        if (debug) {
            log.warning(SS.DeathEventListenerStart);
        }

        //JDL LagMeter
        if (config.getBoolean("LagMeter.enable")) {
            if (debug) {
                log.warning(SS.LagMeterStart);
            }
            if (config.getInt("LagMeter.ActivateTPS") != 0) {
                Bukkit.getPluginManager().registerEvents(new LagDMGevent(), this);
                if (debug) {
                    log.warning(SS.LagMeterLowTPSStart);
                }
            }
            if (APIunsupported.run(GetNMSver.run()).equals("UNKNOWN") && config.getInt("LagMeter.ActivatePING") != 0) {
                Bukkit.getPluginManager().registerEvents(new PingDMGevent(), this);
                if (debug) {
                    log.warning(SS.LagMeterBigPingStart);
                }
            }

        }

        //Check OS
        if (os.contains("Windows")) {
            log.info(SS.Windows);
            log.info(SS.WindowsWarning);
        } else {
            log.info(SS.NotWindows);
            log.info(SS.NotWindowsRunning + os);
        }

        //Print Author
        log.info(SS.by + SS.PluginAuthor);

        //reg cmd
        Objects.requireNonNull(getCommand(SS.jdlcmd)).setExecutor(commandListener);
        Objects.requireNonNull(getCommand(SS.jdllegacycmd)).setExecutor(commandListener);

        //connect metrics
        if (!BS.dev) {
            Metrics m = new Metrics(this, MetricsData.id);
            m.getPluginData();
        }
    }

    public void onDisable() {
        if (os.contains("Windows")) {
            if (!winupdater.exists()) {
                new DownloadWinUpdater(Bukkit.getConsoleSender());
            } else {
                log.warning(SS.WindowsSelfUpdateNote);
            }
        } else if (BS.dev) {
            log.warning(SS.DevBuild);
            log.warning(SS.NoDevBuildSelfUpdate);
        } else {
            if (winupdater.exists()) {
                winupdater.delete();
            }
            new SelfUpdate(Bukkit.getConsoleSender());
        }
        log.info(SS.Unloading);
    }
}
