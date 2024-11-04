package ReFreSH.JMobileSuit.NeuesProjekt;

import ReFreSH.JMobileSuit.IO.*;
import ReFreSH.JMobileSuit.ObjectModel.SuitConfigurator;
import ReFreSH.JMobileSuit.SuitConfiguration;
import ReFreSH.JMobileSuit.TraceBack;

/**
 * 一款以 Power Line 为主题的机动战士提示服务器
 */
public class PowerLineThemedPromptServer extends CommonPromptServer {


    /**
     * 闪电 ⚡
     */
    protected static final char Lightning = '⚡';
    /**
     *右箭头  char
     */
    protected static final char RightArrow = '';
    /**
     * 直角三角形  char
     */
    protected static final char RightTriangle = '';
    /**
     * 叉 ⨯ char
     */
    protected static final char Cross = '⨯';

    /**
     * 初始化提示 default。
     */
    public PowerLineThemedPromptServer() {
        super();
    }

    /**
     *使用 IO 和颜色设置初始化提示。
     *
     * @param io given io server
     */
    private PowerLineThemedPromptServer(IOServer io) {
        super(io, ColorSetting.getInstance());
    }

    /**
     * 使用给定配置初始化提示 Server
     *
     * @param configuration given configuration
     */
    public PowerLineThemedPromptServer(SuitConfiguration configuration) {
        super(configuration);
    }

    /**
     * 创建具有电力线主题的移动设备套装配置
     */
    public static SuitConfiguration getPowerLineThemeConfiguration() {
        return SuitConfigurator.ofDefault().use(PowerLineThemedPromptServer.class).getConfiguration();
    }

    @Override
    public void Print() {
        ConsoleColor tbColor = ColorSetting.selectColor(LastTraceBack.Value > 0 ?
                OutputType.Prompt : (LastTraceBack == TraceBack.AllOk ? OutputType.AllOk : OutputType.Error), null, Color);

        ConsoleColor lastColor = LastTraceBack == TraceBack.Prompt ? Color.ListTitleColor : Color.InformationColor;

        IO.Write(' ' + LastInformation + ' ', Color.DefaultColor, lastColor);

        if (LastReturnValue != null && !LastReturnValue.isEmpty()) {
            IO.Write(String.valueOf(RightTriangle),
                    lastColor, Color.CustomInformationColor);
            IO.Write(" " + Lang.ReturnValue + " " + RightArrow + " " + LastReturnValue + " ",
                    Color.DefaultColor, Color.CustomInformationColor);
            lastColor = Color.CustomInformationColor;
        }

        String tbExpression = switch (LastTraceBack) {
            case Prompt -> LastPromptInformation;
            case OnExit -> "";
            case AllOk -> Lang.AllOK;
            case InvalidCommand -> Lang.InvalidCommand;
            case ObjectNotFound -> Lang.ObjectNotFound;
            case MemberNotFound -> Lang.MemberNotFound;
            case AppException -> Lang.ApplicationException;
        };


        IO.Write(String.valueOf(RightTriangle),
                lastColor, tbColor);
        IO.Write(" " + tbExpression + " ", Color.DefaultColor, tbColor);
        IO.Write(RightTriangle + " ", tbColor);

    }
}

