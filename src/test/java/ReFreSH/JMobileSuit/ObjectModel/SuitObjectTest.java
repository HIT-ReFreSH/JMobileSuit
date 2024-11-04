package ReFreSH.JMobileSuit.ObjectModel;

import ReFreSH.JMobileSuit.*;
import ReFreSH.JMobileSuit.Demo.DiagnosticsDemo;

import ReFreSH.JMobileSuit.IO.CommonPromptServer;
import ReFreSH.JMobileSuit.IO.IOServer;
import ReFreSH.Jarvis.ObjectModel.Tuple;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.whenNew;


public class SuitObjectTest {

    private SuitClient suitClient;

    @Before
    public void setUp() {
        suitClient = new SuitClient(); // 初始化每个测试需要的对象
    }

    @Test
    public void testSetText() {
        String testText = "Hello, SuitClient!";
        suitClient.setText(testText); // 设置文本
        assertEquals(testText, suitClient.text()); // 确认文本是否正确返回
    }

    @Test
    public void testSetIO() {
        IOServer mockIOServer = mock(IOServer.class);
        suitClient.setIO(mockIOServer); // 设置 IOServer
        assertNotNull(suitClient.IO()); // 确认 IO() 方法不返回 null
        assertEquals(mockIOServer, suitClient.IO()); // 确认返回的 IOServer 是我们设置的 mock 对象
    }

    @Test
    public void testTextInitiallyEmpty() {
        assertEquals("", suitClient.text()); // 初始时文本应为空字符串
    }

    @Test
    public void testInstance() {
        var objects = new Object[]{
                new Object(),
                new DiagnosticsDemo()
        };
        for (var instance : objects) {
            assertEquals(instance, new SuitObject(instance).Instance());
        }
    }

    @Test
    public void testMemberCount() {
        var objects = new Object[]{
                new Object(),
                new DiagnosticsDemo()
        };
        var answers = new int[]{0, 21}; // 根据实际数量进行调整
        for (var i = 0; i < objects.length; i++) {
            int memberCount = new SuitObject(objects[i]).MemberCount();
            System.out.println("Object: " + objects[i].getClass().getName() + ", MemberCount: " + memberCount);
            assertEquals(answers[i], memberCount);
        }
    }

    @Test
    public void testExecute() {
        Object instance = new Object();
        SuitObject suitObject = new SuitObject(instance);
        Tuple<TraceBack, Object> result = suitObject.execute(new String[]{"nonexistentMethod"});
        assertEquals(TraceBack.ObjectNotFound, result.First);
        assertNull(result.Second);

        Object instance2 = new DiagnosticsDemo();
        suitObject = new SuitObject(instance2);
        result = suitObject.execute(new String[]{"nonexistentMethod"});
        assertEquals(TraceBack.ObjectNotFound, result.First);
        assertNull(result.Second);
        result = suitObject.execute(new String[]{"get1"});
        assertEquals(TraceBack.AllOk, result.First);
        assertEquals(1, result.Second);
    }

    @Test
    public void testIterator() {
        assertFalse(new SuitObject(new Object()).iterator().hasNext());
        assertTrue(new SuitObject(new DiagnosticsDemo()).iterator().hasNext());
    }

    @Test
    public void testOfDefault() {
        SuitConfigurator configurator = SuitConfigurator.ofDefault();
        assertNotNull(configurator);
        assertEquals(BuildInCommandServer.class, configurator.BuildInCommandServerType);
        assertNotNull(configurator.ColorSetting);
        assertEquals(CommonPromptServer.class, configurator.PromptServerType);
        assertNull(configurator.logger);
        assertEquals(IOServer.class, configurator.IOServerType);
        assertEquals(CommonSuitConfiguration.class, configurator.ConfigurationType);
    }

    @Test
    public void testUseWithInvalidClass() {
        SuitConfigurator configurator = SuitConfigurator.ofDefault();
        configurator.use(String.class); // 使用不合法的类
        assertEquals(BuildInCommandServer.class, configurator.BuildInCommandServerType);
        assertEquals(CommonPromptServer.class, configurator.PromptServerType);
    }

    @Test
    public void testStart() throws Exception {
        // 创建一个实际的 SuitApplication 实例
        SuitApplication suitApplication = new SuitApplication();

        // 使用 PowerMockito 模拟构造函数
        SuitHost mockSuitHost = mock(SuitHost.class);
        whenNew(SuitHost.class).withArguments(suitApplication).thenReturn(mockSuitHost);

        // 调用 start 方法
        suitApplication.start();

        // 验证 Run 方法被调用
        verify(mockSuitHost).Run();
    }

    @Test
    public void testStartThrowsException() throws Exception {
        // 创建一个实际的 SuitApplication 实例
        SuitApplication suitApplication = new SuitApplication();

        // 当创建的 SuitHost 抛出异常时，验证异常处理
        whenNew(SuitHost.class).withArguments(suitApplication).thenThrow(new RuntimeException("Test Exception"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            suitApplication.start();
        });

        // 确保抛出的异常信息是预期的
        assertEquals("Test Exception", exception.getMessage());
    }
}
