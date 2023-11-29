package PlasticMetal.JMobileSuitLite;

import java.util.Locale;
import java.util.ResourceBundle;

public class LangResourceBundle
{
    public final static LangResourceBundle Lang=new LangResourceBundle();
    public final String Bic;
    public final String BicExp1;
    public final String BicExp2;
    public final String Done;
    public final String Error;
    public final String InvalidCommand;
    public final String MemberNotFound;
    public final String Members;
    public final String ObjectNotFound;
    public final String Unknown;
    public final String ViewBic;
    public final String WorkInstance;
    public final String ReturnValue;
    public final String Default;
    public final String AllOK;
    public final String ApplicationException;
    public LangResourceBundle(){
        ResourceBundle bundle= ResourceBundle.getBundle("Lang",  Locale.getDefault());

        Bic=bundle.getString("Bic");
        BicExp1=bundle.getString("BicExp1");
        BicExp2=bundle.getString("BicExp2");
        Done=bundle.getString("Done");
        Error=bundle.getString("Error");
        InvalidCommand=bundle.getString("InvalidCommand");
        MemberNotFound=bundle.getString("MemberNotFound");
        Members=bundle.getString("Members");
        ObjectNotFound=bundle.getString("ObjectNotFound");
        Unknown=bundle.getString("Unknown");
        ViewBic=bundle.getString("ViewBic");
        WorkInstance=bundle.getString("WorkInstance");
        ReturnValue=bundle.getString("ReturnValue");
        Default=bundle.getString("Default");
        AllOK=bundle.getString("AllOK");
        ApplicationException=bundle.getString("ApplicationException");
    }
}
