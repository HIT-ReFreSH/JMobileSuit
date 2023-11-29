package PlasticMetal.JMobileSuitLite.Diagnostics;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utils Representing logs
 */
public class LogUtil{
    public static final DateTimeFormatter DateTimeFormat=DateTimeFormatter.ofPattern("yyMMdd HH:mm:ss");
    /**
     * Timestamp of the log
     */
    public final LocalDateTime TimeStamp;
    /**
     * type of the log
     */
    public final String Type;
    /**
     * message of the log
     */
    public final String Message;

    /**
     * Initialize a LogUtil with timestamp, type and message
     * @param timeStamp Timestamp of the log
     * @param type type of the log
     * @param message message of the log
     */
    public LogUtil(LocalDateTime timeStamp, String type, String message)
    {
        TimeStamp = timeStamp;
        Type = type;
        Message = message;
    }
}
