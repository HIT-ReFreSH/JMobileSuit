package PlasticMetal.JMobileSuit.Demo;

import PlasticMetal.JMobileSuitLite.NeuesProjekt.PowerLineThemedPromptServer;
import PlasticMetal.JMobileSuitLite.ObjectModel.Annotions.SuitAlias;
import PlasticMetal.JMobileSuitLite.ObjectModel.SuitConfigurator;
import PlasticMetal.JMobileSuitLite.SuitHost;
import org.apache.log4j.Logger;

public class DiagnosticsDemo extends Client
{
    public static void main(String[] args)throws Exception
    {
         SuitHost suitHost=new SuitHost(DiagnosticsDemo.class,
                SuitConfigurator.ofDefault().use(PowerLineThemedPromptServer.class)
                        .use(Logger.getLogger(DiagnosticsDemo.class).getClass())
                        .getConfiguration());
         //suitHost.SetUseTraceBack(false);
         suitHost.Run();
    }
    @SuitAlias("ET")
    public void ExceptionTest() throws Exception
    {
        throw new Exception("Test");
    }
}
