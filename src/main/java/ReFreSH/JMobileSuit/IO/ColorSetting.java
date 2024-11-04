package ReFreSH.JMobileSuit.IO;

import org.springframework.stereotype.Component;

/**
 * IOServer 的颜色设置。
 */
@Component("colorSetting")
public class ColorSetting {
    /**
     * 默认颜色。对于 OutputType.Default
     */
    public final ConsoleColor DefaultColor = ConsoleColor.White;
    /**
     * 提示色。对于 OutputType.Prompt
     */
    public final ConsoleColor PromptColor = ConsoleColor.Magenta;
    /**
     * 错误颜色。对于 OutputType.Error
     */
    public final ConsoleColor ErrorColor = ConsoleColor.Red;
    /**
     * AllOK 颜色。对于 OutputType.AllOK
     */
    public final ConsoleColor AllOkColor = ConsoleColor.Green;
    /**
     * 列表标题颜色。对于 OutputType.ListTitle
     */
    public final ConsoleColor ListTitleColor = ConsoleColor.Yellow;
    /**
     * 用户信息颜色。对于 OutputType.CustomInformation
     */
    public final ConsoleColor CustomInformationColor = ConsoleColor.DarkCyan;
    /**
     * 信息颜色. 对于OutputType.Information
     */
    public final ConsoleColor InformationColor = ConsoleColor.DarkBlue;


    /**
     * IOServer 的默认颜色设置。
     *
     * @return 返回IOServer 的默认颜色设置。.
     */
    public static ColorSetting getInstance() {
        return new ColorSetting();
    }

    public static ConsoleColor selectColor(OutputType type, ConsoleColor customColor, ColorSetting colorSetting) {
        if (customColor == null || customColor == ConsoleColor.Null) {
            return switch (type) {
                case Default -> colorSetting.DefaultColor;
                case Prompt -> colorSetting.PromptColor;
                case Error -> colorSetting.ErrorColor;
                case AllOk -> colorSetting.AllOkColor;
                case ListTitle -> colorSetting.ListTitleColor;
                case CustomInfo -> colorSetting.CustomInformationColor;
                case MobileSuitInfo -> colorSetting.InformationColor;
            };

        }

        return customColor;


    }
}
