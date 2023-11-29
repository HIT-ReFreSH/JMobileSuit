package PlasticMetal.JMobileSuitLite.Diagnostics;

import PlasticMetal.JMobileSuit.Demo.Client;
import PlasticMetal.JMobileSuitLite.IO.OutputType;
import PlasticMetal.JMobileSuitLite.NeuesProjekt.PowerLineThemedPromptServer;
import PlasticMetal.JMobileSuitLite.ObjectModel.Annotions.SuitAlias;
import PlasticMetal.JMobileSuitLite.ObjectModel.Annotions.SuitInfo;
import PlasticMetal.JMobileSuitLite.ObjectModel.InfoProvider;
import PlasticMetal.JMobileSuitLite.ObjectModel.Parsing.AutoDynamicParameter;
import PlasticMetal.JMobileSuitLite.ObjectModel.Parsing.Option;
import PlasticMetal.JMobileSuitLite.ObjectModel.Parsing.SuitParser;
import PlasticMetal.JMobileSuitLite.ObjectModel.Parsing.WithDefault;
import PlasticMetal.JMobileSuitLite.ObjectModel.SuitClient;
import PlasticMetal.JMobileSuitLite.SuitHost;
import PlasticMetal.JMobileSuitLite.TraceBack;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@SuitInfo(resourceBundleName = "LogDriver", value = "Class")
public class LogDriver extends SuitClient
{
    public static void main(String[] args) throws Exception
    {
        new SuitHost(LogDriver.class,
                PowerLineThemedPromptServer.getPowerLineThemeConfiguration()).Run();
    }
    private final SuitLogger _logger;
    private final List<LogUtil> logMem;

    /**
     * Initialize a log driver with the logger
     *
     * @param logger the logger to operate
     */
    public LogDriver(SuitLogger logger)
    {
        _logger = logger;
        logMem = logger.getLogMem();
    }

    public LogDriver()throws IOException
    {
        SuitLogger logger = new SuitLogger("../LogUtil.txt");
        _logger = logger;
        logMem = logger.getLogMem();
    }

    public static class LogFilter extends AutoDynamicParameter
    {

        public static LocalDateTime ParseTime(String str)
        {
            return LocalDateTime.parse(str, LogUtil.DateTimeFormat);

        }

        @Option(value = "s", length = 2)
        @WithDefault
        @SuitParser(ParserClass = LogFilter.class, MethodName = "ParseTime")
        public LocalDateTime start = LocalDateTime.MIN;

        @Option(value = "e", length = 2)
        @WithDefault
        @SuitParser(ParserClass = LogFilter.class, MethodName = "ParseTime")
        public LocalDateTime end = LocalDateTime.MAX;

        @Option("t")
        @WithDefault
        @SuitParser(ParserClass = Pattern.class, MethodName = "compile")
        public Pattern TypeRegex = Pattern.compile("");

        @Option("m")
        @WithDefault
        @SuitParser(ParserClass = Pattern.class, MethodName = "compile")
        public Pattern MessageRegex = Pattern.compile("");
    }

    @SuitAlias("On")
    @SuitInfo(resourceBundleName = "LogDriver", value = "Enable")
    public TraceBack Enable()
    {
        _logger.EnableLogQuery = true;
        return TraceBack.AllOk;
    }

    @SuitAlias("Off")
    @SuitInfo(resourceBundleName = "LogDriver", value = "Disable")
    public TraceBack Disable()
    {
        _logger.EnableLogQuery = false;
        return TraceBack.AllOk;
    }

    @SuitAlias("F")
    @SuitInfo(resourceBundleName = "LogDriver", value = "Find")
    public int Find(LogFilter filter)
    {
        List<LogUtil> logsToShow = logMem.parallelStream().filter(
                u -> u.TimeStamp.isAfter(filter.start) &&
                        u.TimeStamp.isBefore(filter.end) &&
                        filter.TypeRegex.asPredicate().test(u.Type) &&
                        filter.MessageRegex.asPredicate().test(u.Message))
                .sorted(Comparator.comparing(o -> o.TimeStamp)).collect(Collectors.toList());
        int i = 1;
        for (LogUtil u : logsToShow)
        {
            IO().WriteLine("(" + i + ") " + u.Type, OutputType.ListTitle);

            IO().AppendWriteLinePrefix();
            IO().WriteLine(
                    InfoProvider.getInfo("LogDriver", "Time") + ": ");

            IO().AppendWriteLinePrefix();
            IO().WriteLine(u.TimeStamp.format(LogUtil.DateTimeFormat), OutputType.MobileSuitInfo);
            IO().SubtractWriteLinePrefix();

            IO().WriteLine(
                    InfoProvider.getInfo("LogDriver", "Info") + ": ");

            IO().AppendWriteLinePrefix();
            String[] message = u.Message.split("\n");
            for (String m : message)
            {
                IO().WriteLine(m, OutputType.CustomInfo);
            }
            IO().SubtractWriteLinePrefix();
            IO().SubtractWriteLinePrefix();
            i++;
        }

        return logsToShow.size();
    }

    @SuitAlias("S")
    @SuitInfo(resourceBundleName = "LogDriver", value = "Status")
    public String Status()
    {
        IO().WriteLine(InfoProvider.getInfo("LogDriver", "LogFileAt") + ": ");
        IO().AppendWriteLinePrefix();
        IO().WriteLine(_logger.Path, OutputType.MobileSuitInfo);
        IO().SubtractWriteLinePrefix();
        IO().WriteLine(InfoProvider.getInfo("LogDriver", "Dynamic") + ": ");
        IO().AppendWriteLinePrefix();
        IO().WriteLine(_logger.EnableLogQuery ?
                InfoProvider.getInfo("LogDriver", "On") :
                InfoProvider.getInfo("LogDriver", "Off"), OutputType.MobileSuitInfo);
        IO().SubtractWriteLinePrefix();
        return _logger.Path;
    }

}
