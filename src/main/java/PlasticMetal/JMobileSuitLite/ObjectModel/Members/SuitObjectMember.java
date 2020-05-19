package PlasticMetal.JMobileSuitLite.ObjectModel.Members;

import PlasticMetal.JMobileSuitLite.ObjectModel.Annotions.*;

import PlasticMetal.JMobileSuitLite.ObjectModel.DynamicParameter;
import PlasticMetal.JMobileSuitLite.ObjectModel.Executable;
import PlasticMetal.JMobileSuitLite.ObjectModel.InfoProvider;
import PlasticMetal.JMobileSuitLite.ObjectModel.Parsing.ParsingAPIs;
import PlasticMetal.JMobileSuitLite.ObjectModel.Parsing.SuitParser;
import PlasticMetal.Jarvis.ObjectModel.Tuple;
import PlasticMetal.JMobileSuitLite.TraceBack;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * A SuitObject's member.
 */
@SuppressWarnings("ALL")
public class SuitObjectMember implements Executable
{
    private final MemberAccess _access;
    private final MemberType _type;
    private final String _information;
    private final String[] _aliases;
    private final String _absoluteName;
    private final Object _instance;
    private final TailParameterType _tailParameterType;
    private final Parameter[] _parameters;
    private final int _minParameterCount;
    private final int _nonArrayParameterCount;
    private final int _maxParameterCount;
    private final Method _method;

    /**
     * Initialize a SuitObjectMember with SuitObject's instance and the method's information.
     *
     * @param instance SuitObject's instance
     * @param method   the method's information.
     */
    public SuitObjectMember(Object instance, Method method)
    {
        _access = method.getAnnotation(SuitIgnore.class) == null
                ? MemberAccess.VisibleToUser
                : MemberAccess.Hidden;
        _absoluteName = method.getName();
        SuitAliases aliases = method.getAnnotation(SuitAliases.class);
        SuitAlias alias = method.getAnnotation(SuitAlias.class);
        if (alias != null)
        {
            _aliases = new String[]{alias.value()};
        }
        else if (aliases != null)
        {
            List<String> aliasesList = new ArrayList<>();
            for (SuitAlias aliasClass : aliases.value())
            {
                aliasesList.add(aliasClass.value());
            }
            _aliases = aliasesList.toArray(new String[0]);
        }
        else
        {
            _aliases = new String[0];
        }
        _instance = instance;
        _method = method;

        //Invoke = method::invoke();
        _parameters = method.getParameters();

        int length = _parameters.length;
        if (_parameters.length == 0)
        {
            _tailParameterType = TailParameterType.NoParameter;
            _minParameterCount = 0;
            _nonArrayParameterCount = 0;
            _maxParameterCount = 0;
        }
        else
        {
            Class<?> tailParameterType=_parameters[length - 1].getType();
            if (tailParameterType.isArray())
                _tailParameterType = TailParameterType.Array;
            else {
                boolean flag=false;
                while (!tailParameterType.equals(Object.class)){
                    if(Arrays.asList(tailParameterType.getInterfaces()).contains(DynamicParameter.class) ){
                        flag=true;
                        break;
                    }else {
                        tailParameterType=tailParameterType.getSuperclass();
                    }
                }
                _tailParameterType = flag?TailParameterType.DynamicParameter :TailParameterType.Normal;
            }



            _maxParameterCount =
                    _tailParameterType == TailParameterType.Normal
                            ? length
                            : Integer.MAX_VALUE;
            _nonArrayParameterCount =
                    _tailParameterType == TailParameterType.Normal
                            ? length
                            : length - 1;
            int i = _nonArrayParameterCount - 1;
            while ((i >= 0) &&
                    _parameters[i].getAnnotation(SuitDefaultArgument.class) != null)
            {
                i--;
            }

            _minParameterCount = i + 1;
        }

        SuitInfo info = method.getAnnotation(SuitInfo.class);
        if (info == null)
        {
            _type = MemberType.MethodWithoutInfo;
            StringBuilder infoSb = new StringBuilder();
            if (_maxParameterCount > 0)
            {
                for (Parameter parameter : _parameters)
                {
                    infoSb.append(parameter.getName());
                    String paraExpression;
                    if (parameter.getType().isArray())
                        paraExpression = "[]";
                    else if (Arrays.asList(parameter.getType().getInterfaces()).contains(DynamicParameter.class))
                        paraExpression = "{}";
                    else if (parameter.getAnnotation(SuitDefaultArgument.class) != null)
                        paraExpression = "=" + parameter.getAnnotation(SuitDefaultArgument.class).value();
                    else
                        paraExpression = "";
                    infoSb.append(paraExpression);
                    infoSb.append(',');
                }

                _information = infoSb.toString().substring(0, infoSb.length() - 1);
            }
            else
            {
                _information = "";
            }
        }
        else
        {
            _type = MemberType.MethodWithInfo;
            _information= InfoProvider.getInfo(method.getAnnotation(SuitInfo.class));


        }

    }

