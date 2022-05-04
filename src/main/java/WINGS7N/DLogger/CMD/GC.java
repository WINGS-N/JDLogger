package WINGS7N.DLogger.CMD;

import WINGS7N.DLogger.storage.SS;
import org.bukkit.command.CommandSender;

public class GC {

    public GC(CommandSender s) {
        if (s.hasPermission(SS.GCPerm)) {
            s.sendMessage(SS.gcstart);
            System.gc();
            s.sendMessage(SS.gcsuccess);
        }
    }
}
