package io.WINGS.JDLogger.CMD;

import io.WINGS.JDLogger.storage.SS;
import io.WINGS.providers.TPS.GetTPS;
import org.bukkit.command.CommandSender;

public class GetTPSCMD {
    public GetTPSCMD(CommandSender s) {
        if (s.hasPermission(SS.GetTPSPerm)) {
            double tps1m = GetTPS.run()[0];
            double tps5m = GetTPS.run()[1];
            double tps15m = GetTPS.run()[2];
            String tpsmsg = String.format("1m = %f, 5m = %f, 15m = %f", tps1m, tps5m, tps15m);
            s.sendMessage(SS.LagmetrPrefix + SS.eq + SS.Space + tpsmsg);
        }
    }
}
