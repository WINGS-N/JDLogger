package WINGS.DLogger.CMD;

import WINGS.DLogger.storage.SS;
import WINGS.providers.NMS.APIunsupported;
import WINGS.providers.NMS.GetNMSver;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetPINGCMD {
    public GetPINGCMD(CommandSender s) {
        if (s.hasPermission(SS.GetPINGPerm) && s instanceof Player) {
            if (APIunsupported.run(GetNMSver.run()).equals("UNKNOWN")) {
                Player p = (Player) s;
                int ping = p.getPing();
                String pingmsg = String.format("PING = %s", ping);
                p.sendMessage(ChatColor.BLUE + SS.LagometrPrefix + pingmsg);
            } else {
                s.sendMessage(ChatColor.RED + SS.LagometrPrefix + "This feature 1.17+ only");
            }
        } else if (s.hasPermission(SS.GetPINGPerm)) {
            s.sendMessage(SS.prefix + ChatColor.RED + SS.OnlyPlayer);
        } else {
            s.sendMessage(SS.prefix + ChatColor.RED + SS.NoPerms);
        }
    }
}
