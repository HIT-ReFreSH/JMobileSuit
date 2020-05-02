package PlasticMetal.JMobileSuitLite.ObjectModel;

import PlasticMetal.JMobileSuitLite.ObjectModel.Members.MemberAccess;
import PlasticMetal.JMobileSuitLite.ObjectModel.Members.SuitObjectMember;
import PlasticMetal.JMobileSuitLite.TraceBack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Represents an Object in Mobile Suit.
 */
@SuppressWarnings("unused")
public class SuitObject implements Executable, Iterable<Tuple<String, SuitObjectMember>>
{
    private final Object _instance;
    private final Map<String, List<Tuple<String, SuitObjectMember>>> _members = new HashMap<>();
    private final Map<String, List<Tuple<String, SuitObjectMember>>> _membersAbs = new HashMap<>();
private static final Set<String> IgnoreMethods=new HashSet<>(Arrays.asList("wait","getclass","equals","hashcode","notifyall","tostring","notify")) ;

    /**
     * Initialize a SuitObject with an instance.
     * @param instance The instance that this SuitObject represents.
     */
    public SuitObject(Object instance)
    {
        _instance = instance;
        Class<?> type = instance.getClass();
        if (type == null)return;
        for(Method member : type.getMethods()){
            if((member.getModifiers() & Modifier.PUBLIC)!=0&&(member.getModifiers() & Modifier.STATIC)==0&&!IgnoreMethods.contains(member.getName().toLowerCase())){
                TryAddMember(new SuitObjectMember(instance,member));
            }
        }
    }

    /**
     * The instance that this SuitObject represents.
     * @return The instance that this SuitObject represents.
     */
    public Object Instance()
    {
        return _instance;
    }



    /**
     * Count of Members that this Object contains.
     *
     * @return Count of Members that this Object contains.
     */
    public int MemberCount()
    {
        return _members.size();
    }


    /**
     *
     * @param args The arguments for execution.
     * @return Execute this Object.
     * @throws IllegalAccessException ignore.
     * @throws InstantiationException ignore.
     * @throws InvocationTargetException ignore.
     */
    public Tuple<TraceBack, Object> Execute(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException
    {


        if (args.length == 0) return new Tuple<>(TraceBack.ObjectNotFound,null);
        args[0] = args[0].toLowerCase();

        if (!_members.containsKey(args[0])) return new Tuple<>(TraceBack.ObjectNotFound,null);

        for(Tuple<String,SuitObjectMember> t : _members.get(args[0]))
        {

            Tuple<TraceBack,Object> r = t.Second.Execute(Arrays.copyOfRange(args,1,args.length));
            if (r.First == TraceBack.ObjectNotFound) continue;
            return r;
        }

        return new Tuple<>(TraceBack.ObjectNotFound,null);
    }

    private void TryAddMember(SuitObjectMember objMember)
    {
        if (objMember.Access() != MemberAccess.VisibleToUser)
            return;

        String lAbsName = objMember.AbsoluteName().toLowerCase();
        if (_membersAbs.containsKey(lAbsName))
            _membersAbs.get(lAbsName).add(new Tuple<>(objMember.AbsoluteName(), objMember));
        else _membersAbs.put(lAbsName, new ArrayList<>(
                Collections.singletonList(new Tuple<>(objMember.AbsoluteName(), objMember))));
        for (String name : objMember.FriendlyNames())
        {
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
    public Iterator<Tuple<String, SuitObjectMember>> iterator()
    {
        List<Tuple<String, SuitObjectMember>> list = new ArrayList<>();
        for (String absName : _membersAbs.keySet())
        {
            list.addAll(_membersAbs.get(absName));
        }
        list.sort(Comparator.comparing(o -> o.First));
        return list.iterator();
    }


}
