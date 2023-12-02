package ReFreSH.JMobileSuit.ObjectModel;

import ReFreSH.JMobileSuit.ObjectModel.Members.SuitObjectMember;
import ReFreSH.JMobileSuit.TraceBack;
import ReFreSH.Jarvis.ObjectModel.Tuple;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class SuitObjectTest {

    @Test
    public void testInstance() {
        // Test the Instance method
        Object instance = new Object();
        SuitObject suitObject = new SuitObject(instance);
        assertEquals(instance, suitObject.Instance());
    }

    @Test
    public void testMemberCount() {
        // Test the MemberCount method
        Object instance = new Object();
        SuitObject suitObject = new SuitObject(instance);
        // Assuming the Object class has no public non-static methods
        assertEquals(0, suitObject.MemberCount());
    }

    @Test
    public void testExecute() {
        // Test the execute method
        Object instance = new Object();
        SuitObject suitObject = new SuitObject(instance);
        Tuple<TraceBack, Object> result = suitObject.execute(new String[]{"nonexistentMethod"});
        assertEquals(TraceBack.ObjectNotFound, result.First);
        assertNull(result.Second);
    }

    @Test
    public void testIterator() {
        // Test the iterator method
        Object instance = new Object();
        SuitObject suitObject = new SuitObject(instance);
        Iterator<Tuple<String, SuitObjectMember>> iterator = suitObject.iterator();
        assertFalse(iterator.hasNext());
    }
}