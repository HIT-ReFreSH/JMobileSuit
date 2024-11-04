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
    /// 初始化配置
    /// </summary>
    /// <param name="buildInCommandServerType">内置命令服务器的类型</param>
    /// <param name="io">IO 服务器</param>
    /// <param name="promptServer">提示服务器</param>
    /// <param name="colorSetting">颜色设置</param>
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
     * 当前移动套件的记录器
     *
     * @return 记录器
     */
    @Override
    public Logger Logger() {
        return logger;
    }
}

