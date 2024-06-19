package xyz.kozord.torchmclobby.utils;

public class TimeUtil {

    public static String format(long timeMillis) {
        long milliseconds = timeMillis % 1000L;
        long seconds = timeMillis / 1000L % 60L;
        long minutes = timeMillis / 60000L % 60L;
        long hours = timeMillis / 3600000L % 24L;
        long days = timeMillis / 86400000L % 365L;
        long years = timeMillis / 31536000000L;
        StringBuilder formattedTime = new StringBuilder();
        if (years > 0L) {
            formattedTime.append(years).append("y ");
        }

        if (days > 0L) {
            formattedTime.append(days).append("d ");
        }

        if (hours > 0L) {
            formattedTime.append(hours).append("h ");
        }

        if (minutes > 0L) {
            formattedTime.append(minutes).append("m ");
        }

        if (seconds > 0L) {
            formattedTime.append(seconds).append("s ");
        }

        if (milliseconds > 0L || formattedTime.length() == 0 && timeMillis < 1000L) {
            formattedTime.append(milliseconds).append("ms ");
        }

        return formattedTime.toString().trim();
    }
}
