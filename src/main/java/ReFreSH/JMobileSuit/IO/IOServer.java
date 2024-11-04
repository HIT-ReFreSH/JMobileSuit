package ReFreSH.JMobileSuit.IO;

import ReFreSH.JMobileSuit.SuitConfiguration;
import ReFreSH.JMobileSuit.TraceBack;
import ReFreSH.Jarvis.ObjectModel.Tuple;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.Stack;

import static ReFreSH.JMobileSuit.LangResourceBundle.Lang;

/**
 * 一个实体，用于提供移动套装的输入/输出。
 */
@SuppressWarnings({"BooleanMethodIsAlwaysInverted", "unused"})
@Component("IOServer")
public class IOServer {
    /**
     *默认 IOServer，使用 stdin、stdout、stderr。
     */
    public static final IOServer GeneralIO = new IOServer();
    private static final String ClearEffect = "\033[0m";

    private final Logger logger;
    private final StringBuilder PrefixBuilder = new StringBuilder();
    private final Stack<Integer> PrefixLengthStack = new Stack<>();
    /**
     *  此 IOServer 的颜色设置。（默认 getInstance）
     */
    @Autowired
    public ReFreSH.JMobileSuit.IO.ColorSetting ColorSetting;
    public PromptServer Prompt;
    /**
     * 错误流（默认 stderr）
     */
    public PrintStream Error;
    /**
     * 输出流（默认 stdout）
     */
    public PrintStream Output;
    /**
     * 输入流 (默认 stdin)
     */
    private InputStream _input;
    private Scanner _inputScanner;

    /**
     * 初始化 IOServer。
     */
    public IOServer() {
        Prompt = PromptServer.getInstance();
//        ColorSetting = ReFreSH.JMobileSuit.IO.ColorSetting.getInstance();
        _input = System.in;
        Output = System.out;
        Error = System.err;
        logger = LogManager.getLogger(this.getClass());
        _inputScanner = new Scanner(_input);

    }

    /**
     * 初始化 IOServer.
     */
    public IOServer(PromptServer promptServer, Logger logger, ColorSetting colorSetting) {
        Prompt = promptServer;
        ColorSetting = colorSetting;
        _input = System.in;
        Output = System.out;
        Error = System.err;
        this.logger = logger;
        _inputScanner = new Scanner(_input);

    }

    /**
     * 初始化 IOServer.
     */
    public IOServer(SuitConfiguration configuration) {
        Prompt = configuration.Prompt();
        ColorSetting = configuration.ColorSetting();
        _input = System.in;
        Output = System.out;
        Error = System.err;
        logger = configuration.Logger();
        _inputScanner = new Scanner(_input);

    }

    private static String GetLabel(OutputType type) {
        return switch (type) {
            case Default -> "";
            case Prompt -> "[Prompt]";
            case Error -> "[Error]";
            case AllOk -> "[AllOk]";
            case ListTitle -> "[List]";
            default -> "[Info]";
        };

    }

    /**
     *将调试信息写入日志文件
     *
     * @param content debug info
     */
    public void WriteDebug(String content) {
        logger.debug(content);
    }

    /**
     * 将异常信息写入日志文件
     *
     * @param content exception
     */
    public void WriteException(Exception content) {
        logger.error(content);
    }

    /**
     *将异常信息写入日志文件
     *
     * @param content exception info
     */
    public void WriteException(String content) {
        logger.error(content);
    }

    /**
     * 获得输入流 （默认 stdin）
     *
     * @return 返回输入流
     */
    public InputStream GetInput() {
        return _input;
    }

    /**
     * 设置输入流 (默认 stdin)
     *
     * @param value 创建新的输入流
     */
    public void SetInput(InputStream value) {
        _input = value;
        _inputScanner = new Scanner(_input);
    }

    /**
     *检查此 IOServer 的输入流是否被重定向（不是 stdin）
     *
     * @return 如果此 IOServer 的输入流被重定向（不是 stdin）
     */
    public boolean IsInputRedirected() {
        return !System.in.equals(_input);
    }

    /**
     *将此 IOServer 的输入流重置为 stdin
     */
    public void ResetInput() {
        _input = System.in;
    }

    /**
     * 使用 prompt 从 input stream 中读取一行。
     *
     * @param prompt  用户输入之前的提示显示（输出到输出流）。
     * @param newLine 提示是否显示在一行中。
     * @return来自输入流的内容，如果 EOF，则为 null。
     */
    public String ReadLine(String prompt, boolean newLine) {
        return ReadLineBase(prompt, null, newLine, null);
    }

