package ReFreSH.JMobileSuit.ObjectModel;

import ReFreSH.JMobileSuit.IO.ColorSetting;
import ReFreSH.JMobileSuit.IO.IOServer;
import ReFreSH.JMobileSuit.IO.PromptServer;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SuitConfiguratorTest {
    private SuitConfigurator suitConfigurator;

    @Before
    public void setUp() {
        suitConfigurator = SuitConfigurator.ofDefault();
    }

    @Test
    public void testOf() {
        assertNotNull(SuitConfigurator.of(IOServer.class));
        assertNotNull(SuitConfigurator.of(PromptServer.class));
        assertNotNull(SuitConfigurator.of(Logger.class));
        assertNotNull(SuitConfigurator.of(ColorSetting.getInstance()));
    }

    @Test
    public void testUse() {
        assertNotNull(suitConfigurator.use(IOServer.class));
        assertNotNull(suitConfigurator.use(PromptServer.class));
        assertNotNull(suitConfigurator.use(Logger.class));
        assertNotNull(suitConfigurator.use(ColorSetting.getInstance()));
    }

    @Test
    public void testGetConfiguration() {
        assertNotNull(suitConfigurator.getConfiguration());
    }
}