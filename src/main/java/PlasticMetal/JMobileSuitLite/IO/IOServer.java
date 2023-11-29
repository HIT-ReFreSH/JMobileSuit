package PlasticMetal.JMobileSuitLite.IO;

import static PlasticMetal.JMobileSuitLite.LangResourceBundle.Lang;

import PlasticMetal.JMobileSuitLite.Diagnostics.SuitLogger;
import PlasticMetal.Jarvis.ObjectModel.Tuple;
import PlasticMetal.JMobileSuitLite.SuitConfiguration;
import PlasticMetal.JMobileSuitLite.TraceBack;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.Stack;

/**
 * A entity, which serves the input/output of a mobile suit.
 */
@SuppressWarnings({"BooleanMethodIsAlwaysInverted", "unused"})
public class IOServer
{
    /**
     * Default IOServer, using stdin, stdout, stderr.
     */
    public static final IOServer GeneralIO = new IOServer();
    private static final String ClearEffect = "\033[0m";

    private final SuitLogger Logger;

    /**
     * Color settings for this IOServer. (default getInstance)
     */
    public PlasticMetal.JMobileSuitLite.IO.ColorSetting ColorSetting;
    public PromptServer Prompt;

    /**
     * Write debug info to log file
     * @param content debug info
     */
    public void WriteDebug(String content){
        Logger.LogDebug(content);
    }

    /**
     * Write exception info to log file
     * @param content exception
     */
    public void WriteException(Exception content){
        Logger.LogException(content);
    }

    /**
     * Write exception info to log file
     * @param content exception info
     */
    public void WriteException(String content){
        Logger.LogException(content);
    }

    /**
     * Initialize a IOServer.
     */
    public IOServer()
    {
        Prompt = PromptServer.getInstance();
        ColorSetting = PlasticMetal.JMobileSuitLite.IO.ColorSetting.getInstance();
        _input = System.in;
        Output = System.out;
        Error = System.err;
        Logger=SuitLogger.ofTemp();
        _inputScanner = new Scanner(_input);

    }


    /**
     * Initialize a IOServer.
     */
    public IOServer(PromptServer promptServer,SuitLogger logger,ColorSetting colorSetting)
    {
        Prompt = promptServer;
        ColorSetting = colorSetting;
        _input = System.in;
        Output = System.out;
        Error = System.err;
        Logger=logger;
        _inputScanner = new Scanner(_input);

    }

    /**
     * Initialize a IOServer.
     */
    public IOServer(SuitConfiguration configuration)
    {
        Prompt = configuration.Prompt();
        ColorSetting = configuration.ColorSetting();
        _input = System.in;
        Output = System.out;
        Error = System.err;
        Logger=configuration.Logger();
        _inputScanner = new Scanner(_input);

    }

    /**
     * Input stream (default stdin)
     */
    private InputStream _input;

    private Scanner _inputScanner;

    /**
     * get Input stream (default stdin)
     *
     * @return Input stream
     */
    public InputStream GetInput()
    {
        return _input;
    }

    /**
     * set Input stream (default stdin)
     *
     * @param value new input stream
     */
    public void SetInput(InputStream value)
    {
        _input = value;
        _inputScanner = new Scanner(_input);
    }

    /**
     * Checks if this IOServer's input stream is redirected (NOT stdin)
     *
     * @return if this IOServer's input stream is redirected (NOT stdin)
     */
    public boolean IsInputRedirected()
    {
        return !System.in.equals(_input);
    }

    /**
     * Reset this IOServer's input stream to stdin
     */
    public void ResetInput()
    {
        _input = System.in;
    }


    /**
     * Reads a line from input stream, with prompt.
     *
     * @param prompt  The prompt display (output to output stream) before user input.
     * @param newLine If the prompt will display in a single line.
     * @return Content from input stream, null if EOF.
     */
    public String ReadLine(String prompt, boolean newLine)
    {
        return ReadLineBase(prompt, null, newLine, null);
    }

    /**
     * Reads a line from input stream, with prompt.
     *
     * @param prompt            The prompt display (output to output stream) before user input.
     * @param newLine           If the prompt will display in a single line.
     * @param customPromptColor Prompt's Color, Color.PromptColor as default.
     * @return Content from input stream, null if EOF.
     */
    public String ReadLine(String prompt, boolean newLine, ConsoleColor customPromptColor)
    {
        return ReadLineBase(prompt, null, newLine, customPromptColor);
    }

