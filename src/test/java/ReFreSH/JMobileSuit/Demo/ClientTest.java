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
        // Initialize the Client and mock IOServer
        client = new Client();
        mockIoServer = Mockito.mock(IOServer.class);
        client.setIO(mockIoServer);
    }

    private void verifyGoodEvening(String expectedOutput, String... args) {
        client.GoodEvening(args);
        Mockito.verify(mockIoServer).WriteLine(expectedOutput);
    }

    @Test
    public void testHello() {
        // Test the Hello method
        client.Hello();
        Mockito.verify(mockIoServer).WriteLine("Hello! MobileSuit!");
    }

    @Test(expected = Exception.class)
    public void testByeThrowsException() throws Exception {
        // Directly calling exp() will throw an exception
        client.exp();
    }

    @Test
    public void testBye() {
        // Test the Bye method with a name parameter
        String result = client.Bye("John");
        assertEquals("bye", result);
        Mockito.verify(mockIoServer).WriteLine("Bye!John");
    }

    @Test
    public void testGoodMorningWithSingleName() {
        // Test the GoodMorning method with a single name parameter
        GoodMorningParameter param = new GoodMorningParameter();
        param.name = "Alice";
        client.GoodMorning(param);
        Mockito.verify(mockIoServer).WriteLine("Good morning,Alice");
    }

    @Test
    public void testGoodMorningWithTwoNames() {
        // Test the GoodMorning2 method with two name parameters
        GoodMorningParameter param = new GoodMorningParameter();
        param.name = "Alice";
        client.GoodMorning2("Bob", param);
        Mockito.verify(mockIoServer).WriteLine("Good morning, Bob and Alice");
    }

    @Test
    public void testGoodMorningParameterParseFailure() {
        // Test parsing failure for the GoodMorningParameter
        GoodMorningParameter param = new GoodMorningParameter();
        String[] options = {"Alice", "Bob"}; // More than one parameter
        assertFalse(param.parse(options));
        assertEquals(param.name, "defaultName"); // Ensure default value is set correctly
    }

    @Test
    public void testGoodEvening() {
        // Test the GoodEvening method with arguments
        String[] args = {"Alice", "Bob"};
        verifyGoodEvening("Good Evening, Alice", args);
    }

    @Test
    public void testGoodEveningNoArgs() {
        // Test the GoodEvening method with no arguments
        String[] args = {}; // No parameters
        verifyGoodEvening("Good Evening, ", args);
    }

    @Test
    public void testShowNumber() {
        // Test showing a number and an array of numbers
        int i = 5;
        int[] j = {10};
        client.ShowNumber(i, j);
        Mockito.verify(mockIoServer).WriteLine("5");
        Mockito.verify(mockIoServer).WriteLine("10");
        Mockito.verifyNoMoreInteractions(mockIoServer);
    }

    @Test
    public void testGoodEvening2() {
        // Test the GoodEvening2 method with two name parameters
        String[] args = {"Alice"};
        client.GoodEvening2("Bob", args);
        Mockito.verify(mockIoServer).WriteLine("Good Evening, Bob and Alice");
    }

    @Test
    public void testSleep() {
        // Test the Sleep method with sleeping argument
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
        // Test the Sleep method with a non-sleeping argument
        SleepArgument argument = new SleepArgument();
        argument.Name.add("Bob");
        argument.isSleeping = false;

        client.Sleep(argument);
        Mockito.verify(mockIoServer).WriteLine("Bob is not sleeping.");
    }

    @Test
    public void testSleepNoArgs() {
        // Test the Sleep method with default arguments
        SleepArgument argument = new SleepArgument(); // Default constructor
        client.Sleep(argument);
        Mockito.verify(mockIoServer).WriteLine(argument.Name.get(0) + " is not sleeping."); // Ensure output
    }

    @Test
    public void testShowNumberEmptyArray() {
        // Test showing a number with an empty array
        int i = 5;
        int[] j = {}; // Empty array
        client.ShowNumber(i, j);
        Mockito.verify(mockIoServer).WriteLine("5");
        Mockito.verify(mockIoServer).WriteLine("");
        Mockito.verifyNoMoreInteractions(mockIoServer);
    }

    @Test
    public void testParse() {
        // Test parsing of GoodMorningParameter with various options
        GoodMorningParameter param = new GoodMorningParameter();
        String[] options = {"Alice"};
        assertTrue(param.parse(options));
        assertEquals(param.name, "Alice");
        String[] options2 = {""};
        String[] options3 = {"Alice", "Bob"};
        assertTrue(param.parse(options2));
        assertFalse(param.parse(options3));
        assertEquals(param.name, "");
    }
}
