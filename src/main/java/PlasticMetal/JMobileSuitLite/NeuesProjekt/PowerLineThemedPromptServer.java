package PlasticMetal.JMobileSuitLite.NeuesProjekt;

import PlasticMetal.JMobileSuitLite.*;
import PlasticMetal.JMobileSuitLite.IO.*;

/// <summary>
/// a power line themed prompt server for mobile suit
/// </summary>
public class PowerLineThemedPromptServer extends CommonPromptServer

{


    /// <inheritdoc />
        public PowerLineThemedPromptServer(){super();}

    /// <inheritdoc />
        private PowerLineThemedPromptServer(IOServer io) { super(io, ColorSetting.getInstance());}

    /// <inheritdoc />
        public PowerLineThemedPromptServer(SuitConfiguration configuration) {super(configuration); }
    /// <summary>
    /// Create a mobile suit configuration with power line theme
    /// </summary>
    /// <returns></returns>
    public static SuitConfiguration getPowerLineThemeConfiguration()
    {
        IOServer io = new IOServer();
        SuitConfiguration r = new CommonSuitConfiguration(BuildInCommandServer.class, io,
                new PowerLineThemedPromptServer(io), ColorSetting.getInstance());
        io.Prompt = r.Prompt();
        return r;
    }

    /// <summary>
    /// a lightning ⚡ char
    /// </summary>
    protected static final char Lightning = '⚡';

    /// <summary>
    /// a right arrow  char
    /// </summary>
    protected static final char RightArrow = '';

    /// <summary>
    /// a right triangle  char
    /// </summary>
    protected static final char RightTriangle = '';
    /// <summary>
    /// a cross ⨯ char
    /// </summary>
    protected static final char Cross = '⨯';

    @Override
    public void Print()
    {
        ConsoleColor tbColor = ColorSetting.SelectColor( LastTraceBack.Value > 0 ?
                OutputType.Prompt : (LastTraceBack == TraceBack.AllOk? OutputType.AllOk : OutputType.Error),null,Color);

        ConsoleColor lastColor=LastTraceBack== TraceBack.Prompt? Color.ListTitleColor : Color.InformationColor;

        IO.Write(' '+ LastInformation+' ', Color.DefaultColor, lastColor);

        if (LastReturnValue!=null&&!LastReturnValue.equals(""))
        {
            IO.Write(String.valueOf(RightTriangle),
                    lastColor, Color.CustomInformationColor);
            IO.Write(" "+ Lang.ReturnValue+" "+RightArrow+" "+LastReturnValue+" ",
                    Color.DefaultColor, Color.CustomInformationColor);
            lastColor = Color.CustomInformationColor;
        }

        String tbExpression="";
        switch (LastTraceBack){

            case Prompt:
                tbExpression=LastPromptInformation;
                break;
            case OnExit:
                tbExpression="";
                break;
            case AllOk:
                tbExpression=Lang.AllOK;
                break;
            case InvalidCommand:
                tbExpression=Lang.InvalidCommand;
                break;
            case ObjectNotFound:
                tbExpression=Lang.ObjectNotFound;
                break;
            case MemberNotFound:
                tbExpression=Lang.MemberNotFound;
                break;
        }


        IO.Write(String.valueOf(RightTriangle),
                lastColor, tbColor);
        IO.Write(" "+tbExpression+" ", Color.DefaultColor, tbColor);
        IO.Write(RightTriangle + " " , tbColor);

    }
}

