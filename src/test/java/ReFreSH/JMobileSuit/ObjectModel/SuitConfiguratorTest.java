// src/test/java/ReFreSH/JMobileSuit/ObjectModel/SuitConfiguratorTest.java
package ReFreSH.JMobileSuit.ObjectModel;

import ReFreSH.JMobileSuit.IO.ColorSetting;
import ReFreSH.JMobileSuit.IO.IOServer;
import ReFreSH.JMobileSuit.SuitConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SuitConfiguratorTest {

    @Test
    public void testOf() {
        // Test the of method with Class<?> parameter
        Class<?> s = IOServer.class;
        SuitConfigurator suitConfigurator = SuitConfigurator.of(s);
        assertEquals(s, suitConfigurator.IOServerType);

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
    }

    @Test
    public void testUse() {
        // Test the use method with Class<?> parameter
        Class<?> s = IOServer.class;
        SuitConfigurator suitConfigurator = SuitConfigurator.ofDefault();
        suitConfigurator = suitConfigurator.use(s);
        assertEquals(s, suitConfigurator.IOServerType);

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
        // Test the getConfiguration method
        SuitConfigurator suitConfigurator = SuitConfigurator.ofDefault();
        SuitConfiguration suitConfiguration = suitConfigurator.getConfiguration();
        assertNotNull(suitConfiguration);
    }
}
