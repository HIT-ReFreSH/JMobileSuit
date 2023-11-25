package PlasticMetal.JMobileSuitLite;

import PlasticMetal.JMobileSuitLite.IO.ColorSetting;
import PlasticMetal.JMobileSuitLite.IO.IOServer;
import PlasticMetal.JMobileSuitLite.IO.PromptServer;
import PlasticMetal.JMobileSuitLite.ObjectModel.SuitConfigurator;
import org.apache.logging.log4j.Logger;


import java.lang.reflect.InvocationTargetException;

/**
 * represent a configuration for Mobile Suit
 */
public interface SuitConfiguration
{


    /**
     * Type of BuildInCommandServer
     */
    Class<?> BuildInCommandServerType();

    /**
     * Initialize the BuildInCommandServer with BuiltInCommandServerType and given host
     *
     * @param host host for BuildInCommandServer
     */

    void InitializeBuildInCommandServer(SuitHost host)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;

    /**
     * IOServer for the Mobile Suit
     */
    IOServer IO();

    /**
     * BuildInCommandServer for the Mobile Suit
     */
    BuildInCommandServer BuildInCommandServer();

    /**
     * Prompt for the Mobile Suit
     */
    PromptServer Prompt();

    /**
     * Color for the Mobile Suit
     */
    ColorSetting ColorSetting();

    /**
     * Logger of current mobile suit
     * @return The logger
     */
    Logger Logger();

    /**
     *get a default configuration of Mobile Suit
     */

    static SuitConfiguration getInstance(){
        return SuitConfigurator.ofDefault().getConfiguration();
    }

}
