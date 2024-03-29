package io.WINGS.JDLogger.CMD;

import io.WINGS.JDLogger.storage.SS;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

public class HelpCommand {

    public HelpCommand(CommandSender s) {
        if (s.hasPermission(SS.HelpPerm)) {
            s.sendMessage(ChatColor.AQUA + SS.LogSeparator);
            s.sendMessage(ChatColor.BLUE + SS.RightsInLogFile);
            s.sendMessage(ChatColor.AQUA + SS.LogSeparator);
            s.sendMessage(SS.Space);
            s.sendMessage(ChatColor.AQUA + SS.helpRollback);
            s.sendMessage(ChatColor.AQUA + SS.helpRollback2);
            s.sendMessage(ChatColor.AQUA + SS.helpTPS);
            s.sendMessage(ChatColor.AQUA + SS.helpPING);
            s.sendMessage(ChatColor.AQUA + SS.helpGC);
            s.sendMessage(ChatColor.AQUA + SS.helpInfo);
        }
    }
}
