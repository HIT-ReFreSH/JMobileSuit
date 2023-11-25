package PlasticMetal.JMobileSuitLite.IO;

import PlasticMetal.JMobileSuitLite.IO.*;
import PlasticMetal.JMobileSuitLite.SuitConfiguration;
import PlasticMetal.Jarvis.ObjectModel.Tuple;
import org.apache.logging.log4j.core.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;



public class IOServerTests {

//    private final Logger Logger;
//
//    public IOServerTests(Logger logger) {
//        Logger = logger;
//    }

    private IOServer getInstance() {
        return new IOServer();
    }

    private IOServer getInstance(PromptServer promptServer,Logger logger,ColorSetting colorSetting) {
        return new IOServer(promptServer,logger,colorSetting);
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

        // 验证模拟对象的 LogDebug 方法是否被调用，并且传入的参数是否符合预期
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
        // 验证模拟对象的 LogException 方法是否被调用，并且传入的参数是正确的异常对象
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

        verify(mockLogger, times(1)).LogException(testException);
    }

    /**
     * set测试必须在get测试方法前
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

        // 验证 _inputScanner 是否被正确更新
//        verify(mockScanner).close();  // 检查 Scanner 是否被关闭过一次，确保重新创建了新的 Scanner 对象
//        try {
//            verifyNew(Scanner.class).withNoArguments();  // 验证 Scanner 构造函数是否被正确调用
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

        // 检查是否成功重置为 System.in
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

    //TODO: Method Overloading test need to be added


    @Test
    public void testRead() throws IOException {
        String testInput = "Test Input";
        InputStream inputStream = new ByteArrayInputStream(testInput.getBytes());

        IOServer ioserver = new IOServer();
        ioserver.SetInput(inputStream);

        InputStream mockInputStream = mock(InputStream.class);
        when(mockInputStream.read()).thenReturn((int) 'T'); // 模拟从输入流中读取一个字节

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

        // 保存原始的 System.out，以便后续恢复
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(outputStream));

            Tuple<String, ConsoleColor> tuple1 = new Tuple<>("Part 1", ConsoleColor.Red);
            Tuple<String, ConsoleColor> tuple2 = new Tuple<>("Part 2", ConsoleColor.Blue);

            ioserver.WriteLine(Arrays.asList(tuple1, tuple2), OutputType.Default);

            String expectedOutput = tuple1.Second + "Part 1" + ConsoleColor.Null + tuple2.Second + "Part 2" + ConsoleColor.Null + "\n";

            assertEquals(new String(), outputStream.toString());
        } finally {
            // 恢复原始的 System.out
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
        // 获取两者状态
        StringBuilder prefixBuilder = ioserver.getPrefixBuilder();
        Stack<Integer> prefixLengthStack = ioserver.getPrefixLengthStack();

        assertEquals("TestPrefix", prefixBuilder.toString());

        assertEquals(1, prefixLengthStack.size());
        assertEquals(Integer.valueOf(10), prefixLengthStack.pop());
    }


}