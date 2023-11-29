package PlasticMetal.JMobileSuitLite.Diagnostics;

import PlasticMetal.JMobileSuitLite.ObjectModel.Annotions.SuitIgnore;
import PlasticMetal.JMobileSuitLite.TraceBack;
import org.apache.log4j.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static PlasticMetal.Jarvis.Common.*;
import static org.apache.log4j.Level.ALL;
import static org.apache.log4j.Level.TRACE;

public class SuitLogger
{
    private final List<LogUtil> logMem = new ArrayList<>();
    public boolean EnableLogQuery = true;
    private final Logger logger = LogManager.getLogger(SuitLogger.class);


    private void addLog(String info,String content){
        LocalDateTime time=LocalDateTime.now();
        if(EnableLogQuery){
            logMem.add(new LogUtil(time,info,content));
        }

    }
    /**
     * get a clone record of memorised logs.
     *
     * @return clone record of memorised logs.
     */
    public List<LogUtil> getLogMem()
    {
        return new ArrayList<>(logMem);
    }

    @SuitIgnore
    public void exitLogger()
    {
        logger.setLevel(Level.OFF);
    }




    /**
     * Write debug info to the log file
     *
     * @param content debug info
     */

    public void LogDebug(String content)
    {

        logger.info(content);
        addLog("info",content);
    }

    /**
     * Write command info to the log file
     *
     * @param content command info
     */
    public void LogCommand(String content)
    {
        logger.info("Command: "+content);
        addLog("Command",content);
    }

    /**
     * Write return info to the log file
     *
     * @param content return info (TraceBack)
     */
    public void LogTraceBack(TraceBack content)
    {
        logger.trace(content);
        addLog("TraceBack",content.toString());
    }

    /**
     * Write return info to the log file
     *
     * @param content     return info (TraceBack)
     * @param returnValue return info (Return Value)
     */
    public void LogTraceBack(TraceBack content, Object returnValue)
    {

        logger.trace(content.toString()+returnValue.toString());
        addLog("TraceBack",content+"("+returnValue+")");
    }

    /**
     * Write custom exception info to the log file
     *
     * @param content custom exception info
     */
    public void LogException(String content)
    {
        logger.error(content);
        addLog("Exception",content);
    }

    /**
     * Write exception info to the log file
     *
     * @param content the Exception
     */
    public void LogException(Exception content)
    {
        StringBuilder stringBuilder = new StringBuilder(CoalesceNull(content.getMessage(), ""));
        for (StackTraceElement se : content.getStackTrace()
        )
        {
            stringBuilder.append("\n\tAt ").append(se);
        }

        logger.error(content.getClass().getName()+"  "+stringBuilder);
        addLog(content.getClass().getName(),stringBuilder.toString());
    }


    /**
     * get path of current log file
     */
    public final String Path;



    protected SuitLogger(String path) throws IOException
    {
        Path = path;
        PatternLayout patternLayout = new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %5p  - %m%n");
        RollingFileAppender appender=new RollingFileAppender(patternLayout,path);
        appender.setThreshold(ALL);
        logger.addAppender(appender);

    }

    private static String getFileName()
    {
        return "PlasticMetal.MobileSuit_"
                + ManagementFactory.getRuntimeMXBean().getName()
                + "_" + LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")) + ".log";
    }

    /**
     * Create a log file in given directory with standard name
     *
     * @param dirPath given directory
     * @return the log file, null if invalid path
     */
    public static SuitLogger ofDirectory(String dirPath)
    {
        return tryCreate(Paths.get(dirPath, getFileName()).toString());
    }

    private static SuitLogger tryCreate(String path)
    {
        try
        {
            return new SuitLogger(path);
        }
        catch (IOException e)
        {
            return null;
        }
    }

    /**
     * Create a log file in given path
     *
     * @param path given path
     * @return the log file, null if invalid path
     */
    public static SuitLogger ofFile(String path)
    {
        return tryCreate(Paths.get(path).toString());
    }

    /**
     * Create a log file in current directory with standard name
     *
     * @return the log file, null if invalid path
     */
    public static SuitLogger ofLocal()
    {

        return tryCreate(Paths.get(System.getProperty("user.dir"), getFileName()).toString());
    }

    /**
     * Create a log file in System temp directory with standard name
     *
     * @return the log file, null if invalid path
     */
    public static SuitLogger ofTemp()
    {
        return tryCreate(Paths.get(System.getProperty("java.io.tmpdir"), getFileName()).toString());
    }
}
