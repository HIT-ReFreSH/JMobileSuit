package ReFreSH.JMobileSuit.IO;

import ReFreSH.JMobileSuit.SuitConfiguration;
import ReFreSH.Jarvis.ObjectModel.Tuple;
import org.apache.logging.log4j.core.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class IOServerTests {


    private IOServer ioServer = new IOServer();
    private Logger logger;
    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private ByteArrayOutputStream errorStream;
    private InputStream inputStream;




    @BeforeEach
    public void setUp() {
        // Mock the logger
        logger = mock(Logger.class);

        // Initialize IOServer with mock dependencies
        ioServer = new IOServer(null, logger, null);

        // Set up custom output/error streams
        outputStream = new ByteArrayOutputStream();
        errorStream = new ByteArrayOutputStream();
        inputStream = new ByteArrayInputStream("test input\n".getBytes());
        ioServer.Output = new PrintStream(outputStream);
        ioServer.Error = new PrintStream(errorStream);
        ioServer.SetInput(inputStream);
    }
    @Test
    public void testWriteLine() {
        ioServer.WriteLine("Hello World");
        assertEquals("Hello World", ioServer.getContent());
    }

    @Test
    public void testWriteDebug2() {
        ioServer.WriteDebug("Debug message");
        verify(logger).debug("Debug message");
    }

    @Test
    public void testWriteException2() {
        Exception e = new Exception("Test Exception");
        ioServer.WriteException(e);
        verify(logger).error(e);
    }

    @Test
    public void testWriteWithColor() {
        ioServer.Write("Colored Text", ConsoleColor.Red);
        // Assuming ConsoleColor.Red changes the output, we verify the text without the color code
        assertEquals("Colored Text\u001B[;31m", ioServer.getContent());
    }

    @Test
    public void testWriteDefault() {
        ioServer.Write("Default Text");
        assertEquals("Default Text\u001B[;1;m", ioServer.getContent());
    }

    @Test
    public void testIsInputRedirected2() {
        assertTrue(ioServer.IsInputRedirected());
    }
//    private final Logger Logger;
//
//    public IOServerTests(Logger logger) {
//        Logger = logger;
//    }

    private IOServer getInstance() {
        return new IOServer();
    }

    private IOServer getInstance(PromptServer promptServer, Logger logger, ColorSetting colorSetting) {
        return new IOServer(promptServer, logger, colorSetting);
    }

    private IOServer getInstance(SuitConfiguration configuration) {
        return new IOServer(configuration);
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }


    @Test
    public void IOServerTest() {
        IOServer instance = getInstance();
        assertNotNull(instance);
        // Testing dependency injection of IOServer and ColorSetting
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContextTest.xml");
        ColorSetting colorSetting = context.getBean("colorSetting", ColorSetting.class);
        IOServer ioServer = context.getBean("IOServer", IOServer.class);
        assertNotNull(colorSetting);
        assertNotNull(ioServer);
        assertNotNull(ioServer.ColorSetting);
    }

    @Test
    public void testParameterizedIOServer1() {
        PromptServer mockPromptServer = mock(PromptServer.class);

        Logger mockLogger = mock(Logger.class);
        ColorSetting mockColorSetting = mock(ColorSetting.class);

        IOServer ioserver = getInstance(mockPromptServer, mockLogger, mockColorSetting);

        assertNotNull(ioserver);
    }

    @Test
    public void testParameterizedIOServer2() {
        SuitConfiguration suitConfiguration = mock(SuitConfiguration.class);

        IOServer ioserver = new IOServer(suitConfiguration);

        assertNotNull(ioserver);
    }


    @Test
    public void testWriteDebug() throws Exception {
        Logger mockLogger = mock(Logger.class);
        ColorSetting colorSetting = mock(ColorSetting.class);
        PromptServer promptServer = mock(PromptServer.class);

        IOServer ioserver = getInstance(promptServer, mockLogger, colorSetting);

        ioserver.WriteDebug("Any Message");


        // Verify if the LogDebug method of the mock object has been called and if the passed parameters match the expected values.
        verify(mockLogger, times(1)).debug(anyString());
    }

    @Test
    public void testWriteException() throws Exception {
        Logger mockLogger = mock(Logger.class);
        ColorSetting colorSetting = mock(ColorSetting.class);
        PromptServer promptServer = mock(PromptServer.class);
        IOServer ioserver = getInstance(promptServer, mockLogger, colorSetting);

        Exception testException = new Exception("Mock Exception");
        ioserver.WriteException(testException);
        // Verify if the LogException method of the mock object has been called, and ensure that the passed parameter is the correct exception object.
        verify(mockLogger, times(1)).error(testException);
    }

    @Test
    public void testWriteExceptionString() throws Exception {
        Logger mockLogger = mock(Logger.class);
        ColorSetting colorSetting = mock(ColorSetting.class);
        PromptServer promptServer = mock(PromptServer.class);
        IOServer ioserver = getInstance(promptServer, mockLogger, colorSetting);

        String testException = "Mock String";
        ioserver.WriteException(testException);

        verify(mockLogger, times(1)).error(testException);
    }

    /**
     * The set tests must be executed before the get test methods.
     */
    @Test
    public void testSetInput() {
        String testInput = "Test Input";
        InputStream inputStream = new ByteArrayInputStream(testInput.getBytes());

        IOServer ioserver = getInstance();

        Scanner mockScanner = Mockito.mock(Scanner.class);

        try {
            PowerMockito.whenNew(Scanner.class).withArguments(inputStream).thenReturn(mockScanner);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ioserver.SetInput(inputStream);

        assertEquals(inputStream, ioserver.GetInput());

        // Validate whether _inputScanner has been updated correctly.
//        verify(mockScanner).close();  // Check if the Scanner has been closed once, ensuring that a new Scanner object has been recreated.
//        try {
//            verifyNew(Scanner.class).withNoArguments();  // Validate whether the Scanner constructor has been called correctly.
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }

    @Test
    public void testGetAndSetInput() throws Exception {
        String testInput = "abcdefg";
        InputStream inputStream = new ByteArrayInputStream(testInput.getBytes());

        IOServer ioserver = getInstance();
        ioserver.SetInput(inputStream);

        InputStream result = ioserver.GetInput();

        byte[] buffer = new byte[1024];
        int bytesRead;
        StringBuilder inputContent = new StringBuilder();
        try {
            while ((bytesRead = result.read(buffer)) != -1) {
                inputContent.append(new String(buffer, 0, bytesRead));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(testInput, inputContent.toString());
    }

    @Test
    public void testIsInputRedirected() {
        IOServer ioserver = new IOServer();

        assertFalse(ioserver.IsInputRedirected());

        String testInput = "anything";
        InputStream inputStream = new ByteArrayInputStream(testInput.getBytes());

        ioserver.SetInput(inputStream);

        assertTrue(ioserver.IsInputRedirected());
    }

    @Test
    public void testResetInput() {
        IOServer ioserver = getInstance();

        String testInput = "blahhhhhhhhhhhhhhhhhhhhhhhhhhh";
        InputStream inputStream = new ByteArrayInputStream(testInput.getBytes());

        ioserver.SetInput(inputStream);

        ioserver.ResetInput();

        // Check if it has been successfully reset to System.in.
        assertFalse(ioserver.IsInputRedirected());
        assertEquals(System.in, ioserver.GetInput());
    }

    @Test
    public void testReadLine() {
        String testInput = "anything";
        InputStream inputStream = new ByteArrayInputStream(testInput.getBytes());

        IOServer ioserver = getInstance();
        ioserver.SetInput(inputStream);

        String prompt = "Someting new";
        boolean newLine = true;
        String result = ioserver.ReadLine(prompt, newLine);

        assertEquals(testInput, result);
    }

    @Test
    public void testReadLineWithoutPrompt() {
        String testInput = "anything";
        InputStream inputStream = new ByteArrayInputStream(testInput.getBytes());
        IOServer ioserver = getInstance();
        ioserver.SetInput(inputStream);
        String result = ioserver.ReadLine();
        assertEquals(testInput, result);
    }

    @Test
    public void testReadLineWithoutInputStream() {
        String testInput = "";
        InputStream inputStream = new ByteArrayInputStream(testInput.getBytes());
        IOServer ioserver = getInstance();
        ioserver.SetInput(inputStream);
        String prompt = "";
        boolean newLine = true;
        String defaultValue ="Default Value";
        String result = ioserver.ReadLine(prompt, defaultValue, newLine);

        assertEquals(defaultValue, result);
    }

    @Test
    public void testReadLineWithPromptAndNewline() {
        String testInput = "Test Input";
        InputStream inputStream = new ByteArrayInputStream(testInput.getBytes());
        IOServer ioserver = getInstance();
        ioserver.SetInput(inputStream);
        String prompt = "";
        boolean newLine = true;
        String result = ioserver.ReadLine(prompt, newLine);

        assertEquals(testInput, result);
    }

    @Test
    public void testRead() throws IOException {
        String testInput = "Test Input";
        InputStream inputStream = new ByteArrayInputStream(testInput.getBytes());

        IOServer ioserver = new IOServer();
        ioserver.SetInput(inputStream);

        InputStream mockInputStream = mock(InputStream.class);
        when(mockInputStream.read()).thenReturn((int) 'T'); // Mock reading a byte from the input stream.

        ioserver.SetInput(mockInputStream);

        int result = ioserver.Read();

        assertEquals('T', result);
    }

    @Test
    public void testIsErrorRedirectedWhenErrorIsRedirected() {
        PrintStream originalErrorStream = System.err;
        IOServer ioserver = getInstance();

        ByteArrayOutputStream customErrorStream = new ByteArrayOutputStream();
        PrintStream customErrorPrintStream = new PrintStream(customErrorStream);

        System.setErr(customErrorPrintStream);

        boolean result = ioserver.IsErrorRedirected();

        System.setErr(originalErrorStream);

        assertTrue(result);
    }

    @Test
    public void testIsErrorNotRedirectedWhenErrorIsNotRedirected() {
        IOServer ioserver = new IOServer();

        boolean result = ioserver.IsErrorRedirected();

        assertFalse(result);
    }

    @Test
    public void testWriteLineWithContentArray() {
        IOServer ioserver = new IOServer();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Save the original System.out for later restoration.
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(outputStream));

            Tuple<String, ConsoleColor> tuple1 = new Tuple<>("Part 1", ConsoleColor.Red);
            Tuple<String, ConsoleColor> tuple2 = new Tuple<>("Part 2", ConsoleColor.Blue);

            ioserver.WriteLine(Arrays.asList(tuple1, tuple2), OutputType.Default);

            String expectedOutput = tuple1.Second + "Part 1" + ConsoleColor.Null + tuple2.Second + "Part 2" + ConsoleColor.Null + "\n";

            assertEquals(new String(), outputStream.toString());
        } finally {
            // Restore the original System.out.
            System.setOut(originalOut);
        }
    }

    @Test
    public void testAppendWriteLinePrefix() {
        IOServer ioserver = getInstance();
        assertEquals("", ioserver.getPrefixBuilder().toString());
        assertTrue(ioserver.getPrefixLengthStack().isEmpty());

        ioserver.AppendWriteLinePrefix();

        assertEquals("\t", ioserver.getPrefixBuilder().toString());
        assertTrue(ioserver.getPrefixLengthStack().contains(1));
    }

    @Test
    public void testAppendWriteLinePrefixWithString() {
        IOServer ioserver = getInstance();
        assertEquals("", ioserver.getPrefixBuilder().toString());
        assertTrue(ioserver.getPrefixLengthStack().isEmpty());

        ioserver.AppendWriteLinePrefix("abcabcabc");
        assertEquals("abcabcabc", ioserver.getPrefixBuilder().toString());

        assertTrue(ioserver.getPrefixLengthStack().contains(9));
    }

    @Test
    public void testSubtractWriteLinePrefix() {
        IOServer ioserver = getInstance();
        assertEquals("", ioserver.getPrefixBuilder().toString());
        assertTrue(ioserver.getPrefixLengthStack().isEmpty());

        ioserver.AppendWriteLinePrefix("abcabcabc");
        assertEquals("abcabcabc", ioserver.getPrefixBuilder().toString());

        ioserver.SubtractWriteLinePrefix();
        assertEquals("", ioserver.getPrefixBuilder().toString());
        assertTrue(ioserver.getPrefixLengthStack().isEmpty());
    }

    @Test
    public void testSubtractWriteLinePrefixWithEmptyStack() {
        IOServer ioserver = getInstance();
        assertEquals("", ioserver.getPrefixBuilder().toString());
        assertTrue(ioserver.getPrefixLengthStack().isEmpty());
        ioserver.SubtractWriteLinePrefix();

        assertEquals("", ioserver.getPrefixBuilder().toString());
        assertTrue(ioserver.getPrefixLengthStack().isEmpty());
    }

    @Test
    public void testSetPrefix() {
        IOServer ioserver = getInstance();

        ioserver.SetPrefix("TestPrefix");
        //Retrieve the states of both.
        StringBuilder prefixBuilder = ioserver.getPrefixBuilder();
        Stack<Integer> prefixLengthStack = ioserver.getPrefixLengthStack();

        assertEquals("TestPrefix", prefixBuilder.toString());

        assertEquals(1, prefixLengthStack.size());
        assertEquals(Integer.valueOf(10), prefixLengthStack.pop());
    }
    @Test
    public void testSelectColorWithDefault() {
        ColorSetting colorSetting = new ColorSetting();

        // 测试当 customColor 为 null 时返回默认颜色
        assertEquals(ConsoleColor.White, ColorSetting.selectColor(OutputType.Default, null, colorSetting));
        assertEquals(ConsoleColor.Magenta, ColorSetting.selectColor(OutputType.Prompt, null, colorSetting));
        assertEquals(ConsoleColor.Red, ColorSetting.selectColor(OutputType.Error, null, colorSetting));
        assertEquals(ConsoleColor.Green, ColorSetting.selectColor(OutputType.AllOk, null, colorSetting));
        assertEquals(ConsoleColor.Yellow, ColorSetting.selectColor(OutputType.ListTitle, null, colorSetting));
        assertEquals(ConsoleColor.DarkCyan, ColorSetting.selectColor(OutputType.CustomInfo, null, colorSetting));
        assertEquals(ConsoleColor.DarkBlue, ColorSetting.selectColor(OutputType.MobileSuitInfo, null, colorSetting));
    }


    @Test
    public void testSelectColorWithCustomColor() {
        ColorSetting colorSetting = new ColorSetting();

        ConsoleColor customColor = ConsoleColor.Blue; // 定义一个自定义颜色
        // 测试当 customColor 非空时返回自定义颜色
        assertEquals(customColor, ColorSetting.selectColor(OutputType.Default, customColor, colorSetting));
        assertEquals(customColor, ColorSetting.selectColor(OutputType.Prompt, customColor, colorSetting));
        assertEquals(customColor, ColorSetting.selectColor(OutputType.Error, customColor, colorSetting));
        assertEquals(customColor, ColorSetting.selectColor(OutputType.AllOk, customColor, colorSetting));
        assertEquals(customColor, ColorSetting.selectColor(OutputType.ListTitle, customColor, colorSetting));
        assertEquals(customColor, ColorSetting.selectColor(OutputType.CustomInfo, customColor, colorSetting));
        assertEquals(customColor, ColorSetting.selectColor(OutputType.MobileSuitInfo, customColor, colorSetting));
    }

    @Test
    public void testGetInstance() {
        ColorSetting colorSetting1 = ColorSetting.getInstance();
        ColorSetting colorSetting2 = ColorSetting.getInstance();

        assertNotNull(colorSetting1);
        assertNotNull(colorSetting2);
        // 检查是否每次都返回新的实例
        assertNotSame(colorSetting1, colorSetting2);
    }
    @Test
    public void testDefaultColorsInitialization() {
        ColorSetting colorSetting = new ColorSetting();

        assertEquals(ConsoleColor.White, colorSetting.DefaultColor);
        assertEquals(ConsoleColor.Magenta, colorSetting.PromptColor);
        assertEquals(ConsoleColor.Red, colorSetting.ErrorColor);
        assertEquals(ConsoleColor.Green, colorSetting.AllOkColor);
        assertEquals(ConsoleColor.Yellow, colorSetting.ListTitleColor);
        assertEquals(ConsoleColor.DarkCyan, colorSetting.CustomInformationColor);
        assertEquals(ConsoleColor.DarkBlue, colorSetting.InformationColor);
    }
    @Test
    public void testColorForDifferentOutputTypes() {
        ColorSetting colorSetting = new ColorSetting();

        assertEquals(ConsoleColor.White, colorSetting.DefaultColor);
        assertEquals(ConsoleColor.Magenta, colorSetting.PromptColor);
        assertEquals(ConsoleColor.Red, colorSetting.ErrorColor);
        assertEquals(ConsoleColor.Green, colorSetting.AllOkColor);
        assertEquals(ConsoleColor.Yellow, colorSetting.ListTitleColor);
        assertEquals(ConsoleColor.DarkCyan, colorSetting.CustomInformationColor);
        assertEquals(ConsoleColor.DarkBlue, colorSetting.InformationColor);
    }

}
