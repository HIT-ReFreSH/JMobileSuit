package ReFreSH.JMobileSuit.NeuesProjekt;

import ReFreSH.JMobileSuit.IO.*;
import ReFreSH.JMobileSuit.SuitConfiguration;
import ReFreSH.JMobileSuit.TraceBack;
import org.mockito.ArgumentCaptor;
import org.junit.Before;
import org.junit.Test;
import static ReFreSH.JMobileSuit.LangResourceBundle.Lang;
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
        mockIoServer = mock(IOServer.class);
        mockConfiguration = mock(SuitConfiguration.class);

        promptServer = new PowerLineThemedPromptServer(mockConfiguration);

// Use reflection to set the value of the protected attribute IO
        Field ioField = CommonPromptServer.class.getDeclaredField("IO");
        ioField.setAccessible(true);
        ioField.set(promptServer, mockIoServer);

// Use reflection to set the value of the protected property colorSetting
        ColorSetting colorSetting = new ColorSetting();
        Field colorSettingField = CommonPromptServer.class.getDeclaredField("Color");
        colorSettingField.setAccessible(true);
        colorSettingField.set(promptServer, colorSetting);
    }

    @Test
    public void testGetPowerLineThemeConfiguration() {
        SuitConfiguration configuration = PowerLineThemedPromptServer.getPowerLineThemeConfiguration();
        assertNotNull("Configuration should not be null", configuration);
    }

    @Test
    public void testPrintMethod_AllOk() throws NoSuchFieldException, IllegalAccessException {
        testPrintMethodWithTraceBack(TraceBack.AllOk, "TestInfo", "TestReturn", "PromptInfo");
    }

    @Test
    public void testPrintMethod_Prompt() throws NoSuchFieldException, IllegalAccessException {
        testPrintMethodWithTraceBack(TraceBack.Prompt, "TestInfo", null, "PromptInfo");
    }

    @Test
    public void testPrintMethod_InvalidCommand() throws NoSuchFieldException, IllegalAccessException {
        testPrintMethodWithTraceBack(TraceBack.InvalidCommand, "TestInfo", null, "SomeInvalidCommandInfo");
    }

    @Test
    public void testPrintMethod_NoReturnValue() throws NoSuchFieldException, IllegalAccessException {
        testPrintMethodWithTraceBack(TraceBack.AllOk, "TestInfo", null, "PromptInfo");
    }

    @Test
    public void testPrintMethod_SpecialReturnValue() throws NoSuchFieldException, IllegalAccessException {
        testPrintMethodWithTraceBack(TraceBack.Prompt, "SpecialInfo", "🚀", "SpecialPromptInfo");
    }

    @Test
    public void testPrintMethod_EmptyInformation() throws NoSuchFieldException, IllegalAccessException {
        testPrintMethodWithTraceBack(TraceBack.AllOk, "", "TestReturn", "PromptInfo");
    }

    private void testPrintMethodWithTraceBack(TraceBack lastTraceBack, String lastInformation, String lastReturnValue, String lastPromptInformation) throws NoSuchFieldException, IllegalAccessException {
        Field TraceBackField = CommonPromptServer.class.getDeclaredField("LastTraceBack");
        TraceBackField.setAccessible(true);
        TraceBackField.set(promptServer, lastTraceBack);

        Field InformationField = CommonPromptServer.class.getDeclaredField("LastInformation");
        InformationField.setAccessible(true);
        InformationField.set(promptServer, lastInformation);

        Field ReturnValueField = CommonPromptServer.class.getDeclaredField("LastReturnValue");
        ReturnValueField.setAccessible(true);
        ReturnValueField.set(promptServer, lastReturnValue);

        Field PromptInformationField = CommonPromptServer.class.getDeclaredField("LastPromptInformation");
        PromptInformationField.setAccessible(true);
        PromptInformationField.set(promptServer, lastPromptInformation);

        promptServer.Print();

        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ConsoleColor> colorArgumentCaptor = ArgumentCaptor.forClass(ConsoleColor.class);
        verify(mockIoServer, atLeastOnce()).Write(stringArgumentCaptor.capture(), colorArgumentCaptor.capture(), colorArgumentCaptor.capture());

        List<String> capturedStrings = stringArgumentCaptor.getAllValues();
        List<ConsoleColor> capturedColors = colorArgumentCaptor.getAllValues();

        // Add specific assertions based on the lastTraceBack and other inputs
        // Here we'll just check the first captured string and color as an example
        // More detailed assertions should be added based on the expected output for each trace back type
        assertNotNull(capturedStrings);
        assertNotNull(capturedColors);
        assertTrue(capturedStrings.size() > 0);
        assertTrue(capturedColors.size() > 0);

        // Example assertions for TraceBack.AllOk
        if (lastTraceBack == TraceBack.AllOk) {
            assertTrue(capturedStrings.stream().anyMatch(s -> s.contains(lastInformation)));
            assertTrue(capturedStrings.stream().anyMatch(s -> s.contains(Lang.AllOK)));
        } else if (lastTraceBack == TraceBack.Prompt) {
            assertTrue(capturedStrings.stream().anyMatch(s -> s.contains(lastInformation)));
            assertTrue(capturedStrings.stream().anyMatch(s -> s.contains(lastPromptInformation)));
        } else if (lastTraceBack == TraceBack.InvalidCommand) {
            assertTrue(capturedStrings.stream().anyMatch(s -> s.contains(Lang.InvalidCommand)));
        }
        // Add more assertions for other trace back types similarly
    }
}
