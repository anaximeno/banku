package com.groupnine.banku;

import java.time.LocalDateTime;
import java.time.LocalTime;

// Miscellaneous class
public class Misc {
    public enum LogType {
        DEBUG, INFO, WARNING, ERROR, EXIT_ERROR
    }

    private static LogType defaultLogType = LogType.INFO;

    public void setDefaultLogType(LogType type) {
        defaultLogType = type;
    }

    public static void log(String message, LogType type) {
        String time = "[" + LocalTime.now() + "] ";
        String prefix;

        switch (type) {
            case EXIT_ERROR, ERROR:
                System.err.println(time + "ERROR: " + message);
                break;

            case WARNING:
                System.err.println(time + "WARNING: " + message);
                break;

            case INFO:
                System.out.println(time + "INFO: " + message);
                break;

            case DEBUG:
                System.out.println(time + "DEBUG MESSAGE: " + message);
                break;

            default:
                System.out.println(time + "LOG MESSAGE: " + message);
                break;
        }

        if (type == LogType.EXIT_ERROR) {
            System.exit(1);
        }
    }

    public static void log(String message) {
        log(message, defaultLogType);
    }
}
