package io.WINGS.JDLogger.storage;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;

public interface debug {
    FileConfiguration config = Objects.requireNonNull(SS.plugin).getConfig();
    static boolean get() {
        if (config.getBoolean("DEV.DEBUG")) {
            return true;
        } else return BS.dev;
    }
}
