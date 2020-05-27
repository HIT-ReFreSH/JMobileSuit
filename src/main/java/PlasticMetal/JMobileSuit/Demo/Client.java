package PlasticMetal.JMobileSuit.Demo;

import PlasticMetal.JMobileSuitLite.NeuesProjekt.PowerLineThemedPromptServer;
import PlasticMetal.JMobileSuitLite.ObjectModel.Annotions.SuitAlias;
import PlasticMetal.JMobileSuitLite.ObjectModel.Annotions.SuitInfo;
import PlasticMetal.JMobileSuitLite.ObjectModel.DynamicParameter;
import PlasticMetal.JMobileSuitLite.ObjectModel.Parsing.*;
import PlasticMetal.JMobileSuitLite.ObjectModel.SuitClient;
import PlasticMetal.JMobileSuitLite.SuitHost;

import java.lang.management.ManagementFactory;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@SuitInfo("Demo")
public class Client extends SuitClient
{
    @SuitAlias("H")
    @SuitInfo("hello command")
    public void Hello()
    {

        IO().WriteLine("Hello! MobileSuit!");
    }

    public String Bye(String name)
    {
        IO().WriteLine("Bye!" + name);
        return "bye";
    }

    private static void exp() throws Exception
    {
        throw new Exception();
    }

    public static void main(String[] args) throws Exception
    {
        new SuitHost(Client.class,
                PowerLineThemedPromptServer.getPowerLineThemeConfiguration()).Run();
    }

    @SuitAlias("GM")
    public void GoodMorning(GoodMorningParameter arg)
    {
        IO().WriteLine("Good morning," + arg.name);
    }

    @SuitAlias("GM2")
    public void GoodMorning2(String arg, GoodMorningParameter arg1)
    {
        IO().WriteLine("Good morning, " + arg + " and " + arg1.name);
    }

    @SuitAlias("GE")
    public void GoodEvening(String[] arg)
    {

        IO().WriteLine("Good Evening, " + (arg.length >= 1 ? arg[0] : ""));
    }

    @SuitAlias("Sn")
    public void ShowNumber(
            @SuitParser(ParserClass = Integer.class, MethodName = "parseInt")
                    int i,
            @SuitParser(ParserClass = Integer.class, MethodName = "parseInt")
                    int[] j
    )
    {
        IO().WriteLine(String.valueOf(i));
        IO().WriteLine(j.length >= 1 ? String.valueOf(j[0]) : "");
    }

    @SuitAlias("GE2")
    public void GoodEvening2(String arg0, String[] arg)
    {

        IO().WriteLine("Good Evening, " + arg0 + (arg.length >= 1 ? " and " + arg[0] : ""));
    }

    @SuitAlias("Sl")
    @SuitInfo("Sleep {-n name (, -t hours, -s)}")
    public void Sleep(SleepArgument argument)
    {
        if (argument.isSleeping)
        {
            IO().WriteLine(argument.Name.get(0) + " has been sleeping for " + argument.SleepTime + " hour(s).");
        }
        else
        {
            IO().WriteLine(argument.Name.get(0) + " is not sleeping.");
        }
    }

    public static class SleepArgument extends AutoDynamicParameter
    {
        @Option("n")
        @AsCollection
        public List<String> Name = new ArrayList<>();

        @SuppressWarnings("CanBeFinal")
        @Option("t")
        @SuitParser(ParserClass = Integer.class, MethodName = "parseInt")
        @WithDefault
        public int SleepTime = 0;
        @Switch("s")
        public boolean isSleeping;
    }

    public static class GoodMorningParameter implements DynamicParameter
    {
        public String name = "foo";

        /**
         * parse this Parameter from String[].
         *
         * @param options String[] to parse from.
         * @return Whether the parsing is successful
         */
        @Override
        public boolean parse(String[] options)
        {
            if (options.length == 1)
            {
                name = options[0];
                return true;
            }
            else return options.length == 0;

        }
    }
}
