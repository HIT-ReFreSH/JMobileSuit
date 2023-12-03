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
        // Create mock objects
        mockIoServer = mock(IOServer.class);
        mockConfiguration = mock(SuitConfiguration.class);

        // Instantiation PowerLineThemedPromptServer
        promptServer = new PowerLineThemedPromptServer(mockConfiguration);
        // Use reflection to set the value of the protected attribute IO
        Field ioField = CommonPromptServer.class.getDeclaredField("IO");
        ioField.setAccessible(true);
        ioField.set(promptServer, mockIoServer);

        // Use reflection to set the value of the protected property colorSetting
        ColorSetting Scolor = new ColorSetting();
        Field colorSettingField = CommonPromptServer.class.getDeclaredField("Color");
        colorSettingField.setAccessible(true);
        colorSettingField.set(promptServer, Scolor);

    }

    @Test
    public void testGetPowerLineThemeConfiguration() {
        // test getPowerLineThemeConfiguration method
        SuitConfiguration configuration = PowerLineThemedPromptServer.getPowerLineThemeConfiguration();
        assertNotNull("Configuration should not be null", configuration);
        // Further assertions can be added as appropriate
    }

    @Test
    public void testPrintMethod() throws NoSuchFieldException, IllegalAccessException {
        // Set the necessary preconditions
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

        // Call the Print method
        promptServer.Print();

        // Use ArgumentCaptor to capture the call arguments for the write method
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ConsoleColor> colorArgumentCaptor = ArgumentCaptor.forClass(ConsoleColor.class);
        verify(mockIoServer, atLeastOnce()).Write(stringArgumentCaptor.capture(), colorArgumentCaptor.capture(), colorArgumentCaptor.capture());

        // Verify that the write method is called correctly and that the parameters are correct
        List<String> capturedStrings = stringArgumentCaptor.getAllValues();
        List<ConsoleColor> capturedColors = colorArgumentCaptor.getAllValues();

        // Add concrete assertions according to the logic of the Print method
        assertEquals(" TestInfo ", capturedStrings.get(0));
        ConsoleColor Color = ConsoleColor.White;
        assertEquals(Color, capturedColors.get(0));
    }




}