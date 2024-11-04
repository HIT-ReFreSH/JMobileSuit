// src/test/java/ReFreSH/JMobileSuit/ObjectModel/SuitConfiguratorTest.java
package ReFreSH.JMobileSuit.ObjectModel;

import ReFreSH.JMobileSuit.BuildInCommandServer;
import ReFreSH.JMobileSuit.IO.ColorSetting;
import ReFreSH.JMobileSuit.IO.CommonPromptServer;
import ReFreSH.JMobileSuit.IO.IOServer;
import ReFreSH.JMobileSuit.IO.PromptServer;
import ReFreSH.JMobileSuit.SuitConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;

public class SuitConfiguratorTest {

    @Test
    public void testOf() {
        // Test the of method with Class<?> parameter
        Class<?> s = IOServer.class;
        SuitConfigurator suitConfigurator = SuitConfigurator.of(s);
        assertEquals(s, suitConfigurator.IOServerType);


        // Test the of method with BuildInCommandServer class
        suitConfigurator = SuitConfigurator.of(BuildInCommandServer.class);
        assertEquals(BuildInCommandServer.class, suitConfigurator.BuildInCommandServerType);


        // Test the of method with Logger parameter
        Logger logger = LogManager.getLogger("test");
        suitConfigurator = SuitConfigurator.of(logger);
        assertEquals(logger, suitConfigurator.logger);

        // Test the of method with ColorSetting parameter
        ColorSetting colorSetting = ColorSetting.getInstance();
        suitConfigurator = SuitConfigurator.of(colorSetting);
        assertEquals(colorSetting, suitConfigurator.ColorSetting);
    }

    @Test
    public void testOfDefault() {
        // Test the ofDefault method
        SuitConfigurator suitConfigurator = SuitConfigurator.ofDefault();
        assertNotNull(suitConfigurator);
        assertEquals(BuildInCommandServer.class, suitConfigurator.BuildInCommandServerType);
        assertEquals(CommonPromptServer.class, suitConfigurator.PromptServerType);
        assertEquals(IOServer.class, suitConfigurator.IOServerType);
        //assertEquals(CommonSuitConfiguration.class, suitConfigurator.ConfigurationType);
    }

    @Test
    public void testUse() {
        // Test the use method with Class<?> parameter
        Class<?> s = IOServer.class;
        SuitConfigurator suitConfigurator = SuitConfigurator.ofDefault();
        suitConfigurator = suitConfigurator.use(s);
        assertEquals(s, suitConfigurator.IOServerType);

        // Test the use method with BuildInCommandServer class
        suitConfigurator = suitConfigurator.use(BuildInCommandServer.class);
        assertEquals(BuildInCommandServer.class, suitConfigurator.BuildInCommandServerType);

        // Test the use method with Logger parameter
        Logger logger = LogManager.getLogger("test");
        suitConfigurator = suitConfigurator.use(logger);
        assertEquals(logger, suitConfigurator.logger);

        // Test the use method with ColorSetting parameter
        ColorSetting colorSetting = ColorSetting.getInstance();
        suitConfigurator = suitConfigurator.use(colorSetting);
        assertEquals(colorSetting, suitConfigurator.ColorSetting);
    }


    @Test
    public void testGetConfiguration() {
        // Prepare mocks and instances
        PromptServer promptServerMock = mock(PromptServer.class);
        IOServer ioServerMock = mock(IOServer.class);
        SuitConfiguration configMock = mock(SuitConfiguration.class);
        Logger logger = LogManager.getLogger("test");
        ColorSetting colorSetting = ColorSetting.getInstance();

        // Set up SuitConfigurator with mocks
        SuitConfigurator suitConfigurator = new SuitConfigurator();
        suitConfigurator.IOServerType = (Class<? extends IOServer>) ioServerMock.getClass();
        suitConfigurator.PromptServerType = (Class<? extends PromptServer>) promptServerMock.getClass();
        suitConfigurator.ConfigurationType = (Class<? extends SuitConfiguration>) configMock.getClass();
        suitConfigurator.logger = logger;
        suitConfigurator.ColorSetting = colorSetting;


    }
}
