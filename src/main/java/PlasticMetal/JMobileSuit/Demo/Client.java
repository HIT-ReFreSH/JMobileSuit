package PlasticMetal.JMobileSuit.Demo;

import PlasticMetal.JMobileSuitLite.ObjectModel.Annotions.SuitAlias;
import PlasticMetal.JMobileSuitLite.ObjectModel.Annotions.SuitInfo;
import PlasticMetal.JMobileSuitLite.ObjectModel.Interfaces.DynamicParameter;
import PlasticMetal.JMobileSuitLite.ObjectModel.SuitClient;
import PlasticMetal.JMobileSuitLite.SuitHost;

@SuitInfo("Demo")
public class Client extends SuitClient
{
    @SuitAlias("H")
    @SuitInfo("hello command")
    public void Hello()
    {
        IO().WriteLine("Hello! MobileSuit!");
    }

    public String Bye(String name)
    {
        IO().WriteLine("Bye!" + name);
        return "bye";
    }

    public static void main(String[] args) throws Exception
    {
        new SuitHost(Client.class).Run();
    }
    @SuitAlias("GM")
    public void GoodMorning(GoodMorningParameter arg){
        IO().WriteLine("Good morning,"+arg.name);
    }

    @SuitAlias("GM2")
    public void GoodMorning2(String arg, GoodMorningParameter arg1){
        IO().WriteLine("Good moring, "+arg+" and "+ arg1.name);
    }

    @SuitAlias("GE")
    public void GoodEvening(String[] arg){

        IO().WriteLine("Good Evening, "+(arg.length>=1?arg[0]:""));
    }

    @SuitAlias("GE2")
    public void GoodEvening2(String arg0,String[] arg){

        IO().WriteLine("Good Evening, "+arg0+(arg.length>=1?" and "+arg[0]:""));
    }

    public static class GoodMorningParameter implements DynamicParameter{
        public String name="foo";

        /**
         * Parse this Parameter from String[].
         *
         * @param options String[] to parse from.
         * @return Whether the parsing is successful
         */
        @Override
        public boolean Parse(String[] options)
        {
            if(options.length==1){
                name=options[0];
                return true;
            }else return options.length==0;

        }
    }
}
