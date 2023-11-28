package ReFreSH.JMobileSuit.IO;

import ReFreSH.JMobileSuit.LangResourceBundle;
import ReFreSH.JMobileSuit.SuitConfiguration;
import ReFreSH.JMobileSuit.TraceBack;

/**
 * A common implement of PromptServer
 */
public class CommonPromptServer implements PromptServer {
    protected final LangResourceBundle Lang;
    /**
     * Color setting of this prompt server
     */
    protected final ColorSetting Color;
    /**
     * IO server of this prompt server
     */
    protected IOServer IO;
    /**
     * return value from last update
     */
    protected String LastReturnValue = "";
    /**
     * Information from last update
     */
    protected String LastInformation = "";
    /**
     * TraceBack from last update
     */
    protected TraceBack LastTraceBack;
    /**
     * Information shows when TraceBack==Prompt from last update
     */
    protected String LastPromptInformation = "";

    /**
     * Initialize a prompt with GeneralIO
     */
    public CommonPromptServer() {
        this(IOServer.GeneralIO, ColorSetting.getInstance());
    }

    /**
     * Initialize a prompt with IO and color setting.
     *
     * @param colorSetting given color setting
     * @param io           given io server
     */

    protected CommonPromptServer(IOServer io, ColorSetting colorSetting) {
        IO = io;
        Color = colorSetting;
        Lang = new LangResourceBundle();
    }

    /**
     * Initialize a prompt Server with given configuration
     *
     * @param configuration given configuration
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
     * @param returnValue       return value of last command
     * @param information       information of current instance
     * @param traceBack         traceBack of last command
     * @param promptInformation information shows when traceBack==TraceBack.Prompt
     */
    public void Update(String returnValue, String information, TraceBack traceBack, String promptInformation) {
        Update(returnValue, information, traceBack);
        LastPromptInformation = promptInformation;

    }

    /**
     * print out the prompt
     */
    public void Print() {
        IO.Write(" " + LastInformation, OutputType.Prompt);
        if (LastTraceBack == TraceBack.Prompt) {
            IO.Write("[" + LastPromptInformation + "]", OutputType.Prompt);
        }

        IO.Write(" > ", OutputType.Prompt);
    }

    /**
     * Provides Interface for SuitHost to set ioServer
     *
     * @param io SuitHost's IOServer.
     */
    @Override
    public void setIO(IOServer io) {
        IO = io;
    }
}
