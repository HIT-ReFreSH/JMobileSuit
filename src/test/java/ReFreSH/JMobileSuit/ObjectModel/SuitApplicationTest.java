package ReFreSH.JMobileSuit.ObjectModel;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import java.time.Duration;

public class SuitApplicationTest {

    @Test
    void testStart() {
        //  SuitApplication
        SuitApplication suitApplication = new SuitApplication();

        assertDoesNotThrow(() -> suitApplication.start());
    }
}
