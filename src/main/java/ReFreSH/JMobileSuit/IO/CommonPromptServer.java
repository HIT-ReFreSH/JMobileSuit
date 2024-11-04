package ReFreSH.JMobileSuit.IO;

import ReFreSH.JMobileSuit.LangResourceBundle;
import ReFreSH.JMobileSuit.SuitConfiguration;
import ReFreSH.JMobileSuit.TraceBack;

/**
 * PromptServer 的常见实现
 */
public class CommonPromptServer implements PromptServer {
    protected final LangResourceBundle Lang;
    /**
     * 此提示服务器的颜色设置
     */
    protected final ColorSetting Color;
    /**
     * 此提示服务器的 IO 服务器
     */
    protected IOServer IO;
    /**
     *返回上次的更新值
     */
    protected String LastReturnValue = "";
    /**
     * 上次更新的信息
     */
    protected String LastInformation = "";
    /**
     * 上次更新的返回路径
     */
    protected TraceBack LastTraceBack;
    /**
     * 当返回路径=上次更新的提示时显示信息
     */
    protected String LastPromptInformation = "";

    /**
     * 使用 GeneralIO 初始化提示
     */
    public CommonPromptServer() {
        this(IOServer.GeneralIO, ColorSetting.getInstance());
    }

    /**
     * 使用 IO 和颜色设置初始化提示。
     *
     * @param colorSetting 给定颜色设置
     * @param io           给定 io server
     */

    protected CommonPromptServer(IOServer io, ColorSetting colorSetting) {
        IO = io;
        Color = colorSetting;
        Lang = new LangResourceBundle();
    }

    /**
     * 使用给定配置初始化提示 Server
     *
     * @param configuration 给定配置
     */
    public CommonPromptServer(SuitConfiguration configuration) {
        this(configuration.IO(),
                configuration.ColorSetting());

    }

    public void Update(String returnValue, String information, TraceBack traceBack) {
        LastReturnValue = returnValue;
        LastInformation = information;
        LastTraceBack = traceBack;
    }

    /**
     * @param returnValue       返回上次命令的值
     * @param information       当前实例信息
     * @param traceBack         traceLast 命令的返回
     * @param promptInformation 当返回路径=上次更新的提示时显示信息
     */
    public void Update(String returnValue, String information, TraceBack traceBack, String promptInformation) {
        Update(returnValue, information, traceBack);
        LastPromptInformation = promptInformation;

    }

    /**
     * 打印出提示
     */
    public void Print() {
        IO.Write(" " + LastInformation, OutputType.Prompt);
        if (LastTraceBack == TraceBack.Prompt) {
            IO.Write("[" + LastPromptInformation + "]", OutputType.Prompt);
        }

        IO.Write(" > ", OutputType.Prompt);
    }

    /**
     * 为 SuitHost 提供设置 ioServer 的接口
     *
     * @param io SuitHost's IOServer.
     */
    @Override
    public void setIO(IOServer io) {
        IO = io;
    }
}
