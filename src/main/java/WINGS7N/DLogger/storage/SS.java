package WINGS7N.DLogger.storage;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public interface SS {

    Plugin plugin = Bukkit.getPluginManager().getPlugin("JDLogger");
    FileConfiguration config = Objects.requireNonNull(plugin).getConfig();

    //String Storage

    //Permissions
    String accessPerm = "jdl.lowtps";
    String BigPingPerm = "jdl.bigping";
    String checkPerm = "jdl.rollback";
    String HelpPerm = "jdl.help";
    String GetTPSPerm = "jdl.gettps";
    String GetPINGPerm = "jdl.getping";
    String UPDPerm = "jdl.upd";
    String GCPerm = "jdl.gc";

    //Date Formatter
    String DateFormat = "dd.MM.yy_HH.mm.ss";

    //System Strings
    String max = ">";
    String min = "<";
    String eq = "=";
    String NULL = "";

    //File Utils
    String DotInv = ".inv";
    String DotLog = ".log";
    String FolderSeparator = "/";
    String DLPath = "logs/JDLogger/";
    String DLInvPath = "/InvData/";
    String DLArmorPath = "/ArmorData/";
    String ul = "_";
    String DL = "DL";
    String Space = " ";
    String SeparatorV2 = ".";

    //Authors & Plugin data
    String pl = "JDLogger";
    String PluginAuthor = "WINGS7N";
    String bug_report = "https://github.com/WINGS7N/JDLogger/issues";

    //Messages

    //On load MSG
    String LagMeterStart = "Starting LagMeter : Loading components...";
    String LagMeterLowTPSStart = "Starting LagMeter : component : WINGS7N.DLogger.listeners.lags.LagDMGevent";
    String LagMeterHighPingStart = "Starting LagMeter : component : WINGS7N.DLogger.listeners.lags.PingsDMGevent";
    String DeathEventListenerStart = "Starting Death listener : WINGS7N.DLogger.Death";
    String by = "By ";
    String Detected = "Detected ";
    String Loading = "Loading...";
    String Unloading = "Unloading...";
    String CFGver1 = "Your config is new. Errors may occur. JDL trying to ignore/delete unknown values, but recommended delete this config to generate new.";
    String CFGver0 = "Your config is old. Errors may occur. JDL will try to add new values to old config, but recommended delete old config to generate new.";
    String CFGnull = "Config not found. Creating new JDL configuration...";
    String CFGverPASS = "JDL config version passed check!";
    String Windows = "Running on Windows, some features may be disabled!";
    String WindowsWarning = "JDLogger not tested on Windows OS, so if you notice any bugs, report it: " + bug_report;
    String NotWindows = "Running not on Windows OS. All JDLogger functions enabled! " + "\uD83D\uDC9C";
    String NotWindowsRunning = "Running on: ";
    String WindowsSelfUpdateNote = "SelfUpdate on Windows OS still in beta. Plugin JDLogger_WinUPD.jar responsible for SelfUpdate, please, don't rename/remove it.";
    String NoDevBuildSelfUpdate = "Running dev build, auto update cancelled";
    String DevBuild = ChatColor.RED + "______ JDLogger DEV BUILD! ______";

    //Prefix
    String prefix = ChatColor.BLACK +
            "[" +
            ChatColor.LIGHT_PURPLE +
            SS.pl +
            ChatColor.BLACK +
            "]" +
            ChatColor.RESET +
            " ";
    String LagometrPrefix = config.getString("LagMeter.prefix", defaults.LagMeter_prefix) + " ";

    //LagMeter Messages
    String LowTps = config.getString("LagMeter.TPS.LowTPSMessage", defaults.LagMeter_TPS_LowTPSMessage) + " ";
    String HighPing = config.getString("LagMeter.PING.HighPingMessage", defaults.LagMeter_PING_HighPingMessage) + " ";
    String LowTpsMob = config.getString("LagMeter.TPS.LowTPSEntityDamageMessage", defaults.LagMeter_TPS_LowTPSEntityDamageMessage) + " ";
    String HighPingMob = config.getString("LagMeter.PING.HighPingEntityDamageMessage", defaults.LagMeter_PING_HighPingEntityDamageMessage) + " ";
    String Ping = "PING = %s";

    //File Info Strings
    String RightsInLogFile = "DEATH LOGGER WINGS7N EDITION BY " + SS.PluginAuthor;
    String LogSeparator = "---------------------------";
    String LogSeparatorHashTag = "##############";
    String Using = "Using ";
    String site = "Site: ";
    String InvSavedIn = "Inventory path: ";
    String ArmorSavedIn = "Armor path: ";

    //Errors
    String cantFind = "Can't Find File ";
    String OnlyPlayer = "Only player can execute this command.";
    String NoPerms = "No permissions!";

    //Log Message
    String CreatingFolder = "Creating logs folder for player ";
    String CreatingInvDataFolder = "Creating inventory storage folder for player ";
    String CreatingMainDLFolder = "Creating main JDLogger's logs folder";
    String Logging = "Logging this death...";
    String ERROR = "ERROR ";

    //Debug
    String LowTPSdetected = "Low TPS Detected by LagMeter. TPS = ";
    String PlayerDamageDetected = "Player Damage Event Detected!";
    String CancelingEvent = "Canceling Event ";
    String TimeNow = "Date and Time Detected: ";
    String DetectedArgs = "Detected Args: ";
    String SettingIS = "Setting Item Stacks...";
    String InvDataCV = "Inventory Coded Data Value = ";
    String ArmorDataCV = "Armor Coded Data Value = ";
    String Decoding = "Decoding...";
    String SendingInvData = "Sending Decoded Inventory to player ";
    String SendingArmorData = "Sending Decoded Armor Data to player ";
    String TZID = "TimeZone ID = ";
    String HighPingDetected = "High ping detected by LagMeter. PING of player %s = %s";
    String LowTPSEntityDamageDetected = "Low TPS EntityDamage detected by LagMeter. Damager = %s, TPS = %s";
    String HighPingEntityDamageDetected = "High ping EntityDamage detected by LagMeter. Damager = %s, PING = %s";

    //CMD
    String jdlcmd = "jdl";
    String jdllegacycmd = "jdllegacy";

    //CMD help Messages
    String helpPage = "JDLogger v." + Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(pl)).getDescription().getVersion() + " Help (1/1)";
    String helpRollback = "/jdl r PlayerName/" + config.getString("LOG.DateFormat") + " - Rollback Player's on-Death inventory";
    String helpTPS = "/jdl tps - get JDLogger's tps";
    String helpPING = "/jdl ping - get ping";
    String helpGC = "/jdl gc - execute java garbage collection via System.gc(), may be can temporary help fix low TPS" + ChatColor.YELLOW + " (!USE WITH CAUTION!)";
    String helpInfo = "/jdl info - Info about JDLogger";

    //CMD GC Messages
    String gcstart = UpdateData.prefix + ChatColor.YELLOW + "Starting Garbage Collection...";
    String gcsuccess = UpdateData.prefix + ChatColor.GREEN + "Garbage Collection completed";

    //Log File Strings
    String ServerIP = "Server IP: ";
    String ServerPort = "Server Port: ";
}
