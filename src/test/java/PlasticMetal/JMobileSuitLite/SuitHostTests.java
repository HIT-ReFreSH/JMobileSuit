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
        // 通过反射获取私有方法
        Class<?> clazz = SuitHost.class;
        Method method = clazz.getDeclaredMethod("SplitCommandLine", String.class);
        method.setAccessible(true);

        // 测试正常命令
        String cmd = "command arg1 arg2";
        String[] expected = {"command", "arg1", "arg2"};
        String[] actual = (String[]) method.invoke(null, cmd);
        assertArrayEquals(expected, actual);

        // 测试带引号参数
        cmd = "command \"arg1\" arg2";
        expected = new String[]{"command", "arg1", "arg2"};
        actual = (String[]) method.invoke(null, cmd);
        assertArrayEquals(expected, actual);

        // 测试带引号参数
        cmd = "command \'arg1\' arg2";
        expected = new String[]{"command", "arg1", "arg2"};
        actual = (String[]) method.invoke(null, cmd);
        assertArrayEquals(expected, actual);

        // 测试空命令
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

        // 创建 SuitHost 实例
        Object instance = new TestInstance();
        SuitConfiguration config = SuitConfiguration.getInstance();
        SuitHost suitHost = new SuitHost(instance, config);

        suitHost.SetShowDone(true);
        suitHost.SetUseTraceBack(true);

        // 使用反射获取私有方法
        Method method = SuitHost.class.getDeclaredMethod("NotifyAllOk", null);
        method.setAccessible(true);

        // 调用方法
        method.invoke(suitHost,null);
    }

    @Test
    public void testNotifyError() throws Exception {

        // 创建 SuitHost 实例
        Object instance = new TestInstance();
        SuitConfiguration config = SuitConfiguration.getInstance();
        SuitHost suitHost = new SuitHost(instance, config);

        suitHost.SetUseTraceBack(true);

        // 使用反射获取私有方法
        Method method = SuitHost.class.getDeclaredMethod("NotifyError", String.class);
        method.setAccessible(true);

        // 调用方法
        method.invoke(suitHost,"java.lang.AssertionError");
    }



    @Test
    public void testUpdatePrompt() throws Exception {

        // 准备测试数据
        String prompt = "Test";

        Object instance = new TestInstance();
        SuitConfiguration config = SuitConfiguration.getInstance();
        SuitHost host = new SuitHost(instance, config);

        host.InstanceNameString.add("Instance1");
        host.InstanceNameString.add("Instance2");

        // 使用反射获取私有方法
        Method method = SuitHost.class.getDeclaredMethod("UpdatePrompt", String.class);
        method.setAccessible(true);

        // 调用方法
        String result = (String) method.invoke(host, prompt);

        // 验证结果
        assertEquals(result, "Test[Instance1.Instance2]");
    }

    @Test
    public void testUpdatePrompt_empty() throws Exception {

        // 准备测试数据
        String prompt = "";

        Object instance = new TestInstance();
        SuitConfiguration config = SuitConfiguration.getInstance();
        SuitHost host = new SuitHost(instance, config);

        host.InstanceNameString.add("Instance");

        // 使用反射获取私有方法
        Method method = SuitHost.class.getDeclaredMethod("UpdatePrompt", String.class);
        method.setAccessible(true);

        String result = (String) method.invoke(host, prompt);

        assertEquals(result, "PlasticMetal.JMobileSuitLite.SuitHostTests$TestInstance[Instance]");
    }

    @Test
    public void testRunBuildInCommand() throws Exception {

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
        // 创建 SuitHost 实例
        Object instance = new TestInstance();
        SuitConfiguration config = SuitConfiguration.getInstance();
        SuitHost suitHost = new SuitHost(instance, config);

        // 创建私有方法的参数
        String[] args = {"arg1", "arg2"};

        // 使用反射获取私有方法
        Method method = SuitHost.class.getDeclaredMethod("RunObject", String[].class);
        method.setAccessible(true); // 设置私有方法可访问

        // 调用私有方法
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
        // 创建 SuitHost 实例
        Object instance = new TestInstance();
        SuitConfiguration config = SuitConfiguration.getInstance();
        SuitHost suitHost = new SuitHost(instance, config);

        // 测试空命令
        TraceBack result = suitHost.RunCommand("", "");
        Assert.assertEquals(TraceBack.AllOk, result);

        // 测试注释命令
        result = suitHost.RunCommand("", "# This is a comment");
        Assert.assertEquals(TraceBack.AllOk, result);

        // 测试内置命令
        result = suitHost.RunCommand("", "@internal command");
        Assert.assertEquals(TraceBack.ObjectNotFound, result);

        // 测试对象命令
        result = suitHost.RunCommand("", "object command");
        Assert.assertEquals(TraceBack.ObjectNotFound, result);

        // 测试无效命令
        result = suitHost.RunCommand("", null);
        Assert.assertEquals(TraceBack.AllOk, result);

    }

    @Test
    public void testRunCommand_2() throws Exception {
        // 创建 SuitHost 实例
        Object instance = new TestInstance();
        SuitConfiguration config = SuitConfiguration.getInstance();
        SuitHost suitHost = new SuitHost(instance, config);

        // 测试空命令
        TraceBack result = suitHost.RunCommand("");
        Assert.assertEquals(TraceBack.AllOk, result);

        // 测试注释命令
        result = suitHost.RunCommand("# This is a comment");
        Assert.assertEquals(TraceBack.AllOk, result);

        // 测试内置命令
        result = suitHost.RunCommand("@internal command");
        Assert.assertEquals(TraceBack.ObjectNotFound, result);

        // 测试对象命令
        result = suitHost.RunCommand("object command");
        Assert.assertEquals(TraceBack.ObjectNotFound, result);

        // 测试无效命令
        result = suitHost.RunCommand(null);
        Assert.assertEquals(TraceBack.AllOk, result);

    }

}