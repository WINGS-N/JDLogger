package WINGS7N.DLogger;

import WINGS7N.DLogger.listeners.CommandListener;
import WINGS7N.DLogger.listeners.PlayerJoinListener;
import WINGS7N.DLogger.listeners.lags.LagDMGevent;
import WINGS7N.DLogger.listeners.lags.PingDMGevent;
import WINGS7N.DLogger.storage.*;
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

    public void onEnable() {
        if (BS.dev) {
            log.warning(SS.DevBuild);
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
        config.addDefault("ConfigVersion", defaults.ConfigVersion);
        config.addDefault("LOG.LogInventory", defaults.LOG_LogInventory);
        config.addDefault("LOG.LogArmor", defaults.LOG_LogArmor);
        config.addDefault("LOG.DateFormat", defaults.LOG_DateFormat);
        config.addDefault("LOG.TimeZone", defaults.LOG_TimeZone);

        config.addDefault("LagMeter.enable", defaults.LagMeter_enable);
        config.addDefault("LagMeter.prefix", defaults.LagMeter_prefix);
        config.addDefault("LagMeter.TPS.ActivateTPS", defaults.LagMeter_TPS_ActivateTPS);
        config.addDefault("LagMeter.TPS.LowTPSMessage", defaults.LagMeter_TPS_LowTPSMessage);
        config.addDefault("LagMeter.TPS.LowTPSPreventEntityDamage", defaults.LagMeter_TPS_LowTPSPreventEntityDamage);
        config.addDefault("LagMeter.TPS.LowTPSEntityDamageMessage", defaults.LagMeter_TPS_LowTPSEntityDamageMessage);
        if (APIunsupported.run(GetNMSver.run()).equals("UNKNOWN")) {
            config.addDefault("LagMeter.PING.ActivatePING", defaults.LagMeter_PING_ActivatePING);
            config.addDefault("LagMeter.PING.HighPingMessage", defaults.LagMeter_PING_HighPingMessage);
            config.addDefault("LagMeter.PING.HighPingPreventEntityDamage", defaults.LagMeter_PING_HighPingPreventEntityDamage);
            config.addDefault("LagMeter.PING.HighPingEntityDamageMessage", defaults.LagMeter_PING_HighPingEntityDamageMessage);
        }

        config.addDefault("DEV.DEBUG", defaults.DEV_DEBUG);
        config.options().copyDefaults(true);
        saveConfig();


        log.info(SS.Loading);
        //Print Bukkit Ver
        log.info(SS.Detected + Bukkit.getVersion());

        //Main JDL component
        Bukkit.getPluginManager().registerEvents(new Death(), this);
        if (debug.get()) {
            log.warning(SS.DeathEventListenerStart);
        }

        //JDL LagMeter
        if (config.getBoolean("LagMeter.enable", defaults.LagMeter_enable)) {
            if (debug.get()) {
                log.warning(SS.LagMeterStart);
            }
            if (config.getDouble("LagMeter.TPS.ActivateTPS", defaults.LagMeter_TPS_ActivateTPS) != 0) {
                Bukkit.getPluginManager().registerEvents(new LagDMGevent(), this);
                if (debug.get()) {
                    log.warning(SS.LagMeterLowTPSStart);
                }
            }
            if (APIunsupported.run(GetNMSver.run()).equals("UNKNOWN") && config.getInt("LagMeter.PING.ActivatePING", defaults.LagMeter_PING_ActivatePING) != 0) {
                Bukkit.getPluginManager().registerEvents(new PingDMGevent(), this);
                if (debug.get()) {
                    log.warning(SS.LagMeterHighPingStart);
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
