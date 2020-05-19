package PlasticMetal.JMobileSuitLite.ObjectModel;

import PlasticMetal.JMobileSuitLite.BuildInCommandServer;
import PlasticMetal.JMobileSuitLite.CommonSuitConfiguration;
import PlasticMetal.JMobileSuitLite.Diagnostics.SuitLogger;
import PlasticMetal.JMobileSuitLite.IO.ColorSetting;
import PlasticMetal.JMobileSuitLite.IO.CommonPromptServer;
import PlasticMetal.JMobileSuitLite.IO.IOServer;
import PlasticMetal.JMobileSuitLite.IO.PromptServer;
import PlasticMetal.JMobileSuitLite.SuitConfiguration;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class SuitConfigurator
{
    private SuitConfigurator(){

    }

    private static boolean hasInterface(Class<?> c, Class<?> i)
    {
        while (!c.equals(Object.class))
        {
            if (Arrays.asList(c.getInterfaces()).contains(i)) return true;
            c = c.getSuperclass();
        }
        return false;
    }

    private static boolean hasBaseClass(Class<?> c, Class<?> b)
    {
        if (b.equals(Object.class)) return true;
        while (!c.equals(Object.class))
        {
            if (c.equals(b)) return true;
            c = c.getSuperclass();
        }
        return false;
    }


    public Class<? extends BuildInCommandServer> BuildInCommandServerType = BuildInCommandServer.class;
    public ColorSetting ColorSetting = PlasticMetal.JMobileSuitLite.IO.ColorSetting.getInstance();
    public Class<? extends PromptServer> PromptServerType = CommonPromptServer.class;
    public SuitLogger Logger = SuitLogger.ofTemp();
    public Class<? extends IOServer> IOServerType = IOServer.class;
    public Class<? extends SuitConfiguration> ConfigurationType = CommonSuitConfiguration.class;

    public SuitConfigurator Use(Class<?> s)
    {
        if (hasBaseClass(s, IOServer.class))
            this.IOServerType = (Class<? extends IOServer>) s;
        else if (hasInterface(s, PromptServer.class))
            this.PromptServerType = (Class<? extends PromptServer>) s;
        else if (hasBaseClass(s, BuildInCommandServer.class))
            this.BuildInCommandServerType = (Class<? extends BuildInCommandServer>) s;
        else if (hasInterface(s, SuitConfiguration.class))
            this.ConfigurationType = (Class<? extends SuitConfiguration>) s;


        return this;
    }

    public SuitConfigurator Use(SuitLogger s)
    {
        this.Logger = s;
        return this;
    }

    public SuitConfigurator Use(ColorSetting s)
    {
        this.ColorSetting = s;
        return this;
    }

    public SuitConfiguration getConfiguration()// throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        try
        {
            PromptServer promptServer = PromptServerType.newInstance();
            IOServer ioServer = IOServerType.getConstructor(PromptServer.class, SuitLogger.class, ColorSetting.class)
                    .newInstance(promptServer, Logger, ColorSetting);
            promptServer.SetIO(ioServer);
            return ConfigurationType.getConstructor(Class.class, IOServer.class, PromptServer.class, ColorSetting.class, SuitLogger.class).newInstance(
                    BuildInCommandServerType,
                    ioServer,
                    promptServer,
                    ColorSetting,
                    Logger
            );
        }
        catch (Exception e)
        {
            return null;
        }

    }

    public static SuitConfigurator of(Class<?> s)
    {
        SuitConfigurator r = new SuitConfigurator();
        if (hasBaseClass(s, IOServer.class))
            r.IOServerType = (Class<? extends IOServer>) s;
        else if (hasInterface(s, PromptServer.class))
            r.PromptServerType = (Class<? extends PromptServer>) s;
        else if (hasBaseClass(s, BuildInCommandServer.class))
            r.BuildInCommandServerType = (Class<? extends BuildInCommandServer>) s;
        else if (hasInterface(s, SuitConfiguration.class))
            r.ConfigurationType = (Class<? extends SuitConfiguration>) s;

        return r;
    }

    public static SuitConfigurator of(SuitLogger s)
    {
        SuitConfigurator r = new SuitConfigurator();
        r.Logger = s;
        return r;
    }

    public static SuitConfigurator ofDefault()
    {
        return new SuitConfigurator();
    }

    public static SuitConfigurator of(ColorSetting s)
    {
        SuitConfigurator r = new SuitConfigurator();
        r.ColorSetting = s;
        return r;
    }
}
