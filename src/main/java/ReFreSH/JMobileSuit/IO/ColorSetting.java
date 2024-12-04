package ReFreSH.JMobileSuit.IO;

import org.springframework.stereotype.Component;

/**
 * Color settings of a IOServer.
 */
@Component("colorSetting")
public class ColorSetting {
    /**
     * Default color. For OutputType.Default
     */
    public ConsoleColor DefaultColor = ConsoleColor.White;
    /**
     * Prompt Color. For OutputType.Prompt
     */
    public ConsoleColor PromptColor = ConsoleColor.Magenta;
    /**
     * Error Color. For OutputType.Error
     */
    public ConsoleColor ErrorColor = ConsoleColor.Red;
    /**
     * AllOK Color. For OutputType.AllOK
     */
    public ConsoleColor AllOkColor = ConsoleColor.Green;
    /**
     * ListTitle Color. For OutputType.ListTitle
     */
    public ConsoleColor ListTitleColor = ConsoleColor.Yellow;
    /**
     * CustomInformation Color. For OutputType.CustomInformation
     */
    public ConsoleColor CustomInformationColor = ConsoleColor.DarkCyan;
    /**
     * Information Color. For OutputType.Information
     */
    public ConsoleColor InformationColor = ConsoleColor.DarkBlue;


    /**
     * Default color settings for IOServer.
     *
     * @return Default color settings for IOServer.
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
