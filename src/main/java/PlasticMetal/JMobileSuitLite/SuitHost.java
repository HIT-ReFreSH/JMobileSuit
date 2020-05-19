package PlasticMetal.JMobileSuitLite;

import PlasticMetal.JMobileSuitLite.Diagnostics.SuitLogger;
import PlasticMetal.JMobileSuitLite.IO.IOServer;
import PlasticMetal.JMobileSuitLite.IO.OutputType;
import PlasticMetal.JMobileSuitLite.IO.PromptServer;
import PlasticMetal.JMobileSuitLite.ObjectModel.Annotions.SuitInfo;
import PlasticMetal.JMobileSuitLite.ObjectModel.IOInteractive;
import PlasticMetal.JMobileSuitLite.ObjectModel.InfoProvider;
import PlasticMetal.JMobileSuitLite.ObjectModel.SuitObject;
import PlasticMetal.Jarvis.ObjectModel.Tuple;

import static PlasticMetal.JMobileSuitLite.LangResourceBundle.Lang;

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
    public SuitHost(Object instance) throws Exception

    {
        this(instance, SuitConfiguration.getInstance());
        Current = new SuitObject(instance);
        WorkInstanceInit();
    }

    /**
     * Initialize a SuitHost with given BicServer, IOServer, an instance.
     *
     * @param configuration Configuration
     */
    public SuitHost(Object instance, SuitConfiguration configuration) throws Exception

    {
        IO = configuration.IO() == null ? IOServer.GeneralIO : configuration.IO();
        Configuration=configuration;
        configuration.InitializeBuildInCommandServer(this);
        BicServer = new SuitObject(configuration.BuildInCommandServer() == null ?
                new BuildInCommandServer(this) : configuration.BuildInCommandServer());
        Prompt = configuration.Prompt();
        Logger = configuration.Logger();
        Current = new SuitObject(instance);
        WorkInstanceInit();

    }

    public final SuitConfiguration Configuration;

    public final SuitLogger Logger;

    /**
     * Initialize a SuitHost with general BicServer, IOServer, a type.
     *
     * @param type The type for Mobile Suit to drive.
     */
    public SuitHost(Class<?> type) throws Exception

    {

        this(type.newInstance());
    }

    /**
     * Initialize a SuitHost with given BicServer, IOServer, a type.
     *
     * @param type          The type for Mobile Suit to drive.
     * @param configuration Configuration
     */
    public SuitHost(Class<?> type, SuitConfiguration configuration) throws Exception

    {
        this(type.newInstance(), configuration);
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
    private boolean _showReference = true;

    /**
     * set If the prompt contains the reference (For example, System.Console.Title) of current instance.
     *
     * @param value If the prompt contains the reference (For example, System.Console.Title) of current instance.
     */
    public void SetShowReference(boolean value)
    {
        _showReference = value;
    }

    /**
     * get If the prompt contains the reference (For example, System.Console.Title) of current instance.
     *
     * @return if the prompt contains the reference (For example, System.Console.Title) of current instance.
     */
    public boolean GetShowReference()
    {
        return _showReference;
    }

    /**
     * The IOServer for this SuitHost
     */
    public final IOServer IO;


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
     * get Use TraceBack, or just throw Exceptions.
     *
     * @return Use TraceBack, or just throw Exceptions.
     */
    public boolean GetUseTraceBack()
    {
        return _useTraceBack;
    }

    /**
     * set Use TraceBack, or just throw Exceptions.
     *
     * @param _useTraceBack Use TraceBack, or just throw Exceptions.
     */
    public void SetUseTraceBack(boolean _useTraceBack)
    {
        this._useTraceBack = _useTraceBack;
    }

    /**
     * Use TraceBack, or just throw Exceptions.
     */
    private boolean _useTraceBack = true;

    /**
     * get If show that a command has been executed.
     *
     * @return If show that a command has been executed.
     */
    public boolean GetShowDone()
    {
        return _showDone;
    }

    /**
     * set If show that a command has been executed.
     *
     * @param _showDone If show that a command has been executed.
     */
    public void SetShowDone(boolean _showDone)
    {
        this._showDone = _showDone;
    }


    private boolean _showDone = false;

    /**
     * If this SuitHost runs like a shell that will not exit UNLESS user input exit command.
     */
    private final boolean ShellMode = false;

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
        if (_useTraceBack && _showDone) IO.WriteLine(Lang.Done, OutputType.AllOk);
    }


    private void NotifyError(String errorDescription) throws Exception
    {
        if (_useTraceBack) IO.WriteLine(errorDescription + "!", OutputType.Error);
        else throw new Exception(errorDescription);
    }

    private String UpdatePrompt(String prompt)
    {
        String prompt_;
        if (Objects.equals(prompt, "") && WorkInstance() != null)
        {
            if (WorkType().getAnnotation(SuitInfo.class) != null)
            {
                prompt_ = InfoProvider.getInfo(WorkType().getAnnotation(SuitInfo.class));
            }
            else
            {
                if (WorkInstance() instanceof InfoProvider)
                {
                    prompt_ = ((InfoProvider) WorkInstance()).Text();
                }
                else
                {
                    prompt_ = WorkType().getName();
                }

            }

        }
        else
        {
            prompt_ = prompt;
        }

        if (!_showReference || InstanceNameString.size() <= 0) return prompt_;
        StringBuilder sb = new StringBuilder();
        sb.append(prompt_);
        sb.append('[');
        sb.append(InstanceNameString.get(0));
        if (InstanceNameString.size() > 1)
            for (int i = 1; i < InstanceNameString.size(); i++)
                sb.append(".").append(InstanceNameString.get(i));
        sb.append(']');
        return sb.toString();
    }

    /**
     * The prompt server for mobile suit
     */
    public PromptServer Prompt;

    private String _returnValue;

    public boolean ShowReturnValue;

    private TraceBack RunBuildInCommand(String[] cmdList) //throws IllegalAccessException, InvocationTargetException, InstantiationException
    {
        if (cmdList == null)
        {
            Logger.LogTraceBack(InvalidCommand);
            return InvalidCommand;
        }
        TraceBack tb = BicServer.Execute(cmdList).First;
        Logger.LogTraceBack(tb);
        return tb;
    }

    private Tuple<TraceBack, Object> RunObject(String[] args) //throws IllegalAccessException, InvocationTargetException, InstantiationException
    {
        Tuple<TraceBack, Object> t = Current.Execute(args);
        if (t.Second != null && t.First.equals(AllOk))
        {
            String retVal = t.Second.toString();
            if (!retVal.equals("")) _returnValue = retVal;
            if (ShowReturnValue) IO.WriteLine(Arrays.asList(
                    new Tuple<>(Lang.ReturnValue + " > ", IO.ColorSetting.PromptColor),
                    new Tuple<>(t.Second.toString(), null)
            ));

        }
        if (t.Second == null) Logger.LogTraceBack(t.First);
        else Logger.LogTraceBack(t.First, t.Second);
        return t;
    }


    /**
     * Run a Mobile Suit with Prompt.
     *
     * @param prompt The prompt.
     * @return 0, is All ok.
     */
    public int Run(String prompt) throws Exception
    {
        Prompt.Update("", UpdatePrompt(prompt), AllOk);
        for (; ; )
        {
            if (!IO.IsInputRedirected()) Prompt.Print();
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
                case AppException:
                    NotifyError(Lang.ApplicationException);
                    break;
                case Prompt:
                    continue;
                case InvalidCommand:
                default:
                    NotifyError(Lang.InvalidCommand);
                    break;
            }
        }
    }

    /**
     * Run a Mobile Suit command with Prompt="".
     *
     * @return AllOK, is All ok.
     */
    public TraceBack RunCommand(String cmd)
    {
        return RunCommand("", cmd);
    }

    /**
     * Run a Mobile Suit command with Prompt.
     *
     * @param prompt The prompt.
     * @return AllOK, is All ok.
     */
    public TraceBack RunCommand(String prompt, String cmd)
    {

        if ((cmd == null || cmd.equals("")) && IO.IsInputRedirected() && ShellMode)
        {
            IO.ResetInput();
            return AllOk;
        }

        if (cmd == null || cmd.equals("")) return AllOk;
        Logger.LogCommand(cmd);
        TraceBack traceBack;
        String[] args = SplitCommandLine(cmd);
        if (args == null) return InvalidCommand;
        try
        {
            if (cmd.charAt(0) == '#') return TraceBack.AllOk;//Comment
            if (cmd.charAt(0) == '@')
            {
                args[0] = args[0].substring(1);
                traceBack = RunBuildInCommand(args);
            }
            else
            {
                Tuple<TraceBack, Object> t = RunObject(args);
                traceBack = t.First;
                if (traceBack == ObjectNotFound && !(t.Second instanceof TraceBack))
                    traceBack = RunBuildInCommand(args);
            }


        }
        catch (Exception e)
        {
            IO.Error.println(e.toString());
            Logger.LogException(e);
            traceBack = InvalidCommand;
        }
        Prompt.Update(_returnValue, UpdatePrompt(prompt), traceBack);
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
