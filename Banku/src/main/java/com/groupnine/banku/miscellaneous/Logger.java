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
    /* Usado para mudar o tipo de log por default. */
    {
        defaultLogType = type;
    }

    public static void log(String message, LogType type)
    /* Mostra e registra um log do sistema. */
    {
        String infix, fullLogMessage;

        infix = switch (type) {
            case EXIT_ERROR, ERROR -> "ERROR: ";
            case WARNING -> "WARNING: ";
            case INFO -> "INFO: ";
            case DEBUG -> "DEBUG MESSAGE: ";
            default -> "LOG MESSAGE: ";
        };

        fullLogMessage = getFormattedTime() + infix + message;

        saveLog(fullLogMessage);
        printLog(fullLogMessage, type);

        if (type == LogType.EXIT_ERROR) {
            System.exit(1);
        }
    }

    private static String getFormattedTime()
    /* Retorna a hora atual formatada para uso no log. */
    {
        return "[" + LocalTime.now() + "] ";
    }

    private static void saveLog(String message)
    /* Guarda a mensagem de log no arquivo de log da aplicação*/
    {
        // todo: save log at `defaultLogFilePath`
    }

    private static void printLog(String message, LogType type)
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
