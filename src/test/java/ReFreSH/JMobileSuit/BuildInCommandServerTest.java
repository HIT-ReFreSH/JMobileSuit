// src/test/java/ReFreSH/JMobileSuit/BuildInCommandServerTest.java
package ReFreSH.JMobileSuit;

import ReFreSH.JMobileSuit.ObjectModel.SuitObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

// Class to test the functionality of BuildInCommandServer
public class BuildInCommandServerTest {

    @Test      // Test method for the List command
    public void testList() {
        SuitHost mockSuitHost = mock(SuitHost.class);
        BuildInCommandServer instance = new BuildInCommandServer(mockSuitHost);
        mockSuitHost.Current = null;
        assertEquals(TraceBack.InvalidCommand, instance.List(null));       // Assert that the List command returns InvalidCommand when no current SuitObject is set
        mockSuitHost.Current = mock(SuitObject.class);
        Object object = new Object();
        SuitHost suitHost;
        try {
            suitHost = new SuitHost(object);
        } catch (Exception e) {
            throw new RuntimeException(e);    // Throw a runtime exception if an error occurs
        }
        instance = new BuildInCommandServer(suitHost);
        assertEquals(TraceBack.AllOk, instance.List(null));
    }

    @Test          // Test method for the RunScript command
    public void testRunScript() {
        SuitHost suitHost;
        Object object = new Object();
        try {
            suitHost = new SuitHost(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        suitHost.Current = mock(SuitObject.class);   // Set the Current property of suitHost to a mock SuitObject
        BuildInCommandServer instance = new BuildInCommandServer(suitHost);     // Instantiate BuildInCommandServer with the SuitHost
        String[] args = {};         // Test with an empty array of arguments, expecting InvalidCommand
        assertEquals(TraceBack.InvalidCommand, instance.RunScript(args));
        String[] args1 = {"1"};
        assertEquals(TraceBack.InvalidCommand, instance.RunScript(args1)); // Incorrect file path
        args1[0] = "src/test/resources/SuitHostTests.mss";          // Set the file path to a valid one and expect AllOk
        assertEquals(TraceBack.AllOk, instance.RunScript(args1));
    }

    @Test      // Test method for the Exit command
    public void testExit() {
        SuitHost mockSuitHost = mock(SuitHost.class);
        BuildInCommandServer instance = new BuildInCommandServer(mockSuitHost);
        assertEquals(TraceBack.OnExit, instance.Exit(null));
    }

    @Test           // Test method for the Help command
    public void testHelp() {
        SuitHost suitHost;
        Object object = new Object();
        try {
            suitHost = new SuitHost(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        BuildInCommandServer instance = new BuildInCommandServer(suitHost);
        assertEquals(TraceBack.AllOk, instance.Help(null));            // Assert that the Help command returns AllOk
    }

    @Test        // Test method for the ListMembers command
    public void testListMembers() {
        SuitHost suitHost;          // Create a generic Object and try to instantiate a SuitHost with it (wrapped in a try-catch for error handling)
        Object object = new Object();
        try {
            suitHost = new SuitHost(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        BuildInCommandServer instance = new BuildInCommandServer(suitHost);
        SuitObject obj = new SuitObject(object);
        instance.ListMembers(obj);         // Call the ListMembers method with the SuitObject
    }
}
