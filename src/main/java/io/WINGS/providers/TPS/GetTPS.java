package io.WINGS.providers.TPS;

import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.lang.reflect.Field;

public class GetTPS {
    private static Object minecraftServer;
    private static Field recentTps;

    public static double[] run() {
        try {
            return getRecentTpsReflection();
        } catch (Throwable throwable) {
            return new double[]{0, 0, 0};
        }
    }

    private static double[] getRecentTpsReflection() throws Throwable {
        if (minecraftServer == null) {
            Server server = Bukkit.getServer();
            Field consoleField = server.getClass().getDeclaredField("console");
            consoleField.setAccessible(true);
            minecraftServer = consoleField.get(server);
        }
        if (recentTps == null) {
            recentTps = minecraftServer.getClass().getSuperclass().getDeclaredField("recentTps");
            recentTps.setAccessible(true);
        }
        return (double[]) recentTps.get(minecraftServer);
    }
}
