package ReFreSH.JMobileSuit;

import ReFreSH.JMobileSuit.IO.ColorSetting;
import ReFreSH.JMobileSuit.IO.IOServer;
import ReFreSH.JMobileSuit.IO.PromptServer;
import ReFreSH.JMobileSuit.ObjectModel.SuitConfigurator;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
/**
 * 表示移动套件的配置
 */
public interface SuitConfiguration {


    /**
     * 获取移动套件的默认配置
     */

    static SuitConfiguration getInstance() {
        return SuitConfigurator.ofDefault().getConfiguration();
    }

    /**
     * 内置命令服务器的类型
     */
    Class<?> BuildInCommandServerType();

    /**
     * 使用 BuiltInCommandServerType 和给定主机初始化 BuildInCommandServer
     *
     * @param host BuildInCommandServer 的主机
     */

    void InitializeBuildInCommandServer(SuitHost host)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;

    /**
     * 移动套件的 IO 服务器
     */
    IOServer IO();

    /**
     * 移动套件的内置命令服务器
     */
    BuildInCommandServer BuildInCommandServer();

    /**
     * 移动套件的提示服务器
     */
    PromptServer Prompt();

    /**
     * 移动套件的颜色设置
     */
    ColorSetting ColorSetting();

    /**
     * 当前移动套件的记录器
     *
     * @return 记录器
     */
    Logger Logger();

}
