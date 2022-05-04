package WINGS7N.providers.NMS;

import org.bukkit.Bukkit;

public class GetNMSver {
    public static String run() {
        String v = Bukkit.getServer().getClass().getPackage().getName();
        return v.substring(v.lastIndexOf('.') + 1);
    }
}
