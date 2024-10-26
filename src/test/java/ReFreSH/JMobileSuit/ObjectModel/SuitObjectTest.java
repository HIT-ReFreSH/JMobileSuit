// src/test/java/ReFreSH/JMobileSuit/ObjectModel/SuitObjectTest.java
package ReFreSH.JMobileSuit.ObjectModel;

import ReFreSH.JMobileSuit.Demo.DiagnosticsDemo;
import ReFreSH.JMobileSuit.TraceBack;
import ReFreSH.Jarvis.ObjectModel.Tuple;
import org.junit.Test;

import static org.junit.Assert.*;

public class SuitObjectTest {

    @Test
    public void testInstance() {
        // Test the Instance method
        var objects = new Object[]{
                new Object(),
                new DiagnosticsDemo()
        };
        for (var instance : objects) {
            assertEquals(instance, new SuitObject(instance).Instance());
        }
    }

    @Test
    public void testMemberCount() {
        // Test the MemberCount method
        var objects = new Object[]{
                new Object(),
                new DiagnosticsDemo()
        };
        var answers = new int[]{0, 19};
        for (var i = 0; i < objects.length; i++) {
            assertEquals(answers[i], new SuitObject(objects[i]).MemberCount());
        }
    }

    @Test
    public void testExecute() {
        // Test the execute method
        Object instance = new Object();
        SuitObject suitObject = new SuitObject(instance);
        Tuple<TraceBack, Object> result = suitObject.execute(new String[]{"nonexistentMethod"});
        assertEquals(TraceBack.ObjectNotFound, result.First);
        assertNull(result.Second);

        Object instance2 = new DiagnosticsDemo();
        suitObject = new SuitObject(instance2);
        result = suitObject.execute(new String[]{"nonexistentMethod"});
        assertEquals(TraceBack.ObjectNotFound, result.First);
        assertNull(result.Second);
        result = suitObject.execute(new String[]{"get1"});
        assertEquals(TraceBack.AllOk, result.First);
        assertEquals(1, result.Second);
    }

    @Test
    public void testIterator() {
        // Test the iterator method
        assertFalse(new SuitObject(new Object()).iterator().hasNext());
        assertTrue(new SuitObject(new DiagnosticsDemo()).iterator().hasNext());
    }
}
