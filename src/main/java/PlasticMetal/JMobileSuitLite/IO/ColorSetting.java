package PlasticMetal.JMobileSuitLite.IO;

/**
 * Color settings of a IOServer.
 */
public class ColorSetting {
    /**
     * Default color. For OutputType.Default
     */
    public final ConsoleColor DefaultColor = ConsoleColor.White;
    /**
     * Prompt Color. For OutputType.Prompt
     */
    public final ConsoleColor PromptColor = ConsoleColor.Magenta;
    /**
     * Error Color. For OutputType.Error
     */
    public final ConsoleColor ErrorColor = ConsoleColor.Red;
    /**
     * AllOK Color. For OutputType.AllOK
     */
    public final ConsoleColor AllOkColor = ConsoleColor.Green;
    /**
     * ListTitle Color. For OutputType.ListTitle
     */
    public final ConsoleColor ListTitleColor = ConsoleColor.Yellow;
    /**
     * CustomInformation Color. For OutputType.CustomInformation
     */
    public final ConsoleColor CustomInformationColor = ConsoleColor.DarkCyan;
    /**
     * Information Color. For OutputType.Information
     */
    public final ConsoleColor InformationColor = ConsoleColor.DarkBlue;


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
