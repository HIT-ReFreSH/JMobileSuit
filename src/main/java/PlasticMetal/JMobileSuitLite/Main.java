package PlasticMetal.JMobileSuitLite;

import PlasticMetal.JMobileSuitLite.ObjectModel.Annotions.SuitAlias;
import PlasticMetal.JMobileSuitLite.ObjectModel.Annotions.SuitIgnore;
import PlasticMetal.JMobileSuitLite.ObjectModel.Annotions.SuitInfo;
import PlasticMetal.JMobileSuitLite.ObjectModel.SuitClient;

@SuitInfo("Main")
public class Main extends SuitClient
{
    public static void main(String[] args) throws Exception
    {
        new SuitHost(new Main()).Run();
    }

    @SuitAlias("A1")
    @SuitAlias("A2")
    @SuitInfo("Test")
    public void Test()
    {
        IO().WriteLine("Hello, MobileSuitLite");

    }

    @SuitInfo("Test")
    @SuitIgnore

    public void Test2()
    {

    }

    @SuitInfo("Test2")
    @SuitAlias("A1")
    @SuitAlias("A2")
    public void Test3(String arg)
    {
        IO().WriteLine(arg + "nmsl");
    }
}
