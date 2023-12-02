package ReFreSH.JMobileSuit.Demo;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ReFreSH.JMobileSuit.IO.IOServer;
import ReFreSH.JMobileSuit.Demo.Client.GoodMorningParameter;
import ReFreSH.JMobileSuit.Demo.Client.SleepArgument;

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
        Mockito.verify(mockIoServer).WriteLine("Good morning, Alice");
    }

    @Test
    public void testGoodMorning2() {
        GoodMorningParameter param = new GoodMorningParameter();
        param.name = "Alice";
        client.GoodMorning2("Bob", param);
        Mockito.verify(mockIoServer).WriteLine("Good morning, Bob and Alice");
    }

    @Test
    public void testGoodEvening() {
        String[] args = {"Alice", "Bob"};
        client.GoodEvening(args);
        Mockito.verify(mockIoServer).WriteLine("Good Evening, Alice");
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
}

