package ReFreSH.JMobileSuit.ObjectModel;

import ReFreSH.JMobileSuit.ObjectModel.Members.MemberAccess;
import ReFreSH.JMobileSuit.ObjectModel.Members.SuitObjectMember;
import ReFreSH.JMobileSuit.TraceBack;
import ReFreSH.Jarvis.ObjectModel.Tuple;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Represents an Object in Mobile Suit.
 */
@SuppressWarnings("unused")
public class SuitObject implements Executable, Iterable<Tuple<String, SuitObjectMember>> {
    private static final Set<String> IgnoreMethods = new HashSet<>(Arrays.asList("wait", "getclass", "equals", "hashcode", "notifyall", "tostring", "notify"));
    private final Object _instance;
    private final Map<String, List<Tuple<String, SuitObjectMember>>> _members = new HashMap<>();
    private final Map<String, List<Tuple<String, SuitObjectMember>>> _membersAbs = new HashMap<>();

    /**
     * Initialize a SuitObject with an instance.
     *
     * @param instance The instance that this SuitObject represents.
     */
    public SuitObject(Object instance) {
        _instance = instance;
        Class<?> type = instance.getClass();
        if (type == null) return;
        for (Method member : type.getMethods()) {
            if ((member.getModifiers() & Modifier.PUBLIC) != 0 && (member.getModifiers() & Modifier.STATIC) == 0 && !IgnoreMethods.contains(member.getName().toLowerCase())) {
                tryAddMember(new SuitObjectMember(instance, member));
            }
        }
    }

    /**
     * The instance that this SuitObject represents.
     *
     * @return The instance that this SuitObject represents.
     */
    public Object Instance() {
        return _instance;
    }


    /**
     * Count of Members that this Object contains.
     *
     * @return Count of Members that this Object contains.
     */
    public int MemberCount() {
        return _members.size();
    }


    /**
     * @param args The arguments for execution.
     * @return execute this Object.
     */
    public Tuple<TraceBack, Object> execute(String[] args) {


        if (args.length == 0) return new Tuple<>(TraceBack.ObjectNotFound, null);
        args[0] = args[0].toLowerCase();

        if (!_members.containsKey(args[0])) return new Tuple<>(TraceBack.ObjectNotFound, null);

        for (Tuple<String, SuitObjectMember> t : _members.get(args[0])) {
            Tuple<TraceBack, Object> r;
            try {
                r = t.Second.execute(Arrays.copyOfRange(args, 1, args.length));
            } catch (IllegalAccessException | InstantiationException e) {
                r = new Tuple<>(TraceBack.InvalidCommand, e);
            } catch (InvocationTargetException e) {
                r = new Tuple<>(TraceBack.AppException, e.getTargetException());
            } catch (Exception e) {
                r = new Tuple<>(TraceBack.AppException, e);
            }

            if (r.First == TraceBack.ObjectNotFound) continue;
            return r;
        }
        return new Tuple<>(TraceBack.ObjectNotFound, null);
    }

    private void tryAddMember(SuitObjectMember objMember) {
        if (objMember.Access() != MemberAccess.VisibleToUser)
            return;

        String lAbsName = objMember.AbsoluteName().toLowerCase();
        if (_membersAbs.containsKey(lAbsName))
            _membersAbs.get(lAbsName).add(new Tuple<>(objMember.AbsoluteName(), objMember));
        else _membersAbs.put(lAbsName, new ArrayList<>(
                Collections.singletonList(new Tuple<>(objMember.AbsoluteName(), objMember))));
        for (String name : objMember.FriendlyNames()) {
            String lName = name.toLowerCase();
            if (_members.containsKey(lName)) _members.get(lName).add(new Tuple<>(name, objMember));
            else
                _members.put(lName, new ArrayList<>
                        (Collections.singletonList(new Tuple<>(name, objMember))));
        }
    }


    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Tuple<String, SuitObjectMember>> iterator() {
        List<Tuple<String, SuitObjectMember>> list = new ArrayList<>();
        for (String absName : _membersAbs.keySet()) {
            list.addAll(_membersAbs.get(absName));
        }
        list.sort(Comparator.comparing(o -> o.First));
        return list.iterator();
    }


}
