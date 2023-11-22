package PlasticMetal.JMobileSuitLite;

import PlasticMetal.JMobileSuitLite.IO.*;
import PlasticMetal.Jarvis.ObjectModel.Tuple;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import PlasticMetal.JMobileSuitLite.IO.OutputType;

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

    private static class TestInstance {
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
        method.invoke(suitHost,null);
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
        method.invoke(suitHost,"java.lang.AssertionError");
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

        // Get a private method by reflection
        Method method = SuitHost.class.getDeclaredMethod("UpdatePrompt", String.class);
        method.setAccessible(true);

        // Call the method
        String result = (String) method.invoke(host, prompt);

        // Verify the results
        assertEquals(result, "Test[Instance1.Instance2]");
    }

    @Test
    public void testUpdatePrompt_empty() throws Exception {

        // Create a SuitHost instance
        String prompt = "";

        Object instance = new TestInstance();
        SuitConfiguration config = SuitConfiguration.getInstance();
        SuitHost host = new SuitHost(instance, config);

        host.InstanceNameString.add("Instance");

        // Get a private method by reflection
        Method method = SuitHost.class.getDeclaredMethod("UpdatePrompt", String.class);
        method.setAccessible(true);

        String result = (String) method.invoke(host, prompt);

        assertEquals(result, "PlasticMetal.JMobileSuitLite.SuitHostTests$TestInstance[Instance]");
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
        Assert.assertEquals(TraceBack.AllOk, result);

        // Test Annotation command
        result = suitHost.RunCommand("", "# This is a comment");
        Assert.assertEquals(TraceBack.AllOk, result);

        // Test the built-in command
        result = suitHost.RunCommand("", "@internal command");
        Assert.assertEquals(TraceBack.ObjectNotFound, result);

        // Test object command
        result = suitHost.RunCommand("", "object command");
        Assert.assertEquals(TraceBack.ObjectNotFound, result);

        // Test invalid command
        result = suitHost.RunCommand("", null);
        Assert.assertEquals(TraceBack.AllOk, result);

    }

    @Test
    public void testRunCommand_2() throws Exception {
        // Create a SuitHost instance
        Object instance = new TestInstance();
        SuitConfiguration config = SuitConfiguration.getInstance();
        SuitHost suitHost = new SuitHost(instance, config);

        // Test the null command
        TraceBack result = suitHost.RunCommand("");
        Assert.assertEquals(TraceBack.AllOk, result);

        // Test Annotation command
        result = suitHost.RunCommand("# This is a comment");
        Assert.assertEquals(TraceBack.AllOk, result);

        // Test the built-in command
        result = suitHost.RunCommand("@internal command");
        Assert.assertEquals(TraceBack.ObjectNotFound, result);

        // Test object command
        result = suitHost.RunCommand("object command");
        Assert.assertEquals(TraceBack.ObjectNotFound, result);

        // Test invalid command
        result = suitHost.RunCommand(null);
        Assert.assertEquals(TraceBack.AllOk, result);

    }

}