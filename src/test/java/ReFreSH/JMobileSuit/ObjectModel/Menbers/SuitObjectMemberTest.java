package ReFreSH.JMobileSuit.ObjectModel.Menbers;

import ReFreSH.JMobileSuit.ObjectModel.Members.MemberAccess;
import ReFreSH.JMobileSuit.ObjectModel.Members.SuitObjectMember;
import ReFreSH.JMobileSuit.TraceBack;
import ReFreSH.Jarvis.ObjectModel.Tuple;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SuitObjectMemberTest {

    @Test
    public void testSuitObjectMemberCreation() {
        Object instance = new String("TEST");  //  replace  with  actual  instance
        Method method = instance.getClass().getMethods()[0];

        SuitObjectMember suitObjectMember = new SuitObjectMember(instance, method);

        assertNotNull(suitObjectMember);
        assertEquals(suitObjectMember.Access(), MemberAccess.VisibleToUser);
        assertEquals(suitObjectMember.AbsoluteName(), method.getName());
        assertEquals(suitObjectMember.Instance(), instance);

    }

    @Test
    public void testSuitObjectMember() {
        Object instance = new Object();
        Method method = instance.getClass().getMethods()[0];
        SuitObjectMember suitObjectMember = new SuitObjectMember(instance, method);
        assertNotNull(suitObjectMember.Access());
        assertNotNull(suitObjectMember.Type());
        assertNotNull(suitObjectMember.Information());
        assertEquals(suitObjectMember.FriendlyNames().get(0), suitObjectMember.AbsoluteName());
        assertNotNull(suitObjectMember.Instance());
    }

    @Test
    public void testAccess() {
        Object instance = new Object();
        Method method = instance.getClass().getMethods()[0];
        SuitObjectMember suitObjectMember = new SuitObjectMember(instance, method);
        MemberAccess memberAccess = MemberAccess.VisibleToUser;
        assertNotNull(suitObjectMember.Access());
        assertEquals(memberAccess, suitObjectMember.Access());
    }

    @Test
    public void testType() {
        Object instance = new Object();
        Method method = instance.getClass().getMethods()[0];
        SuitObjectMember suitObjectMember = new SuitObjectMember(instance, method);
        assertNotNull(suitObjectMember.Type());
    }

    @Test
    public void testInformation() {
        Object instance = new Object();
        Method method = instance.getClass().getMethods()[0];
        SuitObjectMember suitObjectMember = new SuitObjectMember(instance, method);
        assertNotNull(suitObjectMember.Information());
        assertEquals("arg0", suitObjectMember.Information());
    }

    @Test
    public void testFriendlyNames() {
        Object instance = new Object();
        Method method = instance.getClass().getMethods()[0];
        SuitObjectMember suitObjectMember = new SuitObjectMember(instance, method);
        assertEquals(suitObjectMember.FriendlyNames().get(0), suitObjectMember.AbsoluteName());
    }


    @Test
    public void testSuitAliases() {
        Object instance = new Object();
        Method method = instance.getClass().getMethods()[0];
        SuitObjectMember suitObjectMember = new SuitObjectMember(instance, method);
        assertNotNull(suitObjectMember.Aliases());
    }

    @Test
    public void testSuitAbsoluteName() {
        Object instance = new Object();
        Method method = instance.getClass().getMethods()[0];
        SuitObjectMember suitObjectMember = new SuitObjectMember(instance, method);
        assertNotNull(suitObjectMember.AbsoluteName());
        assertEquals(suitObjectMember.AbsoluteName(), method.getName());
    }

    @Test
    public void testInstance() {
        Object instance = new Object();
        Method method = instance.getClass().getMethods()[0];
        SuitObjectMember suitObjectMember = new SuitObjectMember(instance, method);
        Object object = suitObjectMember.Instance();
        assertNotNull(suitObjectMember.Instance());
        assertEquals(object, suitObjectMember.Instance());
    }

    @Test
    public void testExecute() throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Object instance = new Object();
        Method method = instance.getClass().getMethods()[0];
        SuitObjectMember suitObjectMember = new SuitObjectMember(instance, method);
        String[] test = new String[]{"TEST1", "TEST2", "TEST3"};
        Tuple tuple = new Tuple<>(TraceBack.ObjectNotFound, null);
        assertNotNull(suitObjectMember.execute(test));
        assertEquals(tuple.toString(), suitObjectMember.execute(test).toString());
    }
}