    /**
     * 使用 prompt 从 input stream 中读取一行。
     *
     * @param prompt            用户输入之前的提示显示（输出到输出流）。
     * @param newLine           提示是否显示在一行中。.
     * @param customPromptColor Prompt's Color, Color.PromptColor as default.
     * @return 来自输入流的内容，如果 EOF，则为 null。
     */
    public String ReadLine(String prompt, boolean newLine, ConsoleColor customPromptColor) {
        return ReadLineBase(prompt, null, newLine, customPromptColor);
    }

    /**
     * 使用 prompt 从 input stream 中读取一行。如果用户输入 “”，则返回默认值
     *
     * @param prompt            用户输入之前的提示显示（输出到输出流）。
     * @param defaultValue      Default return value if user input "".
     * @param customPromptColor Prompt's Color, Color.PromptColor as default.
     * @return 来自输入流的内容，如果 EOF，则为 null。 if user input "", return defaultValue.
     */
    public String ReadLine(String prompt, String defaultValue,
                           ConsoleColor customPromptColor) {
        return ReadLineBase(prompt, defaultValue, false, customPromptColor);
    }

    /**
     *使用 prompt 从 input stream 中读取一行。
     *
     * @return 来自输入流的内容，如果 EOF，则为 null。
     */
    public String ReadLine() {
        return ReadLineBase(null, null, false, null);
    }

    /**
     * 使用 prompt 从 input stream 中读取一行。
     *
     * @param prompt 用户输入之前的提示显示（输出到输出流）。
     * @return 来自输入流的内容，如果 EOF，则为 null。
     */
    public String ReadLine(String prompt) {
        return ReadLineBase(prompt, null, false, null);
    }

    /**
     * 使用 prompt 从 input stream 中读取一行。如果用户输入 “”，则返回默认值
     *
     * @param prompt            用户输入之前的提示显示（输出到输出流）。
     * @param customPromptColor Prompt's Color，Color.PromptColor 为默认值。
     * @return 来自输入流的内容，如果 EOF 为 null，如果用户输入 “”，则返回 defaultValue
     */
    public String ReadLine(String prompt, ConsoleColor customPromptColor) {
        return ReadLineBase(prompt, null, false, customPromptColor);
    }

    /**
     * 使用 prompt 从 input stream 中读取一行。如果用户输入 “”，则返回默认值
     *
     * @param prompt      用户输入之前的提示显示（输出到输出流）。
     * @param defaultValue 如果用户输入 “”，则默认返回值。
     * @return 来自输入流的内容，如果 EOF 为 null，如果用户输入 “”，则返回 defaultValue
     */
    public String ReadLine(String prompt, String defaultValue) {
        return ReadLineBase(prompt, defaultValue, false, null);
    }

    /**
     * 使用 prompt 从 input stream 中读取一行。如果用户输入 “”，则返回默认值.
     *
     * @param prompt       用户输入之前的提示显示（输出到输出流）。
     * @param defaultValue 如果用户输入 “”，则默认返回值。
     * @param newLine      提示是否显示在一行中。
     * @return 来自输入流的内容，如果 EOF 为 null，如果用户输入 “”，则返回 defaultValue
     */
    public String ReadLine(String prompt, String defaultValue, boolean newLine) {
        return ReadLineBase(prompt, defaultValue, newLine, null);
    }

    /**
     * 使用 prompt 从 input stream 中读取一行。如果用户输入 “”，则返回默认值
     *
     * @param prompt           用户输入之前的提示显示（输出到输出流）。
     * @param defaultValue     如果用户输入 “”，则默认返回值。
     * @param newLine           提示是否显示在一行中。
     * @param customPromptColor Prompt's Color，Color.PromptColor 为默认值。
     * @return 来自输入流的内容，如果 EOF 为 null，如果用户输入 “”，则返回 defaultValue
     */
    public String ReadLine(String prompt, String defaultValue, boolean newLine, ConsoleColor customPromptColor) {
        return ReadLineBase(prompt, defaultValue, newLine, customPromptColor);
    }

    private String ReadLineBase(String prompt, String defaultValue, boolean newLine, ConsoleColor customPromptColor) {
        if (prompt != null && prompt.isEmpty()) {
            if (defaultValue != null && !defaultValue.isEmpty())
                Prompt.Update("", prompt, TraceBack.Prompt);
            else
                Prompt.Update("", prompt, TraceBack.Prompt,
                        Lang.Default + ": " + defaultValue);
            Prompt.Print();

            if (newLine)
                WriteLine();
        }

        Scanner sc = _inputScanner;
        if (!sc.hasNextLine()) return null;
        StringBuilder stringBuilder = new StringBuilder(sc.nextLine());
        while (!stringBuilder.isEmpty() && stringBuilder.charAt(stringBuilder.length() - 1) == '%') {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            if (!sc.hasNextLine()) break;
            stringBuilder.append(sc.nextLine());

        }
        return (stringBuilder.isEmpty() ? defaultValue : stringBuilder.toString());
    }

