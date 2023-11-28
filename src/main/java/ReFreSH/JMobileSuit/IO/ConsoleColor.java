package ReFreSH.JMobileSuit.IO;

public enum ConsoleColor {
    Black(8),
    DarkGray(-8),
    Gray(7),
    White(-7),
    Red(1),
    DarkRed(-1),
    Cyan(-6),
    DarkCyan(6),
    Magenta(5),
    DarkMagenta(-5),
    Blue(-4),
    DarkBlue(4),
    Green(2),
    DarkGreen(-2),
    Yellow(3),
    DarkYellow(-3),
    Null(0);
    public static final String UnderLineCode = "4";
    private static final String ClearEffect = "\033[0m";
    /**
     * get ForeGroundCode of a color. only foreground is available for colors with value < 0
     */
    public final String ForeGroundCode;
    /**
     * get BackGroundCode of a color.
     * only foreground is available for colors with value < 0
     * so if necessary, use getColorInverse().
     */
    public final String BackGroundCode;
    /**
     * the code represents the color
     */
    public final int Code;

    ConsoleColor(int code) {
        Code = code;
        if (code == 0) {
            ForeGroundCode = BackGroundCode = "";
        } else {
            ForeGroundCode = code < 0 ? "1;" : "" + "3" + (Math.abs(code) & 7);
            BackGroundCode = "4" + (Math.abs(code) & 7);
        }
    }

    public static String getColor(ConsoleColor foreGround) {
        return getColor(foreGround, ConsoleColor.Null, false);
    }

    public static String getColor(ConsoleColor foreGround, ConsoleColor backGround) {
        return getColor(foreGround, backGround, false);
    }

    public static String getColor(ConsoleColor foreGround, ConsoleColor backGround, boolean underline) {
        return getColorCore(foreGround, backGround, underline) + "m";
    }

    public static String getColorInverse(ConsoleColor foreGround, ConsoleColor backGround, boolean underline) {
        return getColorCore(backGround, foreGround, underline) + ";7m";//Inverse
    }

    private static String getColorCore(ConsoleColor foreGround, ConsoleColor backGround, boolean underline) {
        StringBuilder sb = new StringBuilder("\033[");
        if (underline) sb.append(UnderLineCode);
        if (foreGround != ConsoleColor.Null) sb.append(';').append(foreGround.ForeGroundCode);
        if (backGround != ConsoleColor.Null) sb.append(';').append(backGround.BackGroundCode);
        return sb.toString();
    }

    @Override
    public String toString() {
        return getColor(this);
    }
}
