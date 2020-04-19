package PlasticMetal.JMobileSuitLite;

import PlasticMetal.JMobileSuitLite.IO.ConsoleColor;
import PlasticMetal.JMobileSuitLite.IO.OutputType;
import PlasticMetal.JMobileSuitLite.ObjectModel.Annotions.SuitAlias;
import PlasticMetal.JMobileSuitLite.ObjectModel.Annotions.SuitInfo;
import PlasticMetal.JMobileSuitLite.ObjectModel.Members.MemberType;
import PlasticMetal.JMobileSuitLite.ObjectModel.Members.SuitObjectMember;
import PlasticMetal.JMobileSuitLite.ObjectModel.SuitObject;
import PlasticMetal.JMobileSuitLite.ObjectModel.Tuple;

import java.util.Arrays;

/**
 * Built-In-Command Server. May be Override if necessary.
 */
@SuppressWarnings("unused")
public class SuitBuildInCommandServer
{
    /**
     * Initialize a BicServer with the given SuitHost.
     *
     * @param host The given SuitHost.
     */

    public SuitBuildInCommandServer(SuitHost host)
    {
        _host = host;
    }

    /**
     * Host.
     */
    protected final SuitHost _host;



    /**
     * _host's current SuitObject.
     */
    protected SuitObject _current;


    /**
     * Show Members of the Current SuitObject
     *
     * @param args args
     * @return Command status
     */
    @SuitAlias("Ls")
    @SuitInfo("Show Members of the Current SuitObject")
    public TraceBack List(String[] args)
    {
        if (_host.Current == null) return TraceBack.InvalidCommand;
        _host.IO.WriteLine(Lang.Members, OutputType.ListTitle);
        ListMembers(_host.Current);
        _host.IO.WriteLine();

        _host.IO.WriteLine(Arrays.asList(
                new Tuple<>(Lang.ViewBic, null),
                new Tuple<>("@Help", ConsoleColor.Cyan),
                new Tuple<>("'", null))
                , OutputType.AllOk);
        return TraceBack.AllOk;
    }


    /**
     * Exit MobileSuit
     *
     * @param args args
     * @return Command status
     */

    @SuitInfo("Exit MobileSuit")
    public TraceBack Exit(String[] args)
    {
        return TraceBack.OnExit;
    }

    /**
     * Show Help of MobileSuit
     *
     * @param args args
     * @return Command status
     */

    @SuitInfo("Show Help of MobileSuit")
    public TraceBack Help(String[] args)
    {
        _host.IO.WriteLine(Lang.Bic, OutputType.ListTitle);
        ListMembers(_host.BicServer);
        _host.IO.WriteLine();
        _host.IO.WriteLine(Arrays.asList(

                new Tuple<>(Lang.BicExp1, null),
                new Tuple<>("@", ConsoleColor.Cyan),
                new Tuple<>(Lang.BicExp2, null)
        ), OutputType.AllOk);
        return TraceBack.AllOk;
    }

    /**
     * List members of a SuitObject
     * @param obj The SuitObject, Maybe this BicServer.
     */

    protected void ListMembers(SuitObject obj)
    {
        _host.IO.AppendWriteLinePrefix();

        for (Tuple<String, SuitObjectMember> t: obj)
        {
            String name=t.First;
            SuitObjectMember member=t.Second;
            String infoColor= member.Type().equals(MemberType.MethodWithInfo)?ConsoleColor.Blue:ConsoleColor.DarkBlue;
            char lChar=member.Type().equals(MemberType.MethodWithInfo)?'[':'(';
            char rChar=member.Type().equals(MemberType.MethodWithInfo)?']':')';

            StringBuilder aliasesExpression = new StringBuilder();
            for(String alias : member.Aliases()) aliasesExpression.append("/").append(alias);
            _host.IO.WriteLine(Arrays.asList(

                new Tuple<>(name, null),
                    new Tuple<>(aliasesExpression.toString(), ConsoleColor.DarkYellow),
                    new Tuple<>(lChar+member.Information()+rChar, infoColor)
            ));
        }

        _host.IO.SubtractWriteLinePrefix();
    }


}