    /**
     *从输入流中读取下一个字符.
     *
     * @return 下一个可用角字符.
     * @throws IOException ignore.
     */
    public int Read() throws IOException {
        return _input.read();
    }

    /**
     * 检查此 IOServer 的错误流是否已重定向（不是 stderr）
     *
     * @return 如果此 IOServer 的错误流被重定向（不是 stderr）
     */
    public boolean IsErrorRedirected() {
        return !System.err.equals(Error);
    }

    /**
     * 检查此 IOServer 的输出流是否被重定向（不是 stdout）
     *
     * @return 如果此 IOServer 的输出流被重定向（不是 stdout）
     */
    public boolean IsOutputRedirected() {
        return !System.out.equals(Output);
    }

    /**
     * WriteLine（） 输出的前缀，通常用于缩进。
     */
    public String Prefix() {
        return PrefixBuilder.toString();
    }

    /**
     * set WriteLine（） 输出的前缀，通常用于缩进。
     *
     * @param value WriteLine（） 输出的前缀，通常用于缩进。
     */
    public void SetPrefix(String value) {

        ResetWriteLinePrefix();
        PrefixBuilder.append(value);
        PrefixLengthStack.push(value.length());

    }

    public StringBuilder getPrefixBuilder() {
        return new StringBuilder(PrefixBuilder);
    }

    public Stack<Integer> getPrefixLengthStack() {
        Stack<Integer> copyStack = new Stack<>();
        copyStack.addAll(PrefixLengthStack);
        return copyStack;
    }

    private ConsoleColor SelectColor(OutputType type, ConsoleColor customColor) {
        if (customColor == null || customColor == ConsoleColor.Null) {
            return switch (type) {
                case Default -> ColorSetting.DefaultColor;
                case Prompt -> ColorSetting.PromptColor;
                case Error -> ColorSetting.ErrorColor;
                case AllOk -> ColorSetting.AllOkColor;
                case ListTitle -> ColorSetting.ListTitleColor;
                case CustomInfo -> ColorSetting.CustomInformationColor;
                case MobileSuitInfo -> ColorSetting.InformationColor;
            };

        }

        return customColor;


    }

    /**
     * 将此 IOServer 的错误流重置为 stderr
     */
    public void ResetError() {
        Error = System.err;
    }

    /**
     * 将此 IOServer 的输出流重置为 stdout
     */
    public void ResetOutput() {
        Output = System.out;
    }

    private void ResetWriteLinePrefix() {
        PrefixBuilder.setLength(0);
        PrefixLengthStack.clear();
    }

    /**
     * 在 Prefix 后附加一个 {@code 't'}，通常用于增加缩进
     */


    public void AppendWriteLinePrefix() {
        PrefixBuilder.append("\t");
        PrefixLengthStack.push(1);
    }

    /**
     * 在 Prefix 后附加一个 str，通常用于增加缩进
     *
     * @param str 要附加的 str
     */


    public void AppendWriteLinePrefix(String str) {
        PrefixBuilder.append(str);
        PrefixLengthStack.push(str.length());
    }

    /**
     * 从 Prefix 中减去一个 str，通常用于减少缩进
     */
    public void SubtractWriteLinePrefix() {
        if (PrefixLengthStack.isEmpty()) return;
        int l = PrefixLengthStack.pop();
        PrefixBuilder.delete(PrefixBuilder.length() - l, PrefixBuilder.length());
    }

    /**
     *将一些内容写入输出流。控制台有一定的颜色。
     *
     * @param content     Content to output .
     * @param customColor Customized foreground color in console
     */
    public void Write(String content, ConsoleColor customColor) {
        WriteBase(content, OutputType.Default, customColor);
    }

    /**
     * 将一些内容写入输出流。控制台有一定的颜色。
     *
     * @param content         要输出的内容 。
     * @param foreGroundColor 前景色
     * @param backGroundColor 背景色
     */
    public void Write(String content, ConsoleColor foreGroundColor, ConsoleColor backGroundColor) {
        if (!IsOutputRedirected()) {

            Output.print(ConsoleColor.getColor(foreGroundColor, backGroundColor) + content + ClearEffect);
        } else {
            Output.print(content);
        }
    }

    /**
     * 将一些内容写入输出流。
     *
     * @param content 要输出的内容 。
     */
    public void Write(String content) {
        WriteBase(content, OutputType.Default, null);
    }

