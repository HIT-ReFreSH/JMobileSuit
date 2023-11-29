package PlasticMetal.JMobileSuitLite.Diagnostics;

import PlasticMetal.JMobileSuitLite.BuildInCommandServer;
import PlasticMetal.JMobileSuitLite.ObjectModel.Annotions.SuitInfo;
import PlasticMetal.JMobileSuitLite.SuitHost;
import PlasticMetal.JMobileSuitLite.TraceBack;

import java.util.Arrays;

public class DiagnosticsBuildInCommandServer extends BuildInCommandServer
{
    /**
     * Initialize a BicServer with the given SuitHost.
     *
     * @param host The given SuitHost.
     */
    public DiagnosticsBuildInCommandServer(SuitHost host)
    {
        super(host);
    }


    @SuitInfo(resourceBundleName = "LogDriver",value = "Server")
    public TraceBack Log(String[] args){
        try
        {
            SuitHost host= new SuitHost(new LogDriver(_host.Logger),_host.Configuration);
            if(args.length==0){
                host.Run();
                return TraceBack.AllOk;
            }
            else {
                StringBuilder stringBuilder=new StringBuilder();
                for (String arg : args)
                {
                    stringBuilder.append(" ").append(arg);
                }
                return host.RunCommand(stringBuilder.substring(1));
            }
        }
        catch (Exception e)
        {
            _host.IO.WriteException(e);
            System.out.println(Arrays.toString(e.getStackTrace()));
            return TraceBack.AppException;
        }

    }

}
