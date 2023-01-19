package io.WINGS.JDLogger.CMD;

import io.WINGS.JDLogger.storage.SS;
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
