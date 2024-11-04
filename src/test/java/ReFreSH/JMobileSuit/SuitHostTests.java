package ReFreSH.JMobileSuit;

import ReFreSH.JMobileSuit.IO.IOServer;
import ReFreSH.JMobileSuit.IO.OutputType;
import ReFreSH.JMobileSuit.ObjectModel.IOInteractive;
import ReFreSH.JMobileSuit.ObjectModel.SuitApplication;
import ReFreSH.Jarvis.ObjectModel.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SuitHostTests {

    @Test
    public void testConstructor_withInstance() throws Exception {
        Object instance = new TestInstance();

        SuitHost suitHost = new SuitHost(instance);

        assertEquals(instance, suitHost.WorkInstance());
    }

    @Test
    public void testConstructor_withConfiguration() throws Exception {
        Object instance = new TestInstance();
        SuitConfiguration config = SuitConfiguration.getInstance();

        SuitHost suitHost = new SuitHost(instance, config);

        assertEquals(config, suitHost.Configuration);
    }

    @Test
    public void testConstructor_withType_Configuration() throws Exception {
        Class<?> type = Object.class;
        SuitConfiguration config = SuitConfiguration.getInstance();

        SuitHost suitHost = new SuitHost(type, config);
        assertEquals(config, suitHost.Configuration);
    }

    @Test
    public void testSplitCommandLine() throws Exception {
        // Get a private method by reflection
        Class<?> clazz = SuitHost.class;
        Method method = clazz.getDeclaredMethod("SplitCommandLine", String.class);
        method.setAccessible(true);

        // Test the normal commands
        String cmd = "command arg1 arg2";
        String[] expected = {"command", "arg1", "arg2"};
        String[] actual = (String[]) method.invoke(null, cmd);
        assertArrayEquals(expected, actual);

        // Test quoted parameters with "
        cmd = "command \"arg1\" arg2";
        expected = new String[]{"command", "arg1", "arg2"};
        actual = (String[]) method.invoke(null, cmd);
        assertArrayEquals(expected, actual);

        // Test quoted parameters with \'
        cmd = "command \'arg1\' arg2";
        expected = new String[]{"command", "arg1", "arg2"};
        actual = (String[]) method.invoke(null, cmd);
        assertArrayEquals(expected, actual);

        // Test the null command
        cmd = "";
        expected = null;
        actual = (String[]) method.invoke(null, cmd);
        assertArrayEquals(expected, actual);

        cmd = null;
        actual = (String[]) method.invoke(null, cmd);
        assertArrayEquals(expected, actual);

    }

    //test SetShowReference(boolean value) and GetShowReference()
    @Test
    public void testConstructor_ShowReference() throws Exception {
        Object instance = new TestInstance();
        SuitHost suitHost = new SuitHost(instance);

        assertTrue(suitHost.GetShowReference());

        suitHost.SetShowReference(false);
        assertFalse(suitHost.GetShowReference());
    }

    //test GetUseTraceBack()
    @Test
    public void testConstructor_GetUseTraceBack() throws Exception {
        Object instance = new TestInstance();
        SuitHost suitHost = new SuitHost(instance);

        assertTrue(suitHost.GetUseTraceBack());
    }

    //test GetShowDone()
    @Test
    public void testConstructor_GetShowDone() throws Exception {
        Object instance = new TestInstance();
        SuitHost suitHost = new SuitHost(instance);

        assertFalse(suitHost.GetShowDone());
    }

    @Test
    public void testConstructor_WorkInstanceInit() throws Exception {
        IOInteractive ioInteractive = new SuitApplication();
        SuitHost suitHost = new SuitHost(ioInteractive);
        suitHost.WorkInstanceInit();

        assertEquals(ioInteractive,suitHost.WorkInstance());
    }

    @Test
    public void testNotifyAllOk() throws Exception {

        // Create a SuitHost instance
        Object instance = new TestInstance();
        SuitConfiguration config = SuitConfiguration.getInstance();
        SuitHost suitHost = new SuitHost(instance, config);

        suitHost.SetShowDone(true);
        suitHost.SetUseTraceBack(true);

        // Get a private method by reflection
        Method method = SuitHost.class.getDeclaredMethod("NotifyAllOk", null);
        method.setAccessible(true);

        // Call the method
        method.invoke(suitHost, null);
    }


    @Test
    public void testNotifyError() throws Exception {

        // Create a SuitHost instance
        Object instance = new TestInstance();
        SuitConfiguration config = SuitConfiguration.getInstance();
        SuitHost suitHost = new SuitHost(instance, config);

        suitHost.SetUseTraceBack(true);

        // Get a private method by reflection
        Method method = SuitHost.class.getDeclaredMethod("NotifyError", String.class);
        method.setAccessible(true);

        // Call the method
        method.invoke(suitHost, "java.lang.AssertionError");

        suitHost.SetUseTraceBack(false);
    }

    @Test
    public void testUpdatePrompt() throws Exception {

        // Create a SuitHost instance
        String prompt = "Test";

        Object instance = new TestInstance();
        SuitConfiguration config = SuitConfiguration.getInstance();
        SuitHost host = new SuitHost(instance, config);

        host.InstanceNameString.add("Instance1");
        host.InstanceNameString.add("Instance2");
        host.InstanceNameString.add("Instance3");

        // Get a private method by reflection
        Method method = SuitHost.class.getDeclaredMethod("UpdatePrompt", String.class);
        method.setAccessible(true);

        // Call the method
        String result = (String) method.invoke(host, prompt);

        // Verify the results
        assertEquals(result, "Test[Instance1.Instance2.Instance3]");

        host.InstanceNameString.remove("Instance2");
        host.InstanceNameString.remove("Instance3");
        String result1 = (String) method.invoke(host, prompt);
        assertEquals(result1, "Test[Instance1]");

        //_showReference==false
        host.SetShowReference(false);
        String result2 = (String) method.invoke(host, prompt);
        assertEquals(result2, "Test");
    }

    @Test
    public void testUpdatePrompt_empty() throws Exception {

        // Create a SuitHost instance
        String prompt = "";

        Object instance = new TestInstance();
        IOInteractive ioInteractive = new SuitApplication();
        SuitConfiguration config = SuitConfiguration.getInstance();

        SuitHost host = new SuitHost(instance, config);
        SuitHost host1 = new SuitHost(ioInteractive, config);

        host.InstanceNameString.add("Instance");

        // Get a private method by reflection
        Method method = SuitHost.class.getDeclaredMethod("UpdatePrompt", String.class);
        method.setAccessible(true);

        String result = (String) method.invoke(host, prompt);
        assertEquals(result, "ReFreSH.JMobileSuit.SuitHostTests$TestInstance[Instance]");

        //(WorkInstance() instanceof InfoProvider) ==true
        host1.InstanceNameString.add("Instance");
        String result1 = (String) method.invoke(host1, prompt);
        assertEquals(result1, "[Instance]");
    }

    @Test
    public void testRunBuildInCommand() throws Exception {

        // Create a SuitHost instance
        Object instance = new TestInstance();
        SuitConfiguration config = SuitConfiguration.getInstance();
        SuitHost host = new SuitHost(instance, config);

        String[] cmdList = {"command", "param1", "param2"};

        Method method = SuitHost.class.getDeclaredMethod("RunBuildInCommand", String[].class);
        System.out.println(method);
        method.setAccessible(true);

        TraceBack result = (TraceBack) method.invoke(host, (Object) cmdList);

        assertEquals(TraceBack.ObjectNotFound, result);
    }

    @Test
    public void testRunBuildInCommand_invalid() throws Exception {

        // Create a SuitHost instance
        Object instance = new TestInstance();
        SuitConfiguration config = SuitConfiguration.getInstance();
        SuitHost host = new SuitHost(instance, config);

        String[] cmdList = null;

        Method method = SuitHost.class.getDeclaredMethod("RunBuildInCommand", String[].class);
        method.setAccessible(true);

        Object result = method.invoke(host, (Object) cmdList);

        assertEquals(TraceBack.InvalidCommand, result);
    }

    @Test
    public void testRunObject() throws Exception {

        // Create a SuitHost instance
        Object instance = new TestInstance();
        SuitConfiguration config = SuitConfiguration.getInstance();
        SuitHost suitHost = new SuitHost(instance, config);

        // Parameters to create a private method
        String[] args = {"arg1", "arg2"};

        // Get a private method by reflection
        Method method = SuitHost.class.getDeclaredMethod("RunObject", String[].class);
        method.setAccessible(true); // Set up private methods to be accessible

        // Call the private method
        Object result = method.invoke(suitHost, (Object) args);

        assertNotNull(result);

        assertTrue(result instanceof Tuple);
        Tuple tupleResult = (Tuple) result;

        TraceBack traceBack = (TraceBack) tupleResult.First;
        assertNotNull(traceBack);
        assertEquals(TraceBack.ObjectNotFound, tupleResult.First);
        assertEquals(null, tupleResult.Second);
    }

    @Test
    public void testRunCommand() throws Exception {
        // Create a SuitHost instance
        Object instance = new TestInstance();
        SuitConfiguration config = SuitConfiguration.getInstance();
        SuitHost suitHost = new SuitHost(instance, config);

        // Test the null command
        TraceBack result = suitHost.RunCommand("", "");
        assertEquals(TraceBack.AllOk, result);

        // Test Annotation command
        result = suitHost.RunCommand("", "# This is a comment");
        assertEquals(TraceBack.AllOk, result);

        // Test the built-in command
        result = suitHost.RunCommand("", "@internal command");
        assertEquals(TraceBack.ObjectNotFound, result);

        // Test object command
        result = suitHost.RunCommand("", "object command");
        assertEquals(TraceBack.ObjectNotFound, result);

        // Test invalid command
        result = suitHost.RunCommand("", null);
        assertEquals(TraceBack.AllOk, result);

    }

    @Test
    public void testRunCommand_2() throws Exception {
        // Create a SuitHost instance
        Object instance = new TestInstance();
        SuitConfiguration config = SuitConfiguration.getInstance();
        SuitHost suitHost = new SuitHost(instance, config);

        // Test the null command
        TraceBack result = suitHost.RunCommand("");
        assertEquals(TraceBack.AllOk, result);

        // Test Annotation command
        result = suitHost.RunCommand("# This is a comment");
        assertEquals(TraceBack.AllOk, result);

        // Test the built-in command
        result = suitHost.RunCommand("@internal command");
        assertEquals(TraceBack.ObjectNotFound, result);

        // Test object command
        result = suitHost.RunCommand("object command");
        assertEquals(TraceBack.ObjectNotFound, result);

        // Test invalid command
        result = suitHost.RunCommand(null);
        assertEquals(TraceBack.AllOk, result);

    }

    //test logException
    @Test
    public void testlogException() throws Exception {
        // Create a SuitHost instance
        Object instance = new TestInstance();
        SuitConfiguration config = SuitConfiguration.getInstance();
        SuitHost suitHost = new SuitHost(instance, config);

        Exception ClassNotFoundException = new Exception();
        suitHost.logException(ClassNotFoundException);
    }

    private static class TestInstance {
    }

    private class TestIOServer extends IOServer {

        private StringWriter output = new StringWriter();

        public PrintWriter getOutput() {
            return new PrintWriter(output);
        }

        @Override
        public void WriteLine(String message, OutputType type) {
            getOutput().println(message);
        }

    }

}
