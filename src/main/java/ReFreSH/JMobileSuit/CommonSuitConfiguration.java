package ReFreSH.JMobileSuit;


import ReFreSH.JMobileSuit.IO.ColorSetting;
import ReFreSH.JMobileSuit.IO.IOServer;
import ReFreSH.JMobileSuit.IO.PromptServer;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;

public class CommonSuitConfiguration implements SuitConfiguration {
    private final IOServer _io;
    private final Class<?> _buildInCommandServerType;
    private final PromptServer _prompt;
    private final ColorSetting _colorSetting;
    private final Logger logger;
    private BuildInCommandServer _buildInCommandServer;

    /// <summary>
    /// Initialize a configuration
    /// </summary>
    /// <param name="buildInCommandServerType">type of builtInCommandServerType</param>
    /// <param name="io">io server</param>
    /// <param name="promptServer">prompt server</param>
    /// <param name="colorSetting">color setting </param>
    public CommonSuitConfiguration(Class<? extends BuildInCommandServer> buildInCommandServerType, IOServer io, PromptServer promptServer, ColorSetting colorSetting, Logger logger) {
        _buildInCommandServerType = buildInCommandServerType;
        _io = io;
        _prompt = promptServer;
        _colorSetting = colorSetting;
        this.logger = logger;
    }


    public Class<?> BuildInCommandServerType() {
        return _buildInCommandServerType;
    }


    public void InitializeBuildInCommandServer(SuitHost host)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        _buildInCommandServer = (BuildInCommandServer) _buildInCommandServerType.getConstructor(SuitHost.class).newInstance(host);
    }

    public IOServer IO() {
        return _io;
    }

    public BuildInCommandServer BuildInCommandServer() {
        return _buildInCommandServer;
    }


    public PromptServer Prompt() {
        return _prompt;
    }

    public ColorSetting ColorSetting() {
        return _colorSetting;
    }

    /**
     * Logger of current mobile suit
     *
     * @return The logger
     */
    @Override
    public Logger Logger() {
        return logger;
    }
}

