package PlasticMetal.JMobileSuitLite;


import PlasticMetal.JMobileSuitLite.IO.ColorSetting;
import PlasticMetal.JMobileSuitLite.IO.IOServer;
import PlasticMetal.JMobileSuitLite.IO.PromptServer;

import java.lang.reflect.InvocationTargetException;

public class CommonSuitConfiguration implements SuitConfiguration
{
    private final IOServer _io;
    private final Class<?> _buildInCommandServerType;
    private final PromptServer _prompt;
    private final ColorSetting _colorSetting;
    private BuildInCommandServer _buildInCommandServer;

    /// <summary>
    /// Initialize a configuration
    /// </summary>
    /// <param name="buildInCommandServerType">type of builtInCommandServerType</param>
    /// <param name="io">io server</param>
    /// <param name="promptServer">prompt server</param>
    /// <param name="colorSetting">color setting </param>
    public CommonSuitConfiguration(Class<?> buildInCommandServerType, IOServer io, PromptServer promptServer, ColorSetting colorSetting)
    {
        _buildInCommandServerType = buildInCommandServerType;
        _io = io;
        _prompt = promptServer;
        _colorSetting = colorSetting;
    }


    public Class<?> BuildInCommandServerType()
    {
        return _buildInCommandServerType;
    }


    public void InitializeBuildInCommandServer(SuitHost host)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
        _buildInCommandServer=(BuildInCommandServer) _buildInCommandServerType.getConstructor(SuitHost.class).newInstance(host);
    }

    public IOServer IO(){return _io;}

    public BuildInCommandServer BuildInCommandServer(){return _buildInCommandServer;}


    public PromptServer Prompt()
    {
        return _prompt;
    }

    public ColorSetting ColorSetting()
    {
        return _colorSetting;
    }
}