    /**
     * Whether this member is SuitIgnore or not.
     *
     * @return Whether this member is SuitIgnore or not.
     */
    public MemberAccess Access()
    {
        return _access;
    }

    /**
     * Type of the member
     *
     * @return Type of the member
     */
    public MemberType Type()
    {
        return _type;
    }

    /**
     * Information of this member, customized or generated by Mobile Suit.
     *
     * @return Information of this member, customized or generated by Mobile Suit.
     */
    public String Information()
    {
        return _information;
    }

    /**
     * Absolute name, and aliases.
     */
    public List<String> FriendlyNames()
    {
        List<String> r = new ArrayList<>(Arrays.asList(_aliases));
        r.add(_absoluteName);
        return r;
    }


    /**
     * Aliases of this member.
     *
     * @return Aliases of this member.
     */
    public String[] Aliases()
    {
        return _aliases;
    }


    /**
     * Absolute name of this member.
     */
    public String AbsoluteName()
    {
        return _absoluteName;
    }

    /**
     * get Instance which contains this member.
     *
     * @return Instance which contains this member.
     */
    public Object Instance()
    {
        return _instance;
    }

    private boolean CanFitTo(int argumentCount)
    {
        return argumentCount >= _minParameterCount
                && argumentCount <= _maxParameterCount;
    }

    private Tuple<TraceBack, Object> Execute(Object[] args) throws InvocationTargetException, IllegalAccessException
    {
        if (args.length == 0)
        {
            Object t = _method.invoke(_instance);

            return new Tuple<>(t instanceof TraceBack ? (TraceBack) t : TraceBack.AllOk, t);
        }
        else
        {
            Object t = _method.invoke(_instance, args);

            return new Tuple<>(t instanceof TraceBack ? (TraceBack) t : TraceBack.AllOk, t);
        }
    }

    /**
     * Execute this object.
     *
     * @param args The arguments for execution.
     * @return TraceBack result of this object.
     */
    public Tuple<TraceBack, Object> Execute(String[] args) throws InvocationTargetException, IllegalAccessException, InstantiationException
    {
        String parseSrc;
        Method m;
        if (!CanFitTo(args.length))
        {
            return new Tuple<>(TraceBack.ObjectNotFound, null);
        }
        if (_tailParameterType == TailParameterType.NoParameter)
        {
            return Execute(new Object[0]);
        }
        int length = _parameters.length;
        Object[] pass = new Object[length];
        int i = 0;
        for (; i < _nonArrayParameterCount; i++)
        {
            parseSrc = i < args.length ? (args[i])
                    : _parameters[i].getAnnotation(SuitDefaultArgument.class).value();
            m = ParsingAPIs.getParser(_parameters[i].getAnnotation(SuitParser.class));

            if (m != null)
            {
                try
                {
                    pass[i] = m.invoke(null, parseSrc);
                }
                catch (Exception e)
                {

                    return new Tuple<>(TraceBack.InvalidCommand, e);
                }
            }
            else
            {
                pass[i] = parseSrc;
            }

        }


        if (_tailParameterType == TailParameterType.Normal)
        {
            return Execute(pass);
        }

        if (_tailParameterType == TailParameterType.DynamicParameter)
        {

            DynamicParameter dynamicParameter = (DynamicParameter) _parameters[length - 1].getType().newInstance();

            if (dynamicParameter.Parse(i < args.length ? Arrays.copyOfRange(args, i, args.length) : new String[0]))
            {
                pass[i] = dynamicParameter;
                return Execute(pass);
            }
            return new Tuple<>(TraceBack.InvalidCommand, null);
        }

        if (i < args.length)
        {

            m= ParsingAPIs.getParser(_parameters[i].getAnnotation(SuitParser.class));
            if(m!=null){
                try
                {

                    Object argArray=Array.newInstance(_parameters[length - 1].getType().getComponentType(),args.length-i);
                    int k=i;
                    for(int j=0;k<args.length;k++){
                        Array.set(argArray,j,m.invoke(null,args[i]));
                        j++;
                    }
                    pass[i]=argArray;
                }
                catch (Exception e)
                {
                    return new Tuple<>(TraceBack.InvalidCommand, e);
                }

            }else {
                pass[i] = Arrays.copyOfRange(args, i, args.length);
            }

        }
        else
        {
            pass[i]= Array.newInstance(_parameters[length - 1].getType().getComponentType(),0);
            //pass[i] = new String[0];
        }

        return Execute(pass);

    }


}



