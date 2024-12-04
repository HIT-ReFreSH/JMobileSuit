package ReFreSH.JMobileSuit.Demo;

import ReFreSH.JMobileSuit.IO.IOServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class DiagnosticsDemoTest {
    /**
     * Testing Strategy
     * <p>
     * Testing Get1():
     *   NOArgs
     * Testing ET():
     *   NOArgs
     * test method：All cases are covered at least once
     * */
    private DiagnosticsDemo diagnosticsDemo;
    private IOServer mockIoServer;

    @Before
    public void setUp() {
        // Initialize the Client and mock IOServer
        diagnosticsDemo = new DiagnosticsDemo();
        mockIoServer = Mockito.mock(IOServer.class);
        diagnosticsDemo.setIO(mockIoServer);
    }

    @Test
    public void Get1Test() throws Exception {
        // Test the Get1 method
        assertEquals(diagnosticsDemo.ReturnValueTest(),1);
    }

    @Test(expected = Exception.class)
    public void ETTest() throws Exception {
        // Test the ET method
        try{
            diagnosticsDemo.ExceptionTest();
        }catch (Exception e){
            assertEquals(e.getMessage(),"Test");
        }
        diagnosticsDemo.ExceptionTest();
    }
}