    /**
     * 将一些内容写入输出流。控制台有一定的颜色。
     *
     * @param content 要输出的内容 。
     * @param type    类型，这决定了它会是什么样子。
     */
    public void Write(String content, OutputType type) {
        WriteBase(content, type, null);
    }

    /**
     * 将一些内容写入输出流。控制台有一定的颜色。
     *
     * @param content     要输出的内容 。
     * @param type        类型，这决定了它会是什么样子。
     * @param customColor 控制台中的自定义颜色
     */
    public void Write(String content, OutputType type, ConsoleColor customColor) {
        WriteBase(content, type, customColor);
    }

    private void WriteBase(String content, OutputType type, ConsoleColor customColor) {
        if (!IsOutputRedirected()) {
            ConsoleColor color = SelectColor(type, customColor);
            Output.print(color + content + ClearEffect);
        } else {
            if (type != OutputType.Prompt)
                Output.print(content);
        }
    }


    /**
     *  将新行写入 output stream，并带有换行符。
     */
    public void WriteLine() {
        WriteLineBase("", OutputType.Default, null);
    }

    /**
     *将一些内容写入输出流，带换行符。控制台有一定的颜色。
     *
     * @param content     要输出的内容
     * @param customColor 控制台中的自定义颜色。
     */
    public void WriteLine(String content, ConsoleColor customColor) {
        WriteLineBase(content, OutputType.Default, customColor);
    }

    /**
     *将一些内容写入输出流，带换行符。控制台有一定的颜色。
     *
     * @param content 要输出的内容
     */
    public void WriteLine(String content) {
        WriteLineBase(content, OutputType.Default, null);
    }

    /**
     *将一些内容写入输出流，带换行符。控制台有一定的颜色。
     *
     * @param content 要输出的内容
     * @param type    类型，这决定了它会是什么样子（控制台中的颜色，文件中的标签）。
     */
    public void WriteLine(String content, OutputType type) {
        WriteLineBase(content, type, null);
    }

    /**
     * 将一些内容写入输出流，带换行符。控制台有一定的颜色。
     *
     * @param content     要输出的内容
     * @param type        类型，这决定了它会是什么样子（控制台中的颜色，文件中的标签）。
     * @param customColor 控制台中的自定义颜色。
     */
    public void WriteLine(String content, OutputType type, ConsoleColor customColor) {
        WriteLineBase(content, type, customColor);
    }

    private void WriteLineBase(String content, OutputType type, ConsoleColor customColor) {
        if (!IsOutputRedirected()) {
            ConsoleColor color = SelectColor(type, customColor);
            Output.println(color + Prefix() + content + ClearEffect);

        } else {
            String sb =
                    GetLabel(type) +
                    content;
            Output.println(sb);
        }
    }

    /**
     * 将一些内容写入输出流，带换行符。控制台中内容的每个部分都有一定的颜色。
     *
     * @param contentArray TupleArray 的 Array 函数。对于每个 Tuples，
     *                      第一个是内容的一部分，第二个是可选的，
     *                      输出的颜色（在控制台中）
     */
    public void WriteLine(Iterable<Tuple<String, ConsoleColor>> contentArray) {
        WriteLine(contentArray, OutputType.Default);
    }


    /**
     * 将一些内容写入输出流，带换行符。控制台中内容的每个部分都有一定的颜色。
     *
     * @param contentArray TupleArray 的 Array 函数。对于每个 Tuples，
     *                      第一个是内容的一部分，第二个是可选的，
     *                      输出的颜色（在控制台中）
     */
    public void WriteLine(Iterable<Tuple<String, ConsoleColor>> contentArray, OutputType type) {
        if (!IsOutputRedirected()) {
            ConsoleColor defaultColor = SelectColor(type, null);
            Output.print(defaultColor + Prefix() + ClearEffect);
            for (Tuple<String, ConsoleColor> t : contentArray) {
                if (t.Second == null || t.Second == ConsoleColor.Null) t.Second = defaultColor;
                Output.print(t.Second + t.First + ClearEffect);
            }
            Output.print("\n");
        } else {
            for (Tuple<String, ConsoleColor> t : contentArray) {
                Output.print(t.First);
            }

            String sb =
                    GetLabel(type);
            Output.print(sb);
        }
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="contentArray">TupleArray.
    /// 对于每个 Tuples，first 是内容的一部分;
    /// second 是可选的，输出的前景色（在控制台中），
    /// 第三个是可选的，输出的背景颜色。
    /// </param>
    /// <param name="type">自选。类型，这将决定它的外观（控制台中的颜色，文件中的标签）。</param>


}
