package WINGS.DLogger.listeners;

import WINGS.DLogger.CMD.GC;
import WINGS.DLogger.CMD.GetPINGCMD;
import WINGS.DLogger.CMD.GetTPSCMD;
import WINGS.DLogger.CMD.HelpCommand;
import WINGS.DLogger.storage.SS;
import WINGS.InventoryWorker.DecodeInventoryFromFile;
import WINGS.InventoryWorker.DecodeInventoryFromFileLegacy;
import WINGS.PluginUpdater.SelfUpdate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class CommandListener implements Listener, CommandExecutor {

    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase(SS.jdlcmd)) {

            if (args.length == 0) {
                if (s.hasPermission(SS.HelpPerm)) {
                    new HelpCommand(s);
                }
            } else {
                switch (args[0].toLowerCase()) {
                    case "rollback":
                    case "r":
                        if (s.hasPermission(SS.checkPerm)) {
                            if (s instanceof Player) {
                                String FileName = args[1];
                                new DecodeInventoryFromFile(s, FileName);
                            } else {
                                s.sendMessage(SS.OnlyPlayer);
                            }
                        }
                        break;
                    case "tps":
                        if (s.hasPermission(SS.GetTPSPerm)) {
                            new GetTPSCMD(s);
                        }
                        break;
                    case "ping":
                        new GetPINGCMD(s);
                        break;
                    case "help":
                    case "h":
                        if (s.hasPermission(SS.HelpPerm)) {
                            new HelpCommand(s);
                        }
                        break;
                    case "u":
                    case "upd":
                    case "update":
                        if (s.hasPermission(SS.UPDPerm)) {
                            new SelfUpdate(s);
                        }
                        break;
                    case "gc":
                        if (s.hasPermission(SS.GCPerm)) {
                            new GC(s);
                        }
                        break;
                    default:
                        if (s.hasPermission(SS.HelpPerm)) {
                            new HelpCommand(s);
                        } else {
                            s.sendMessage(SS.NoPerms);
                        }
                        break;
                }
            }
        }

        if (cmd.getName().equalsIgnoreCase(SS.jdllegacycmd)) {
            if (s instanceof Player) {
                if (args.length == 0) {
                    s.sendMessage(SS.ERROR);
                } else {
                    String FullFileName = args[1];
                    new DecodeInventoryFromFileLegacy(s, FullFileName);
                }
            }
        }
        return true;
    }
}
