// src/test/java/ReFreSH/JMobileSuit/BuildInCommandServerTest.java
package ReFreSH.JMobileSuit;

import ReFreSH.JMobileSuit.ObjectModel.SuitObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class BuildInCommandServerTest {

    @Test
    public void testList() {
        SuitHost mockSuitHost = mock(SuitHost.class);
        BuildInCommandServer instance = new BuildInCommandServer(mockSuitHost);
        mockSuitHost.Current = null;
        assertEquals(TraceBack.InvalidCommand, instance.List(null));
        mockSuitHost.Current = mock(SuitObject.class);
        Object object = new Object();
        SuitHost suitHost;
        try {
            suitHost = new SuitHost(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        instance = new BuildInCommandServer(suitHost);
        assertEquals(TraceBack.AllOk, instance.List(null));
    }

    @Test
    public void testRunScript() {
        SuitHost suitHost;
        Object object = new Object();
        try {
            suitHost = new SuitHost(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        suitHost.Current = mock(SuitObject.class);
        BuildInCommandServer instance = new BuildInCommandServer(suitHost);
        String[] args = {};
        assertEquals(TraceBack.InvalidCommand, instance.RunScript(args));
        String[] args1 = {"1"};
        assertEquals(TraceBack.InvalidCommand, instance.RunScript(args1)); // Incorrect file path
        args1[0] = "src/test/resources/SuitHostTests.mss";
        assertEquals(TraceBack.AllOk, instance.RunScript(args1));
    }

    @Test
    public void testExit() {
        SuitHost mockSuitHost = mock(SuitHost.class);
        BuildInCommandServer instance = new BuildInCommandServer(mockSuitHost);
        assertEquals(TraceBack.OnExit, instance.Exit(null));
    }

    @Test
    public void testHelp() {
        SuitHost suitHost;
        Object object = new Object();
        try {
            suitHost = new SuitHost(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        BuildInCommandServer instance = new BuildInCommandServer(suitHost);
        assertEquals(TraceBack.AllOk, instance.Help(null));
    }

    @Test
    public void testListMembers() {
        SuitHost suitHost;
        Object object = new Object();
        try {
            suitHost = new SuitHost(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        BuildInCommandServer instance = new BuildInCommandServer(suitHost);
        SuitObject obj = new SuitObject(object);
        instance.ListMembers(obj);
    }
}
