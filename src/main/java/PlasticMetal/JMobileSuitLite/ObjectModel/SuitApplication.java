package PlasticMetal.JMobileSuitLite.ObjectModel;

import PlasticMetal.JMobileSuitLite.ObjectModel.Annotions.SuitIgnore;
import PlasticMetal.JMobileSuitLite.SuitHost;

public class SuitApplication extends SuitClient
{
    /**
     * Start a Application
     * @throws Exception exceptions thrown by Mobile Suit
     */
    @SuitIgnore
    public void Start() throws Exception
    {
        new SuitHost(this).Run();
    }
}