    /**
     * Reads a line from input stream, with prompt. Return something default if user input "".
     *
     * @param prompt            The prompt display (output to output stream) before user input.
     * @param defaultValue      Default return value if user input "".
     * @param customPromptColor Prompt's Color, Color.PromptColor as default.
     * @return Content from input stream, null if EOF, if user input "", return defaultValue.
     */
    public String ReadLine(String prompt, String defaultValue,
                           ConsoleColor customPromptColor)
    {
        return ReadLineBase(prompt, defaultValue, false, customPromptColor);
    }

    /**
     * Reads a line from input stream.
     *
     * @return Content from input stream, null if EOF.
     */
    public String ReadLine()
    {
        return ReadLineBase(null, null, false, null);
    }

    /**
     * Reads a line from input stream, with prompt.
     *
     * @param prompt The prompt display (output to output stream) before user input.
     * @return Content from input stream, null if EOF.
     */
    public String ReadLine(String prompt)
    {
        return ReadLineBase(prompt, null, false, null);
    }


    /**
     * Reads a line from input stream, with prompt. Return something default if user input "".
     *
     * @param prompt            The prompt display (output to output stream) before user input.
     * @param customPromptColor Prompt's Color, Color.PromptColor as default.
     * @return Content from input stream, null if EOF, if user input "", return defaultValue.
     */
    public String ReadLine(String prompt, ConsoleColor customPromptColor)
    {
        return ReadLineBase(prompt, null, false, customPromptColor);
    }

    /**
     * Reads a line from input stream, with prompt. Return something default if user input "".
     *
     * @param prompt       The prompt display (output to output stream) before user input.
     * @param defaultValue Default return value if user input ""
     * @return Content from input stream, null if EOF, if user input "", return defaultValue
     */
    public String ReadLine(String prompt, String defaultValue)
    {
        return ReadLineBase(prompt, defaultValue, false, null);
    }

    /**
     * Reads a line from input stream, with prompt. Return something default if user input "".
     *
     * @param prompt       The prompt display (output to output stream) before user input.
     * @param defaultValue Default return value if user input "".
     * @param newLine      If the prompt will display in a single line.
     * @return Content from input stream, null if EOF, if user input "", return defaultValue.
     */
    public String ReadLine(String prompt, String defaultValue, boolean newLine)
    {
        return ReadLineBase(prompt, defaultValue, newLine, null);
    }


    /**
     * Reads a line from input stream, with prompt. Return something default if user input "".
     *
     * @param prompt            The prompt display (output to output stream) before user input.
     * @param defaultValue      Default return value if user input "".
     * @param newLine           If the prompt will display in a single line.
     * @param customPromptColor Prompt's Color, Color.PromptColor as default.
     * @return Content from input stream, null if EOF, if user input "", return defaultValue.
     */
    public String ReadLine(String prompt, String defaultValue, boolean newLine, ConsoleColor customPromptColor)
    {
        return ReadLineBase(prompt, defaultValue, newLine, customPromptColor);
    }

