package ReFreSH.JMobileSuit;

import ReFreSH.JMobileSuit.IO.IOServer;
import ReFreSH.JMobileSuit.IO.OutputType;
import ReFreSH.JMobileSuit.IO.PromptServer;
import ReFreSH.JMobileSuit.ObjectModel.Annotions.SuitInfo;
import ReFreSH.JMobileSuit.ObjectModel.IOInteractive;
import ReFreSH.JMobileSuit.ObjectModel.InfoProvider;
import ReFreSH.JMobileSuit.ObjectModel.SuitObject;
import ReFreSH.Jarvis.ObjectModel.Tuple;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static ReFreSH.JMobileSuit.LangResourceBundle.Lang;
import static ReFreSH.JMobileSuit.TraceBack.*;
import static ReFreSH.Jarvis.Common.CoalesceNull;

/**
 * A entity, which serves the shell functions of a mobile-suit program.
 */
public class SuitHost {
    public final SuitConfiguration Configuration;
    public final Logger logger;
    /**
     * String of Current Instance's Name.
     */
    public final List<String> InstanceNameString = new ArrayList<>();
    /**
     * Stack of Instance's Name Strings.
     */
    public final Stack<List<String>> InstanceNameStringStack = new Stack<>();
    /**
     * The IOServer for this SuitHost
     */
    public final IOServer IO;
    /**
     * Current BicServer's SuitObject Container.
     */
    public final SuitObject BicServer;
    /**
     * If this SuitHost runs like a shell that will not exit UNLESS user input exit command.
     */
    private final boolean ShellMode = false;
    /**
     * Stack of Instance, created in this Mobile Suit.
     */
    public Stack<SuitObject> InstanceStack = new Stack<>();
    /**
     * Current Instance's SuitObject Container.
     */
    public SuitObject Current;
    /**
     * The prompt server for mobile suit
     */
    public PromptServer Prompt;
    public boolean ShowReturnValue;
    /**
     * If the prompt contains the reference (For example, System.Console.Title) of current instance.
     */
    private boolean _showReference = true;
    /**
     * use TraceBack, or just throw Exceptions.
     */
    private boolean _useTraceBack = true;
    private boolean _showDone = false;
    private String _returnValue;

    /**
     * 使用通用 BicServer、IOServer 和一个实例初始化 SuitHost。
     *
     * @param instance The instance for Mobile Suit to drive.
     */
    public SuitHost(Object instance) throws Exception {
        this(instance, SuitConfiguration.getInstance());
        Current = new SuitObject(instance);
        WorkInstanceInit();
    }
    /**
     * 使用给定的 BicServer、IOServer 和一个实例初始化 SuitHost。
     *
     * @param configuration Configuration
     */
    public SuitHost(Object instance, SuitConfiguration configuration) throws Exception {
        IO = configuration.IO() == null ? IOServer.GeneralIO : configuration.IO();
        Configuration = configuration;
        configuration.InitializeBuildInCommandServer(this);
        BicServer = new SuitObject(configuration.BuildInCommandServer() == null ?
                new BuildInCommandServer(this) : configuration.BuildInCommandServer());
        Prompt = configuration.Prompt();
        logger = configuration.Logger();
        Current = new SuitObject(instance);
        WorkInstanceInit();

    }

    /**
     * 使用通用 BicServer、IOServer 和一个类型初始化 SuitHost。
     *
     * @param type The type for Mobile Suit to drive.
     */
    public SuitHost(Class<?> type) throws Exception {

        this(type.getConstructor().newInstance());
    }

    /**
     * 使用给定的 BicServer、IOServer 和一个类型初始化 SuitHost。
     *
     * @param type          The type for Mobile Suit to drive.
     * @param configuration Configuration
     */
    public SuitHost(Class<?> type, SuitConfiguration configuration) throws Exception {
        this(type.getConstructor().newInstance(), configuration);
    }

