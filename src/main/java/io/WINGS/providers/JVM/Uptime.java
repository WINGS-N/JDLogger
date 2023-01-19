package io.WINGS.providers.JVM;

import io.WINGS.providers.time.ConvertDuration;

import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class Uptime {
    public String get() {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(ManagementFactory.getRuntimeMXBean().getUptime());
        return ConvertDuration.run(Duration.of(seconds, ChronoUnit.SECONDS));
    }
}
