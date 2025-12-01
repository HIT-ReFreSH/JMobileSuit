package ReFreSH.JMobileSuit.Demo;

import ReFreSH.JMobileSuit.IO.IOServer;
import ReFreSH.JMobileSuit.ObjectModel.Annotions.SuitAlias;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Method;

import static org.junit.Assert.*;


public class DiagnosticsDemoTest {

    private DiagnosticsDemo diagnosticsDemo;
    private IOServer mockIoServer;

    @Before
    public void setUp() {
        diagnosticsDemo = new DiagnosticsDemo();
        mockIoServer = Mockito.mock(IOServer.class);
        diagnosticsDemo.setIO(mockIoServer);
    }


    @Test
    public void testReturnValueTest() throws Exception {
        int result = diagnosticsDemo.ReturnValueTest();
        assertEquals("ReturnValueTest should return 1", 1, result);
    }

    @Test(expected = Exception.class)
    public void testExceptionTestThrowsException() throws Exception {
        diagnosticsDemo.ExceptionTest();
    }

    @Test
    public void testExceptionTestMessage() {
        try {
            diagnosticsDemo.ExceptionTest();
            fail("Expected exception was not thrown");
        } catch (Exception e) {
            assertEquals("Exception message should be 'Test'", "Test", e.getMessage());
        }
    }


}