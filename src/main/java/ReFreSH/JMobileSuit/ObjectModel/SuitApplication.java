package ReFreSH.JMobileSuit.ObjectModel;

import ReFreSH.JMobileSuit.ObjectModel.Annotions.SuitIgnore;
import ReFreSH.JMobileSuit.SuitHost;

public class SuitApplication extends SuitClient {
    /**
     * start a Application
     *
     * @throws Exception exceptions thrown by Mobile Suit
     */
    @SuitIgnore
    public void start() throws Exception {
        new SuitHost(this).Run();
    }
}