    private static String[] SplitCommandLine(String commandLine) {
        if (commandLine == null || Objects.equals(commandLine, "")) return null;
        String submit;
        List<String> l = new ArrayList<>();
        boolean separating = false;
        boolean separationPrefix = false;
        char separationCharacter = '"';
        int left = 0;
        int right = 0;
        for (; right < commandLine.length(); right++)
            switch (commandLine.charAt(right)) {
                case '"':
                    if (separationPrefix) continue;
                    if (separating && separationCharacter == '"') {
                        l.add(commandLine.substring(left, right));
                        left = right + 1;
                    } else if (!separating) {
                        separating = true;
                        separationCharacter = '"';
                        left = right + 1;
                    }

                    break;
                case '\'':
                    if (separationPrefix) continue;
                    if (separating && separationCharacter == '\'') {
                        l.add(commandLine.substring(left, right));
                        left = right + 1;
                    } else if (!separating) {
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
     * 设置提示是否包含当前实例的引用（例如，System.Console.Title）。
     *
     * @param value If the prompt contains the reference (For example, System.Console.Title) of current instance.
     */
    public void SetShowReference(boolean value) {
        _showReference = value;
    }

    /**
     * 获取提示是否包含当前实例的引用（例如，System.Console.Title）。
     *
     * @return if the prompt contains the reference (For example, System.Console.Title) of current instance.
     */
    public boolean GetShowReference() {
        return _showReference;
    }

    /**
     * Current Instance
     */
    public Object WorkInstance() {
        return Current.Instance();
    }

    /**
     * 当前实例
     */
    public Class<?> WorkType() {
        return Current.Instance().getClass();
    }

    /**
     * 获取使用 TraceBack，还是只是抛出异常。
     *
     * @return use TraceBack, or just throw Exceptions.
     */
    public boolean GetUseTraceBack() {
        return _useTraceBack;
    }

    /**
     * 设置使用 TraceBack，还是只是抛出异常。
     *
     * @param _useTraceBack use TraceBack, or just throw Exceptions.
     */
    public void SetUseTraceBack(boolean _useTraceBack) {
        this._useTraceBack = _useTraceBack;
    }

    /**
     * 获取是否显示命令已被执行。
     *
     * @return If show that a command has been executed.
     */
    public boolean GetShowDone() {
        return _showDone;
    }

    /**
     * 设置是否显示命令已被执行。
     *
     * @param _showDone If show that a command has been executed.
     */
    public void SetShowDone(boolean _showDone) {
        this._showDone = _showDone;
    }

    /**
     * 初始化当前实例，如果它是 SuitClient 或实现 IIOInteractive。
     */
    public void WorkInstanceInit() {
        if (WorkInstance() instanceof IOInteractive) {
            ((IOInteractive) WorkInstance()).setIO(IO);
        }

    }

    @SuppressWarnings("ConstantConditions")
    private void NotifyAllOk() {
        if (_useTraceBack && _showDone && (logger != null)) logger.trace(OutputType.AllOk);
    }

    private void NotifyError(String errorDescription) throws Exception {
        if (_useTraceBack) IO.WriteLine(errorDescription + "!", OutputType.Error);
        else throw new Exception(errorDescription);
    }

    private String UpdatePrompt(String prompt) {
        String prompt_;
        if (Objects.equals(prompt, "") && WorkInstance() != null) {
            if (WorkType().getAnnotation(SuitInfo.class) != null) {
                prompt_ = InfoProvider.getInfo(WorkType().getAnnotation(SuitInfo.class));
            } else {
                if (WorkInstance() instanceof InfoProvider) {
                    prompt_ = ((InfoProvider) WorkInstance()).text();
                } else {
                    prompt_ = WorkType().getName();
                }

            }

        } else {
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

    private TraceBack RunBuildInCommand(String[] cmdList) //throws IllegalAccessException, InvocationTargetException, InstantiationException
    {
        if (cmdList == null) {
            if (logger != null) {
                logger.trace("TraceBack:" + InvalidCommand);
            }
            return InvalidCommand;
        }
        Tuple<TraceBack, Object> ret = BicServer.execute(cmdList);
        TraceBack tb = ret.First;
        if (logger != null) {
            logger.trace("TraceBack:" + tb);
        }
        if (ret.Second instanceof Exception)
            logException((Exception) ret.Second);
        return tb;
    }

    private Tuple<TraceBack, Object> RunObject(String[] args) throws Exception //throws IllegalAccessException, InvocationTargetException, InstantiationException
    {
        Tuple<TraceBack, Object> t = Current.execute(args);
        if (t.Second != null && t.First.equals(AllOk)) {
            String retVal = t.Second.toString();
            if (!retVal.equals("")) _returnValue = retVal;
            if (ShowReturnValue) IO.WriteLine(Arrays.asList(
                    new Tuple<>(Lang.ReturnValue + " > ", IO.ColorSetting.PromptColor),
                    new Tuple<>(t.Second.toString(), null)
            ));

        }
        if (t.Second == null) {
            if (logger != null) {
                logger.trace("TraceBack:" + t.First);
            }
        } else {
            if (logger != null) {
                logger.trace("TraceBack:" + t.First.toString() + "(" + t.Second + ")");
            }
        }
        if (t.Second instanceof Exception) {
            logException((Exception) t.Second);
            if (t.First.equals(AppException)) {
                NotifyError(Lang.ApplicationException + ": " + ((Exception) t.Second).getMessage());
            }
        } else if (t.First.equals(AppException)) {
            NotifyError(Lang.ApplicationException);
        }
        return t;
    }


    /**
     * 运行一个 Mobile Suit并显示提示。
     *
     * @param prompt The prompt.
     * @return 0, is All ok.
     */
    public int Run(String prompt) throws Exception {
        Prompt.Update("", UpdatePrompt(prompt), AllOk);
        for (; ; ) {
            if (!IO.IsInputRedirected()) Prompt.Print();
            TraceBack traceBack = RunCommand(prompt, IO.ReadLine());
            switch (traceBack) {
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
                    continue;
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
     * 运行一个Mobile Suit命令，提示为空。
     *
     * @return AllOK, is All ok.
     */
    public TraceBack RunCommand(String cmd) {
        return RunCommand("", cmd);
    }

    /**
     * 运行一个Mobile Suit命令并显示提示。
     *
     * @param prompt The prompt.
     * @return AllOK, is All ok.
     */
    public TraceBack RunCommand(String prompt, String cmd) {

        if ((cmd == null || cmd.equals("")) && IO.IsInputRedirected() && ShellMode) {
            IO.ResetInput();
            return AllOk;
        }

        if (cmd == null || cmd.equals("")) return AllOk;
        if (this.logger != null) logger.info("Command:" + cmd);
        TraceBack traceBack;
        String[] args = SplitCommandLine(cmd);
        if (args == null) return InvalidCommand;
        try {
            if (cmd.charAt(0) == '#') return TraceBack.AllOk;//Comment
            if (cmd.charAt(0) == '@') {
                args[0] = args[0].substring(1);
                traceBack = RunBuildInCommand(args);
            } else {
                Tuple<TraceBack, Object> t = RunObject(args);
                traceBack = t.First;
                if (traceBack == ObjectNotFound && !(t.Second instanceof TraceBack))
                    traceBack = RunBuildInCommand(args);
            }


        } catch (Exception e) {
            IO.Error.println(e.toString());
            if (this.logger != null) logger.error(e);
            traceBack = InvalidCommand;
        }
        Prompt.Update(_returnValue, UpdatePrompt(prompt), traceBack);
        return traceBack;
    }

    /**
     * 运行一个Mobile Suit命令并显示默认提示.
     *
     * @return 0, is All ok .
     */
    public int Run() throws Exception {
        return Run("");
    }

    public void logException(Exception content) {
        StringBuilder stringBuilder = new StringBuilder(CoalesceNull(content.getMessage(), ""));
        for (StackTraceElement se : content.getStackTrace()
        ) {
            stringBuilder.append("\n\tAt ").append(se);
        }
        if (logger != null) logger.info(content.getClass().getName() + stringBuilder.toString());
    }
}
