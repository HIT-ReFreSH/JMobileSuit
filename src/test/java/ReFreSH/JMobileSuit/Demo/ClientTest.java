package ReFreSH.JMobileSuit.Demo;

import ReFreSH.JMobileSuit.Demo.Client.GoodMorningParameter;
import ReFreSH.JMobileSuit.Demo.Client.SleepArgument;
import ReFreSH.JMobileSuit.IO.IOServer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class ClientTest {

    private Client client;
    private IOServer mockIoServer;

    @Before
    public void setUp() {
        client = new Client();
        mockIoServer = Mockito.mock(IOServer.class);
        client.setIO(mockIoServer);
    }

    @Test
    public void testHello() {
        client.Hello();
        Mockito.verify(mockIoServer).WriteLine("Hello! MobileSuit!");
    }

    @Test(expected = Exception.class)
    public void testByeThrowsException() throws Exception {
        client.exp(); // 直接调用会抛出异常
    }


    @Test
    public void testBye() {
        String result = client.Bye("John");
        assertEquals("bye", result);
        Mockito.verify(mockIoServer).WriteLine("Bye!John");
    }

    @Test
    public void testGoodMorning() {
        GoodMorningParameter param = new GoodMorningParameter();
        param.name = "Alice";
        client.GoodMorning(param);
        Mockito.verify(mockIoServer).WriteLine("Good morning,Alice");
    }

    @Test
    public void testGoodMorning2() {
        GoodMorningParameter param = new GoodMorningParameter();
        param.name = "Alice";
        client.GoodMorning2("Bob", param);
        Mockito.verify(mockIoServer).WriteLine("Good morning, Bob and Alice");
    }

    @Test
    public void testGoodMorningParameterParseFailure() {
        GoodMorningParameter param = new GoodMorningParameter();
        String[] options = {"Alice", "Bob"}; // 超过一个参数
        assertFalse(param.parse(options));
        assertEquals(param.name, "foo"); // 默认值未更改
    }


    @Test
    public void testGoodEvening() {
        String[] args = {"Alice", "Bob"};
        client.GoodEvening(args);
        Mockito.verify(mockIoServer).WriteLine("Good Evening, Alice");
    }


    @Test
    public void testGoodEveningNoArgs() {
        String[] args = {}; // 没有参数
        client.GoodEvening(args);
        Mockito.verify(mockIoServer).WriteLine("Good Evening, "); // 确保输出
    }


    @Test
    public void testShowNumber() {
        int i = 5;
        int[] j = {10};
        client.ShowNumber(i, j);
        Mockito.verify(mockIoServer).WriteLine("5");
        Mockito.verify(mockIoServer).WriteLine("10");
    }

    @Test
    public void testGoodEvening2() {
        String[] args = {"Alice"};
        client.GoodEvening2("Bob", args);
        Mockito.verify(mockIoServer).WriteLine("Good Evening, Bob and Alice");
    }

    @Test
    public void testSleep() {
        SleepArgument argument = new SleepArgument();
        argument.Name.add("Alice");
        argument.SleepTime = 5;
        argument.isSleeping = true;

        client.Sleep(argument);
        Mockito.verify(mockIoServer).WriteLine("Alice has been sleeping for 5 hour(s).");
    }

    // Additional tests for other methods can be added here...
    @Test
    public void testSleep2() {
        SleepArgument argument = new SleepArgument();
        argument.Name.add("Bob");
        argument.isSleeping = false;

        client.Sleep(argument);
        Mockito.verify(mockIoServer).WriteLine("Bob is not sleeping.");
    }

    @Test
    public void testSleepNoArgs() {
        SleepArgument argument = new SleepArgument(); // 默认构造
        client.Sleep(argument);
        Mockito.verify(mockIoServer).WriteLine(argument.Name.get(0) + " is not sleeping."); // 确保输出
    }

    @Test
    public void testShowNumberEmptyArray() {
        int i = 5;
        int[] j = {}; // 空数组
        client.ShowNumber(i, j);
        Mockito.verify(mockIoServer).WriteLine("5");
        Mockito.verify(mockIoServer).WriteLine(""); // 输出空行
    }


    @Test
    public void testParse() {
        GoodMorningParameter param = new GoodMorningParameter();
        String[] options = {"Alice"};
        assertTrue(param.parse(options));
        assertEquals(param.name, "Alice");
        String[] options2 = {""};
        String[] options3 = {"Alice", "Bob"};
        assertTrue(param.parse(options2));
        assertFalse(param.parse(options3));
        assertEquals(param.name,"");

    }
}

