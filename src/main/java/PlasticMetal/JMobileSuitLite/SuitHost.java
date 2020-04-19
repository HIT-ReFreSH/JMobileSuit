package PlasticMetal.JMobileSuitLite;

import PlasticMetal.JMobileSuitLite.IO.IOServer;
import PlasticMetal.JMobileSuitLite.IO.OutputType;
import PlasticMetal.JMobileSuitLite.ObjectModel.Annotions.SuitInfo;
import PlasticMetal.JMobileSuitLite.ObjectModel.Interfaces.IOInteractive;
import PlasticMetal.JMobileSuitLite.ObjectModel.Interfaces.InfoProvider;
import PlasticMetal.JMobileSuitLite.ObjectModel.SuitObject;
import PlasticMetal.JMobileSuitLite.ObjectModel.Tuple;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static PlasticMetal.JMobileSuitLite.TraceBack.*;

/**
 * A entity, which serves the shell functions of a mobile-suit program.
 */
@SuppressWarnings("ALL")
public class SuitHost
{


    /**
     * Initialize a SuitHost with general BicServer, IOServer, an instance.
     *
     * @param instance The instance for Mobile Suit to drive.
     */
    public SuitHost(Object instance)

    {
        IO = GeneralIO;
        BicServer = new SuitObject(new SuitBuildInCommandServer(this));
        Current = new SuitObject(instance);
        WorkInstanceInit();
    }

    /**
     * Initialize a SuitHost with given BicServer, IOServer, an instance.
     *
     * @param instance  The instance for Mobile Suit to drive.
     * @param io        An IOServer, GeneralIO as default.
     * @param bicServer An BicServer, new MobileSuit.SuitBicServer as default.
     */
    public SuitHost(Object instance, IOServer io, SuitBuildInCommandServer bicServer)

    {
        IO = io == null ? GeneralIO : io;
        BicServer = new SuitObject(bicServer == null ? new SuitBuildInCommandServer(this) : bicServer);
        Current = new SuitObject(instance);
        WorkInstanceInit();

    }

    /**
     * Initialize a SuitHost with general BicServer, IOServer, a type.
     *
     * @param type The type for Mobile Suit to drive.
     */
    public SuitHost(Class<?> type) throws IllegalAccessException, InstantiationException

    {

        this(type.newInstance());
    }

    /**
     * Initialize a SuitHost with given BicServer, IOServer, a type.
     *
     * @param type      The type for Mobile Suit to drive.
     * @param io        An IOServer, GeneralIO as default.
     * @param bicServer An BicServer, new MobileSuit.SuitBicServer as default.
     */
    public SuitHost(Class<?> type, IOServer io, SuitBuildInCommandServer bicServer) throws IllegalAccessException, InstantiationException

    {
        this(type.newInstance(), io, bicServer);
    }

    /**
     * Stack of Instance, created in this Mobile Suit.
     */
    public Stack<SuitObject> InstanceStack = new Stack<>();
    /**
     * String of Current Instance's Name.
     */
    public final List<String> InstanceNameString = new ArrayList<>();
    /**
     * Stack of Instance's Name Strings.
     */
    public final Stack<List<String>> InstanceNameStringStack = new Stack<>();
    /**
     * If the prompt contains the reference (For example, System.Console.Title) of current instance.
     */
    public final boolean ShowReference = true;
    /**
     * The IOServer for this SuitHost
     */
    public final IOServer IO;

    /**
     * Default IOServer, using stdin, stdout, stderr.
     */
    public static final IOServer GeneralIO = new IOServer();

    /**
     * The prompt in Console.
     */
    public String Prompt;

    /**
     * Current Instance's SuitObject Container.
     */
    public SuitObject Current;

    /**
     * Current BicServer's SuitObject Container.
     */
    public final SuitObject BicServer;

    /**
     * Current Instance
     */
    public Object WorkInstance()
    {
        return Current.Instance();
    }

    /**
     * Current Instance's type.
     */
    public Class<?> WorkType()
    {
        return Current.Instance().getClass();
    }

    /**
     * Use TraceBack, or just throw Exceptions.
     */
    public final boolean UseTraceBack = true;
    /**
     * If show that a command has been executed.
     */
    public final boolean ShowDone = false;

    /**
     * If this SuitHost runs like a shell that will not exit UNLESS user input exit command.
     */
    public final boolean ShellMode = false;

    private static String[] SplitCommandLine(String commandLine)
    {
        if (commandLine == null || Objects.equals(commandLine, "")) return null;
        String submit;
        List<String> l = new ArrayList<>();
        boolean separating = false;
        boolean separationPrefix = false;
        char separationCharacter = '"';
        int left = 0;
        int right = 0;
        for (; right < commandLine.length(); right++)
            switch (commandLine.charAt(right))
            {
                case '"':
                    if (separationPrefix) continue;
                    if (separating && separationCharacter == '"')
                    {
                        l.add(commandLine.substring(left, right));
                        left = right + 1;
                    }
                    else if (!separating)
                    {
                        separating = true;
                        separationCharacter = '"';
                        left = right + 1;
                    }

                    break;
                case '\'':
                    if (separationPrefix) continue;
                    if (separating && separationCharacter == '\'')
                    {
                        l.add(commandLine.substring(left, right));
                        left = right + 1;
                    }
                    else if (!separating)
                    {
                        separating = true;
                        separationCharacter = '\'';
                        left = right + 1;
                    }

                    break;
                case ' ':
                    submit = commandLine.substring(left, right);
                    if (!submit.equals(""))
                        l.add(submit);
                    left = right + 1;
                    separationPrefix = false;
                    break;
                default:
                    if (!separating) separationPrefix = true;
                    break;
            }

        submit = commandLine.substring(left, right);
        if (!submit.equals(""))
            l.add(submit);
        return l.toArray(new String[0]);
    }