    private String ReadLineBase(String prompt, String defaultValue, boolean newLine, ConsoleColor customPromptColor)
    {
        if (prompt != null && prompt.equals(""))
        {
            if (defaultValue != null && !defaultValue.equals(""))
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
        while (stringBuilder.length() > 0 && stringBuilder.charAt(stringBuilder.length() - 1) == '%')
        {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            if (!sc.hasNextLine()) break;
            stringBuilder.append(sc.nextLine());

        }
        return (stringBuilder.length() == 0 ? defaultValue : stringBuilder.toString());
    }

    /**
     * Reads the next character from input stream.
     *
     * @return The next available character.
     * @throws IOException ignore.
     */
    public int Read() throws IOException
    {
        return _input.read();
    }


    /**
     * Check if this IOServer's error stream is redirected (NOT stderr)
     *
     * @return if this IOServer's error stream is redirected (NOT stderr)
     */
    public boolean IsErrorRedirected()
    {
        return !System.err.equals(Error);
    }

    /**
     * Check if this IOServer's output stream is redirected (NOT stdout)
     *
     * @return if this IOServer's output stream is redirected (NOT stdout)
     */
    public boolean IsOutputRedirected()
    {
        return !System.out.equals(Output);
    }

    /**
     * Error stream (default stderr)
     */
    public PrintStream Error;
    /**
     * Output stream (default stdout)
     */
    public PrintStream Output;

    /**
     * The prefix of WriteLine() output, usually used to make indentation.
     */
    public String Prefix()
    {
        return PrefixBuilder.toString();
    }

    /**
     * set The prefix of WriteLine() output, usually used to make indentation.
     *
     * @param value The prefix of WriteLine() output, usually used to make indentation.
     */
    public void SetPrefix(String value)
    {

        ResetWriteLinePrefix();
        PrefixBuilder.append(value);
        PrefixLengthStack.push(value.length());

    }

    private final StringBuilder PrefixBuilder = new StringBuilder();

    private final Stack<Integer> PrefixLengthStack = new Stack<>();

    private ConsoleColor SelectColor(OutputType type, ConsoleColor customColor)
    {
        if (customColor == null || customColor == ConsoleColor.Null)
        {
            switch (type)
            {


                case Default:
                    return ColorSetting.DefaultColor;
                case Prompt:
                    return ColorSetting.PromptColor;
                case Error:
                    return ColorSetting.ErrorColor;
                case AllOk:
                    return ColorSetting.AllOkColor;
                case ListTitle:
                    return ColorSetting.ListTitleColor;
                case CustomInfo:
                    return ColorSetting.CustomInformationColor;
                case MobileSuitInfo:
                    return ColorSetting.InformationColor;
            }

        }

        return customColor;


    }

    private static String GetLabel(OutputType type)
    {
        switch (type)
        {
            case Default:
                return "";
            case Prompt:
                return "[Prompt]";
            case Error:
                return "[Error]";
            case AllOk:
                return "[AllOk]";
            case ListTitle:
                return "[List]";
            case CustomInfo:
            case MobileSuitInfo:
            default:
                return "[Info]";
        }

    }

    /**
     * Reset this IOServer's error stream to stderr
     */
    public void ResetError()
    {
        Error = System.err;
    }

    /**
     * Reset this IOServer's output stream to stdout
     */
    public void ResetOutput()
    {
        Output = System.out;
    }

    private void ResetWriteLinePrefix()
    {
        PrefixBuilder.setLength(0);
        PrefixLengthStack.clear();
    }

    /**
     * append a {@code '\t'} to Prefix, usually used to increase indentation
     */


    public void AppendWriteLinePrefix()
    {
        PrefixBuilder.append("\t");
        PrefixLengthStack.push(1);
    }

    /**
     * append a str to Prefix, usually used to increase indentation
     *
     * @param str the str to append
     */


    public void AppendWriteLinePrefix(String str)
    {
        PrefixBuilder.append(str);
        PrefixLengthStack.push(str.length());
    }

    /**
     * Subtract a str from Prefix, usually used to decrease indentation
     */
    public void SubtractWriteLinePrefix()
    {
        if (PrefixLengthStack.size() == 0) return;
        int l = PrefixLengthStack.pop();
        PrefixBuilder.delete(PrefixBuilder.length() - l, PrefixBuilder.length());
    }

    /**
     * Writes some content to output stream. With certain color in console.
     *
     * @param content     Content to output .
     * @param customColor Customized foreground color in console
     */
    public void Write(String content, ConsoleColor customColor)
    {
        WriteBase(content, OutputType.Default, customColor);
    }

    /**
     * Writes some content to output stream. With certain color in console.
     *
     * @param content         Content to output .
     * @param foreGroundColor foreground color
     * @param backGroundColor background color
     */
    public void Write(String content, ConsoleColor foreGroundColor, ConsoleColor backGroundColor)
    {
        if (!IsOutputRedirected())
        {

            Output.print(ConsoleColor.getColor(foreGroundColor, backGroundColor) + content + ClearEffect);
        }
        else
        {
            Output.print(content);
        }
    }

    /**
     * Writes some content to output stream.
     *
     * @param content Content to output .
     */
    public void Write(String content)
    {
        WriteBase(content, OutputType.Default, null);
    }

    /**
     * Writes some content to output stream. With certain color in console.
     *
     * @param content Content to output .
     * @param type    Type of this content,this decides how will it be like.
     */
    public void Write(String content, OutputType type)
    {
        WriteBase(content, type, null);
    }

    /**
     * Writes some content to output stream. With certain color in console.
     *
     * @param content     Content to output .
     * @param type        Type of this content,this decides how will it be like.
     * @param customColor Customized color in console
     */
    public void Write(String content, OutputType type, ConsoleColor customColor)
    {
        WriteBase(content, type, customColor);
    }

    private void WriteBase(String content, OutputType type, ConsoleColor customColor)
    {
        if (!IsOutputRedirected())
        {
            ConsoleColor color = SelectColor(type, customColor);
            Output.print(color + content + ClearEffect);
        }
        else
        {
            if (type != OutputType.Prompt)
                Output.print(content);
        }
    }


    /**
     * Writes a new line to output stream, with line break.
     */
    public void WriteLine()
    {
        WriteLineBase("", OutputType.Default, null);
    }

    /**
     * Writes some content to output stream, with line break. With certain color in console.
     *
     * @param content     Content to output
     * @param customColor Customized color in console.
     */
    public void WriteLine(String content, ConsoleColor customColor)
    {
        WriteLineBase(content, OutputType.Default, customColor);
    }

    /**
     * Writes some content to output stream, with line break.
     *
     * @param content Content to output
     */
    public void WriteLine(String content)
    {
        WriteLineBase(content, OutputType.Default, null);
    }

    /**
     * Writes some content to output stream, with line break. With certain color in console.
     *
     * @param content Content to output
     * @param type    Type of this content,this decides how will it be like(color in Console, label in file).
     */
    public void WriteLine(String content, OutputType type)
    {
        WriteLineBase(content, type, null);
    }

    /**
     * Writes some content to output stream, with line break. With certain color in console.
     *
     * @param content     Content to output
     * @param type        Type of this content,this decides how will it be like(color in Console, label in file).
     * @param customColor Customized color in console.
     */
    public void WriteLine(String content, OutputType type, ConsoleColor customColor)
    {
        WriteLineBase(content, type, customColor);
    }

    private void WriteLineBase(String content, OutputType type, ConsoleColor customColor)
    {
        if (!IsOutputRedirected())
        {
            ConsoleColor color = SelectColor(type, customColor);
            Output.println(color + Prefix() + content + ClearEffect);

        }
        else
        {
            String sb = "[" + LocalDateTime.now().toString() +
                    "]" +
                    GetLabel(type) +
                    content;
            Output.println(sb);
        }
    }

    /**
     * Writes some content to output stream, with line break. With certain color for each part of content in console.
     *
     * @param contentArray TupleArray. For each Tuple,
     *                     first is a part of content second is optional,
     *                     the color of output(in console)
     */
    public void WriteLine(Iterable<Tuple<String, ConsoleColor>> contentArray)
    {
        WriteLine(contentArray, OutputType.Default);
    }



    /**
     * Writes some content to output stream, with line break. With certain color for each part of content in console.
     *
     * @param contentArray TupleArray. For each Tuple,
     *                     first is a part of content second is optional,
     *                     the color of output(in console)
     * @param type         Type of this content,this decides how will it be like(color in Console, label in file).
     */
    public void WriteLine(Iterable<Tuple<String, ConsoleColor>> contentArray, OutputType type)

    {
        if (!IsOutputRedirected())
        {
            ConsoleColor defaultColor = SelectColor(type, null);
            Output.print(defaultColor + Prefix() + ClearEffect);
            for (Tuple<String, ConsoleColor> t : contentArray)
            {
                if (t.Second == null || t.Second==ConsoleColor.Null) t.Second = defaultColor;
                Output.print(t.Second + t.First + ClearEffect);
            }
            Output.print("\n");
        }
        else
        {
            for (Tuple<String, ConsoleColor> t : contentArray)
            {
                Output.print(t.First);
            }

            String sb = "[" + LocalDateTime.now().toString() +
                    "]" +
                    GetLabel(type);
            Output.print(sb);
        }
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="contentArray">TupleArray.
    /// FOR EACH Tuple, first is a part of content;
    /// second is optional, the foreground color of output (in console),
    /// third is optional, the background color of output.
    /// </param>
    /// <param name="type">Optional. Type of this content, this decides how will it be like (color in Console, label in file).</param>


}
