package ReFreSH.JMobileSuit.ObjectModel;

import static org.junit.Assert.*;

import ReFreSH.JMobileSuit.ObjectModel.Members.SuitObjectMember;
import ReFreSH.JMobileSuit.TraceBack;
import ReFreSH.Jarvis.ObjectModel.Tuple;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

public class SuitObjectTest {
    private SuitObject suitObject;

    @Before
    public void setUp() {
        suitObject = new SuitObject(new Object());
    }

    @Test
    public void testInstance() {
        assertNotNull(suitObject.Instance());
    }

    @Test
    public void testMemberCount() {
        assertEquals(0, suitObject.MemberCount());
    }

    @Test
    public void testExecute() {
        Tuple<TraceBack, Object> result = suitObject.execute(new String[]{"nonexistentMethod"});
        assertEquals(TraceBack.ObjectNotFound, result.First);
        assertNull(result.Second);
    }

    @Test
    public void testIterator() {
        Iterator<Tuple<String, SuitObjectMember>> iterator = suitObject.iterator();
        assertFalse(iterator.hasNext());
    }
}