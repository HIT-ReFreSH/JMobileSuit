package PlasticMetal.JMobileSuit.Demo;

import PlasticMetal.JMobileSuitLite.Diagnostics.DiagnosticsBuildInCommandServer;
import PlasticMetal.JMobileSuitLite.Diagnostics.SuitLogger;
import PlasticMetal.JMobileSuitLite.NeuesProjekt.PowerLineThemedPromptServer;
import PlasticMetal.JMobileSuitLite.ObjectModel.SuitConfigurator;
import PlasticMetal.JMobileSuitLite.SuitHost;

public class DiagnosticsDemo extends Client
{
    public static void main(String[] args)throws Exception
    {
         SuitHost suitHost=new SuitHost(DiagnosticsDemo.class,
                SuitConfigurator.ofDefault().Use(PowerLineThemedPromptServer.class)
                        .Use(DiagnosticsBuildInCommandServer.class)
                        .Use(SuitLogger.ofDirectory("D:\\"))
                        .getConfiguration());
         //suitHost.SetUseTraceBack(false);
         suitHost.Run();
    }
}
