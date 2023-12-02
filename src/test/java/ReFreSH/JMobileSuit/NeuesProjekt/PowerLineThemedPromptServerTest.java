package ReFreSH.JMobileSuit.NeuesProjekt;

import ReFreSH.JMobileSuit.IO.*;
import ReFreSH.JMobileSuit.SuitConfiguration;
import ReFreSH.JMobileSuit.TraceBack;
import org.mockito.ArgumentCaptor;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.lang.reflect.Field;
import java.util.List;
import ReFreSH.JMobileSuit.IO.ColorSetting;





public class PowerLineThemedPromptServerTest {

    private PowerLineThemedPromptServer promptServer;
    private IOServer mockIoServer;
    private SuitConfiguration mockConfiguration;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        // 创建mock对象
        mockIoServer = mock(IOServer.class);
        mockConfiguration = mock(SuitConfiguration.class);

        // 实例化PowerLineThemedPromptServer
        promptServer = new PowerLineThemedPromptServer(mockConfiguration);
        // 使用反射设置 protected 属性 IO 的值
        Field ioField = CommonPromptServer.class.getDeclaredField("IO");
        ioField.setAccessible(true);
        ioField.set(promptServer, mockIoServer);

        // 使用反射设置 protected 属性 colorSetting 的值
        ColorSetting Scolor = new ColorSetting();
        Field colorSettingField = CommonPromptServer.class.getDeclaredField("Color");
        colorSettingField.setAccessible(true);
        colorSettingField.set(promptServer, Scolor);

    }

    @Test
    public void testGetPowerLineThemeConfiguration() {
        // 测试getPowerLineThemeConfiguration方法
        SuitConfiguration configuration = PowerLineThemedPromptServer.getPowerLineThemeConfiguration();
        assertNotNull("Configuration should not be null", configuration);
        // 进一步的断言可以根据实际情况添加
    }

    @Test
    public void testPrintMethod() throws NoSuchFieldException, IllegalAccessException {
        // 设置必要的前置条件
        Field TraceBackField = CommonPromptServer.class.getDeclaredField("LastTraceBack");
        TraceBackField.setAccessible(true);
        TraceBackField.set(promptServer, TraceBack.AllOk);
        //promptServer.LastTraceBack = TraceBack.AllOk;

        Field InformationField = CommonPromptServer.class.getDeclaredField("LastInformation");
        InformationField.setAccessible(true);
        InformationField.set(promptServer, "TestInfo");
        //promptServer.LastInformation = "TestInfo";



        Field ReturnValueField = CommonPromptServer.class.getDeclaredField("LastReturnValue");
        ReturnValueField.setAccessible(true);
        ReturnValueField.set(promptServer, "TestReturn");
        //promptServer.LastReturnValue = "TestReturn";




        Field PromptInformationField = CommonPromptServer.class.getDeclaredField("LastPromptInformation");
        PromptInformationField.setAccessible(true);
        PromptInformationField.set(promptServer, "PromptInfo");
        //promptServer.LastPromptInformation = "PromptInfo";

        // 调用Print方法
        promptServer.Print();

        // 使用ArgumentCaptor来捕获write方法的调用参数
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ConsoleColor> colorArgumentCaptor = ArgumentCaptor.forClass(ConsoleColor.class);
        verify(mockIoServer, atLeastOnce()).Write(stringArgumentCaptor.capture(), colorArgumentCaptor.capture(), colorArgumentCaptor.capture());

        // 验证write方法是否被正确调用和参数是否正确
        List<String> capturedStrings = stringArgumentCaptor.getAllValues();
        List<ConsoleColor> capturedColors = colorArgumentCaptor.getAllValues();

        // 根据Print方法的逻辑添加具体的断言
        assertEquals(" TestInfo ", capturedStrings.get(0));
        ConsoleColor Color = ConsoleColor.White;
        assertEquals(Color, capturedColors.get(0));
    }




}