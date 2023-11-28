package ReFreSH.JMobileSuit.Demo;

import ReFreSH.JMobileSuit.NeuesProjekt.PowerLineThemedPromptServer;
import ReFreSH.JMobileSuit.ObjectModel.Annotions.SuitAlias;
import ReFreSH.JMobileSuit.ObjectModel.SuitConfigurator;
import ReFreSH.JMobileSuit.SuitHost;
import org.apache.logging.log4j.LogManager;

public class DiagnosticsDemo extends Client {
    public static void main(String[] args) throws Exception {
        SuitHost suitHost = new SuitHost(DiagnosticsDemo.class,
                SuitConfigurator.ofDefault().use(PowerLineThemedPromptServer.class)
                        .use(LogManager.getLogger(DiagnosticsDemo.class))
                        .getConfiguration());
        //suitHost.SetUseTraceBack(false);
        suitHost.Run();
    }

    @SuitAlias("ET")
    public void ExceptionTest() throws Exception {
        throw new Exception("Test");
    }
}
