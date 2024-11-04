package ReFreSH.JMobileSuit;

import ReFreSH.JMobileSuit.IO.IOServer;
import ReFreSH.JMobileSuit.ObjectModel.SuitApplication;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SuitApplicationTest {
    @Test
    public void testIO() throws Exception {
        IOServer server = new IOServer();
        SuitApplication app = new SuitApplication();

        app.setIO(server);
        assertEquals(server,app.IO());
    }

    @Test
    public void setText() throws Exception {
        SuitApplication app = new SuitApplication();

        String test ="test";
        app.setText(test);

        assertEquals(test,app.text());
    }
}
