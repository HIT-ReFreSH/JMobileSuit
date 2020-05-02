package PlasticMetal.JMobileSuitLite.IO;

import PlasticMetal.JMobileSuitLite.Lang;
import PlasticMetal.JMobileSuitLite.SuitConfiguration;
import PlasticMetal.JMobileSuitLite.TraceBack;

public class CommonPromptServer implements PromptServer
{
    /// <summary>
    /// IO server of this prompt server
    /// </summary>
    protected final IOServer IO;
    /// <summary>
    /// Color setting of this prompt server
    /// </summary>
    protected final ColorSetting Color;

    /// <summary>
    /// Initialize a prompt with GeneralIO
    /// </summary>
    public CommonPromptServer()
    {
        this(IOServer.GeneralIO, ColorSetting.getInstance());
    }

    /// <summary>
    /// Initialize a prompt with IO and color setting.
    /// </summary>
    /// <param name="io">io</param>
    /// <param name="colorSetting">color setting</param>
    protected CommonPromptServer(IOServer io, ColorSetting colorSetting)
    {
        IO = io;
        Color = colorSetting;
    }

    /// <summary>
    /// Initialize a prompt Server with given configuration
    /// </summary>
    /// <param name="configuration"></param>
    public CommonPromptServer(SuitConfiguration configuration)

    {
        this(configuration.IO(),
                configuration.ColorSetting());

    }

    /// <summary>
    /// return value from last update
    /// </summary>
    protected String LastReturnValue = "";

    /// <summary>
    /// Information from last update
    /// </summary>
    protected String LastInformation = "";

    /// <summary>
    ///TraceBack from last update
    /// </summary>
    protected TraceBack LastTraceBack;

    /// <summary>
    ///Information shows when TraceBack==Prompt from last update
    /// </summary>
    protected String LastPromptInformation = "";

    ///<inheritdoc/>
    public void Update(String returnValue, String information, TraceBack traceBack)
    {
        LastReturnValue = returnValue;
        LastInformation = information;
        LastTraceBack = traceBack;
    }

    /// <inheritdoc />
    public void Update(String returnValue, String information, TraceBack traceBack, String promptInformation)
    {
        Update(returnValue, information, traceBack);
        LastPromptInformation = promptInformation;

    }

    /// <inheritdoc />
    public void Print()
    {
        IO.Write(" "+LastInformation, OutputType.Prompt);
        if (LastTraceBack == TraceBack.Prompt)
        {
            IO.Write("["+LastPromptInformation+"]", OutputType.Prompt);
        }

        IO.Write(" > ", OutputType.Prompt);
    }
}
