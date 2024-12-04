package ReFreSH.JMobileSuit.IO;

import ReFreSH.JMobileSuit.CommonSuitConfiguration;
import ReFreSH.JMobileSuit.ObjectModel.SuitConfigurator;
import ReFreSH.JMobileSuit.SuitConfiguration;
import ReFreSH.JMobileSuit.TraceBack;
import org.junit.Rule;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.module.Configuration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CommonPromptServerTest {
    /**
     * Testing Strategy
     * <p>
     * Testing CommonPromptServer(IOServer io, ColorSetting colorSetting)
     *   io: null;not null;
     *   colorSetting: null;not null;
     * Testing CommonPromptServer(SuitConfiguration configuration):
     *   configuration: null;not null
     * Testing Update():
     *   returnValue:null;not null;
     *   information:null:not null;
     *   traceBack:null;not null;
     *   promptInformation:null;not null;
     * Testing Print():
     *   NOArgs
     * Testing setIO():
     *   IO:null;not null
     * test method：All cases are covered at least once
     * */

    //CommonPromptServer() allow args which are null now
    @Test
    public void testCommonPromptServer(){
        IOServer ioServer = new IOServer();
        ColorSetting colorSetting = null;
        CommonPromptServer commonPromptServer1 = new CommonPromptServer(ioServer,colorSetting);
        CommonPromptServer commonPromptServer2 = new CommonPromptServer(null,colorSetting);
        assertEquals(ioServer,commonPromptServer1.IO);
        assertNotEquals(ioServer,commonPromptServer2.IO);
        assertEquals(null,commonPromptServer1.Color);
    }

    @Test
    public void testCommonPromptServerByConfigure(){
        SuitConfiguration suitConfiguration = new CommonSuitConfiguration(null,null,null,null,null);
        IOServer ioServer = new IOServer();
        ColorSetting colorSetting = new ColorSetting();
        SuitConfiguration suitConfiguration1 = new CommonSuitConfiguration(null,ioServer,null,colorSetting,null);
        CommonPromptServer commonPromptServer1 = new CommonPromptServer(suitConfiguration);
        CommonPromptServer commonPromptServer2 = new CommonPromptServer(suitConfiguration1);
        assertEquals(null,commonPromptServer1.IO);
        assertEquals(ioServer,commonPromptServer2.IO);
        assertEquals(null,commonPromptServer1.Color);
        assertEquals(colorSetting,commonPromptServer2.Color);
    }

    @Test
    public void testUpdate(){
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(output);
        PrintStream printStream1 = System.out;
        System.setOut(printStream);
        IOServer ioServer = new IOServer();
        ioServer.ColorSetting = ColorSetting.getInstance();
        CommonPromptServer commonPromptServer = new CommonPromptServer(ioServer,ColorSetting.getInstance());
        commonPromptServer.Update("args1","args2",TraceBack.Prompt,"args3");
        commonPromptServer.Print();
        assertEquals("\u001B[;35m args2\u001B[0m\u001B[;35m[args3]\u001B[0m\u001B[;35m > \u001B[0m",output.toString());
        System.setOut(printStream1);

        assertEquals("args3",commonPromptServer.LastPromptInformation);
    }

    @Test
    public void testPrint(){
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(output);
        PrintStream printStream1 = System.out;
        System.setOut(printStream);
        IOServer ioServer = new IOServer();
        ioServer.ColorSetting = ColorSetting.getInstance();
        CommonPromptServer commonPromptServer = new CommonPromptServer(ioServer,ColorSetting.getInstance());
        commonPromptServer.Update("args1","args2",TraceBack.Prompt,"args3");
        commonPromptServer.Print();
        assertEquals("\u001B[;35m args2\u001B[0m\u001B[;35m[args3]\u001B[0m\u001B[;35m > \u001B[0m",output.toString());
        output.reset();
        commonPromptServer.Update(null,null,null,null);
        commonPromptServer.Print();
        assertEquals("\u001B[;35m null\u001B[0m\u001B[;35m > \u001B[0m",output.toString());
        System.setOut(printStream1);
    }

    @Test
    public void testIO(){
        IOServer ioServer = new IOServer();
        CommonPromptServer commonPromptServer = new CommonPromptServer(ioServer,ColorSetting.getInstance());
        commonPromptServer.setIO(ioServer);
        assertEquals(ioServer,commonPromptServer.IO);
        commonPromptServer.setIO(null);
        assertEquals(null,commonPromptServer.IO);
    }


}
