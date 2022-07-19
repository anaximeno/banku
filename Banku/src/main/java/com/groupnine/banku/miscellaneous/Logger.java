package com.groupnine.banku.miscellaneous;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// Miscellaneous class
public class Logger {
    private static LogType defaultLogType = LogType.INFO;
    private static final List<String> logList = new ArrayList<>();
    private static final String defaultLogFilePath = "resources/banku.log";

    public void setDefaultLogType(LogType type)
    /* Usado para muda o tipo de log por default. */
    {
        defaultLogType = type;
    }

    public static void log(String message, LogType type)
    /* Mostra e registra um log do sistema. */
    {
        String fullLogMessage;
        String time = "[" + LocalTime.now() + "] ";


        switch (type) {
            case EXIT_ERROR, ERROR:
                fullLogMessage = time + "ERROR: " + message;
                break;

            case WARNING:
                fullLogMessage = time + "WARNING: " + message;
                break;

            case INFO:
                fullLogMessage = time + "INFO: " + message;
                break;

            case DEBUG:
                fullLogMessage = time + "DEBUG MESSAGE: " + message;
                break;

            default:
                fullLogMessage = time + "LOG MESSAGE: " + message;
                break;
        }

        saveLog(fullLogMessage);
        printLog(fullLogMessage, type);

        if (type == LogType.EXIT_ERROR) {
            System.exit(1);
        }
    }

    public static void saveLog(String message)
    /* Guarda a mensagem de log no arquivo de log da aplicação*/
    {
        // todo: save log at `defaultLogFilePath`
    }

    public static void printLog(String message, LogType type)
    /* Mostra a mensagem de log para saidas diferentes dependedo do
     * grau de gravidade.
     * */
    {
       switch (type) {
           case EXIT_ERROR, ERROR, WARNING -> System.err.println(message);
           default -> System.out.println(message);
       }
    }

    public static void log(String message)
    /* Mostra e registra um log do sistema usando o tipo
     * de log por default.
     * */
    {
        log(message, defaultLogType);
    }
}
