package PlasticMetal.JMobileSuitLite.Diagnostics;

import PlasticMetal.JMobileSuitLite.TraceBack;
import org.junit.jupiter.api.Test;


import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;

public class SuitLoggerTest {

    @Test
    public void testLogDebug() {
        SuitLogger suitLogger = createTempLogger();
        suitLogger.LogDebug("Debug message");
        // Add assertions based on your logging mechanism
        assertNotNull(suitLogger.getLogMem());
        assertFalse(suitLogger.getLogMem().isEmpty());
    }

    @Test
    public void testLogCommand() {
        SuitLogger suitLogger = createTempLogger();
        suitLogger.LogCommand("Command message");
        assertNotNull(suitLogger.getLogMem());
        assertFalse(suitLogger.getLogMem().isEmpty());
    }

    @Test
    public void testLogTraceBack() {
        SuitLogger suitLogger = createTempLogger();
        suitLogger.LogTraceBack(TraceBack.AllOk);
        assertNotNull(suitLogger.getLogMem());
        assertFalse(suitLogger.getLogMem().isEmpty());
    }

    @Test
    public void testLogTraceBackWithReturnValue() {
        SuitLogger suitLogger = createTempLogger();
        suitLogger.LogTraceBack(TraceBack.AllOk, "Return value");
        assertNotNull(suitLogger.getLogMem());
        assertFalse(suitLogger.getLogMem().isEmpty());
    }

    @Test
    public void testLogExceptionWithStringContent() {
        SuitLogger suitLogger = createTempLogger();
        suitLogger.LogException("Exception message");
        assertNotNull(suitLogger.getLogMem());
        assertFalse(suitLogger.getLogMem().isEmpty());
    }

    @Test
    public void testLogExceptionWithException() {
        SuitLogger suitLogger = createTempLogger();
        suitLogger.LogException(new IOException("Test exception"));
        assertNotNull(suitLogger.getLogMem());
        assertFalse(suitLogger.getLogMem().isEmpty());
    }


    @Test
    public void testCreateLoggerOfLocal() {
        SuitLogger suitLogger = SuitLogger.ofLocal();
        assertNotNull(suitLogger);
        assertNotNull(suitLogger.Path);
    }

    @Test
    public void testCreateLoggerOfTemp() {
        SuitLogger suitLogger = SuitLogger.ofTemp();
        assertNotNull(suitLogger);
        assertNotNull(suitLogger.Path);
    }

    private SuitLogger createTempLogger() {
        return SuitLogger.ofTemp();
    }
}