package WINGS.providers.time;

import java.time.Duration;

public class ConvertDuration {
    public static String run(final Duration duration) {
        final StringBuilder builder = new StringBuilder();

        long seconds = duration.getSeconds();
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        final long weeks = days / 7;

        seconds %= 60;
        minutes %= 60;
        hours %= 24;
        days %= 7;

        if (seconds > 0) {
            builder.insert(0, seconds + "s");
        }

        if (minutes > 0) {
            if (builder.length() > 0) {
                builder.insert(0, ' ');
            }

            builder.insert(0, minutes + "m");
        }

        if (hours > 0) {
            if (builder.length() > 0) {
                builder.insert(0, ' ');
            }

            builder.insert(0, hours + "h");
        }

        if (days > 0) {
            if (builder.length() > 0) {
                builder.insert(0, ' ');
            }

            builder.insert(0, days + "d");
        }

        if (weeks > 0) {
            if (builder.length() > 0) {
                builder.insert(0, ' ');
            }

            builder.insert(0, weeks + "w");
        }
        return builder.toString();
    }
}