    /**
     * Initialize the current instance, if it is a SuitClient, or implements IIOInteractive.
     */
    public void WorkInstanceInit()
    {
        if (WorkInstance() instanceof IOInteractive)
        {
            ((IOInteractive) WorkInstance()).SetIO(IO);
        }

    }

    @SuppressWarnings("ConstantConditions")
    private void NotifyAllOk()
    {
        if (UseTraceBack && ShowDone) IO.WriteLine(Lang.Done, OutputType.AllOk);
    }


    private void NotifyError(String errorDescription) throws Exception
    {
        if (UseTraceBack) IO.WriteLine(errorDescription, OutputType.Error);
        else throw new Exception(errorDescription);
    }

    private void UpdatePrompt(String prompt)
    {

        if (Objects.equals(prompt, "") && WorkInstance() != null)
        {
            if (WorkType().getAnnotation(SuitInfo.class) != null)
            {
                Prompt = WorkType().getAnnotation(SuitInfo.class).value();
            }
            else
            {
                if (WorkInstance() instanceof InfoProvider)
                {
                    Prompt = ((InfoProvider) WorkInstance()).Text();
                }
                else
                {
                    Prompt = WorkType().getName();
                }

            }

        }
        else
        {
            Prompt = prompt;
            IO.WriteLine("B4");//DEBUG
        }

        if (!ShowReference || InstanceNameString.size() <= 0) return;
        StringBuilder sb = new StringBuilder();
        sb.append(Prompt);
        sb.append('[');
        sb.append(InstanceNameString.get(0));
        if (InstanceNameString.size() > 1)
            for (int i = 1; i < InstanceNameString.size(); i++)
                sb.append(".").append(InstanceNameString.get(i));
        sb.append(']');
        Prompt = sb.toString();
    }


    private TraceBack RunBuildInCommand(String[] cmdList) throws IllegalAccessException, InvocationTargetException, InstantiationException
    {
        if (cmdList == null) return InvalidCommand;
        return BicServer.Execute(cmdList).First;
    }

    private TraceBack RunObject(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException
    {
        Tuple<TraceBack,Object>t=Current.Execute(args);
        if(t.First.equals(AllOk)){
            IO.WriteLine(Arrays.asList(
                    new Tuple<>(Lang.ReturnValue,IO.ColorSetting.PromptColor),
                    new Tuple<>(t.Second.toString(),null)
            ));
        }
        return t.First;
    }


    /**
     * Run a Mobile Suit with Prompt.
     *
     * @param prompt The prompt.
     * @return 0, is All ok.
     */
    public int Run(String prompt) throws Exception
    {
        UpdatePrompt(prompt);
        for (; ; )
        {
            if (!IO.IsInputRedirected()) IO.Write(Prompt + '>', OutputType.Prompt);
            TraceBack traceBack = RunCommand(prompt, IO.ReadLine());
            switch (traceBack)
            {
                case OnExit:
                    return 0;
                case AllOk:
                    NotifyAllOk();
                    break;
                case ObjectNotFound:
                    NotifyError(Lang.ObjectNotFound);
                    break;
                case MemberNotFound:
                    NotifyError(Lang.MemberNotFound);
                    break;
                case InvalidCommand:
                default:
                    NotifyError(Lang.InvalidCommand);
                    break;
            }
        }
    }


    private TraceBack RunCommand(String prompt, String cmd)
    {
        if ((cmd == null || cmd.equals("")) && IO.IsInputRedirected() && ShellMode)
        {
            IO.ResetInput();
            return AllOk;
        }

        if (cmd == null || cmd.equals("")) return AllOk;
        TraceBack traceBack;
        String[] args = SplitCommandLine(cmd);
        if (args == null) return InvalidCommand;
        try
        {
            if (cmd.charAt(0) == '@')
            {
                args[0] = args[0].substring(1);
                traceBack = RunBuildInCommand(args);
            }

            traceBack = RunObject(args);
            if (traceBack == ObjectNotFound) traceBack = RunBuildInCommand(args);
        }
        catch (Exception e)
        {
            IO.Error.println(e.toString());
            traceBack = InvalidCommand;
        }

        UpdatePrompt(prompt);
        return traceBack;
    }

    /**
     * Run a Mobile Suit with default Prompt.
     *
     * @return 0, is All ok .
     */

    public int Run() throws Exception
    {
        return Run("");
    }
}
